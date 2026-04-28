package com.ajsmods.candycraftreborn.world.dungeon;

public enum DungeonType {
    SLIME(10000, 0),
    SUGUARD(20000, 10000);

    private final int baseX;
    private final int baseZ;

    DungeonType(int baseX, int baseZ) {
        this.baseX = baseX;
        this.baseZ = baseZ;
    }

    public int getBaseX() { return baseX; }
    public int getBaseZ() { return baseZ; }
}
