package com.ajsmods.candycraftreborn.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Sugar Block — forms the portal frame for the CandyCraft dimension.
 * Right-click with a Lava Bucket to activate the portal.
 */
public class SugarBlock extends Block {

    public SugarBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                  InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);
        if (held.is(Items.LAVA_BUCKET)) {
            if (!level.isClientSide) {
                // Search adjacent positions for a valid portal frame interior
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        for (int z = -1; z <= 1; z++) {
                            if (CandyPortalBlock.trySpawnPortal(level, pos.offset(x, y, z))) {
                                // Consume lava, give back empty bucket
                                if (!player.getAbilities().instabuild) {
                                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                                }
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }
}
