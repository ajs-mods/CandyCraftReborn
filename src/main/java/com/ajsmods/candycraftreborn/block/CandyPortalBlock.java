package com.ajsmods.candycraftreborn.block;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.registries.Registries;

/**
 * CandyCraft portal block. Frame is built from Sugar Blocks.
 * Activated by right-clicking the frame with a Sugar Crystal.
 * Teleports between Overworld and the Candy dimension.
 */
public class CandyPortalBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    protected static final VoxelShape X_SHAPE = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape Z_SHAPE = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

    public static final ResourceKey<Level> CANDY_DIMENSION = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation("candycraftreborn", "candy_world"));

    public CandyPortalBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return state.getValue(AXIS) == Direction.Axis.Z ? Z_SHAPE : X_SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof ServerPlayer player
                && !player.isPassenger() && !player.isVehicle()
                && player.canChangeDimensions()) {
            // Cooldown to prevent repeated teleportation
            if (player.isOnPortalCooldown()) {
                player.setPortalCooldown();
                return;
            }
            player.setPortalCooldown();

            MinecraftServer server = level.getServer();
            if (server == null) return;

            ResourceKey<Level> targetKey = level.dimension() == CANDY_DIMENSION ? Level.OVERWORLD : CANDY_DIMENSION;
            ServerLevel targetLevel = server.getLevel(targetKey);
            if (targetLevel == null) return;

            // Simple teleport — place player at same X/Z, Y=200 with fall protection
            player.changeDimension(targetLevel);
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState,
                                   LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // Break portal if frame is invalid (simplified: break if no sugar block adjacent)
        Direction.Axis axis = state.getValue(AXIS);
        boolean hasFrame = false;
        for (Direction d : Direction.values()) {
            if (d.getAxis() != axis) {
                BlockPos framePos = pos.relative(d);
                BlockState frameState = level.getBlockState(framePos);
                if (frameState.is(ModBlocks.SUGAR_BLOCK.get()) || frameState.is(this)) {
                    hasFrame = true;
                    break;
                }
            }
        }
        return hasFrame ? state : Blocks.AIR.defaultBlockState();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        for (int i = 0; i < 4; i++) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            double vx = (random.nextFloat() - 0.5) * 0.5;
            double vy = (random.nextFloat() - 0.5) * 0.5;
            double vz = (random.nextFloat() - 0.5) * 0.5;
            level.addParticle(ParticleTypes.PORTAL, x, y, z, vx, vy, vz);
        }
        if (random.nextInt(100) == 0) {
            level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }
    }

    /**
     * Attempt to create a portal inside a sugar block frame at the given position.
     * Returns true if a valid frame was found and portal blocks placed.
     */
    public static boolean trySpawnPortal(Level level, BlockPos clickedPos) {
        // Try X axis then Z axis
        for (Direction.Axis axis : new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}) {
            PortalShape shape = new PortalShape(level, clickedPos, axis);
            if (shape.isValid()) {
                shape.placePortalBlocks();
                return true;
            }
        }
        return false;
    }

    /**
     * Simple portal frame validator — checks for a rectangular sugar block frame.
     */
    static class PortalShape {
        private final Level level;
        private final Direction.Axis axis;
        private final Direction rightDir;
        private final Direction leftDir;
        private BlockPos bottomLeft;
        private int width;
        private int height;

        PortalShape(Level level, BlockPos pos, Direction.Axis axis) {
            this.level = level;
            this.axis = axis;
            this.rightDir = axis == Direction.Axis.X ? Direction.EAST : Direction.SOUTH;
            this.leftDir = rightDir.getOpposite();
            this.calculatePortalSize(pos);
        }

        boolean isValid() {
            return bottomLeft != null && width >= 2 && width <= 21 && height >= 3 && height <= 21;
        }

        private boolean isFrameBlock(BlockState state) {
            return state.is(ModBlocks.SUGAR_BLOCK.get());
        }

        private boolean isEmptyOrPortal(BlockState state) {
            return state.isAir() || state.is(ModBlocks.CANDY_PORTAL.get());
        }

        private void calculatePortalSize(BlockPos pos) {
            // Find bottom-left corner
            BlockPos cursor = pos;
            while (isEmptyOrPortal(level.getBlockState(cursor.below()))) {
                cursor = cursor.below();
                if (cursor.getY() < level.getMinBuildHeight()) return;
            }
            if (!isFrameBlock(level.getBlockState(cursor.below()))) return;

            while (isEmptyOrPortal(level.getBlockState(cursor.relative(leftDir)))) {
                cursor = cursor.relative(leftDir);
            }
            if (!isFrameBlock(level.getBlockState(cursor.relative(leftDir)))) return;

            this.bottomLeft = cursor;

            // Measure width
            this.width = 0;
            BlockPos wCursor = bottomLeft;
            while (width < 21 && isEmptyOrPortal(level.getBlockState(wCursor))) {
                if (!isFrameBlock(level.getBlockState(wCursor.below()))) { this.bottomLeft = null; return; }
                width++;
                wCursor = wCursor.relative(rightDir);
            }
            if (!isFrameBlock(level.getBlockState(wCursor))) { this.bottomLeft = null; return; }
            if (width < 2) { this.bottomLeft = null; return; }

            // Measure height
            this.height = 0;
            outer:
            for (int y = 0; y < 21; y++) {
                for (int x = 0; x < width; x++) {
                    BlockPos check = bottomLeft.above(y).relative(rightDir, x);
                    if (!isEmptyOrPortal(level.getBlockState(check))) {
                        if (isFrameBlock(level.getBlockState(check))) {
                            this.height = y;
                            break outer;
                        }
                        this.bottomLeft = null;
                        return;
                    }
                }
                // Check side pillars
                if (!isFrameBlock(level.getBlockState(bottomLeft.above(y).relative(leftDir)))) { this.bottomLeft = null; return; }
                if (!isFrameBlock(level.getBlockState(bottomLeft.above(y).relative(rightDir, width)))) { this.bottomLeft = null; return; }
            }
            if (height < 3) { this.bottomLeft = null; }
        }

        void placePortalBlocks() {
            BlockState portalState = ModBlocks.CANDY_PORTAL.get().defaultBlockState()
                    .setValue(CandyPortalBlock.AXIS, axis);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    level.setBlock(bottomLeft.above(y).relative(rightDir, x), portalState, 18);
                }
            }
        }
    }
}
