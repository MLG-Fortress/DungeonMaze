package com.timvisee.dungeonmaze.populator.maze.structure;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;

public class WaterWellRoomPopulator extends MazeRoomBlockPopulator {

    /**
     * General populator constants.
     */
    private static final int LAYER_MIN = 3;
    private static final int LAYER_MAX = 7;
    private static final float ROOM_CHANCE = .002f;

    @Override
    public void populateRoom(MazeRoomBlockPopulatorArgs args) {
        final World world = args.getWorld();
        final Chunk chunk = args.getSourceChunk();
        final int x = args.getRoomChunkX();
        final int y = args.getChunkY();
        final int yFloor = args.getFloorY();
        final int z = args.getRoomChunkZ();

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Floor
        for(int x2 = x; x2 <= x + 7; x2 += 1)
            for(int z2 = z; z2 <= z + 7; z2 += 1)
                chunk.getBlock(x2, yFloor, z2).setType(Material.STONE);

        // Floor (cobblestone underneath the stone floor)
        for(int x2 = x; x2 <= x + 7; x2 += 1)
            for(int z2 = z; z2 <= z + 7; z2 += 1)
                chunk.getBlock(x2, yFloor - 1, z2).setType(Material.COBBLESTONE);

        // Well
        for(int x2 = x + 2; x2 <= x + 4; x2 += 1)
            for(int z2 = z + 2; z2 <= z + 4; z2 += 1)
                chunk.getBlock(x2, yFloor + 1, z2).setType(Material.STONE_BRICKS);

        chunk.getBlock(x + 3, yFloor + 1, z + 3).setType(Material.WATER);

        // Poles
        chunk.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.OAK_FENCE);
        chunk.getBlock(x + 2, yFloor + 2, z + 4).setType(Material.OAK_FENCE);
        chunk.getBlock(x + 4, yFloor + 2, z + 2).setType(Material.OAK_FENCE);
        chunk.getBlock(x + 4, yFloor + 2, z + 4).setType(Material.OAK_FENCE);

        // Roof
        chunk.getBlock(x + 2, yFloor + 3, z + 2).setType(Material.OAK_SLAB);
        Stairs stairs = (Stairs)Material.OAK_STAIRS.createBlockData();
        chunk.getBlock(x + 2, yFloor + 3, z + 3).setBlockData(stairs);
        chunk.getBlock(x + 2, yFloor + 3, z + 4).setType(Material.OAK_SLAB);
        stairs.setFacing(BlockFace.SOUTH);
        chunk.getBlock(x + 3, yFloor + 3, z + 2).setBlockData(stairs);
        chunk.getBlock(x + 3, yFloor + 3, z + 3).setType(Material.GLOWSTONE);
        stairs.setFacing(BlockFace.NORTH);
        chunk.getBlock(x + 3, yFloor + 3, z + 4).setBlockData(stairs);
        chunk.getBlock(x + 4, yFloor + 3, z + 2).setType(Material.OAK_SLAB);
        stairs.setFacing(BlockFace.WEST);
        chunk.getBlock(x + 4, yFloor + 3, z + 3).setBlockData(stairs);
        chunk.getBlock(x + 4, yFloor + 3, z + 4).setType(Material.OAK_SLAB);
    }

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
    }

    /**
     * Get the minimum layer
     *
     * @return Minimum layer
     */
    @Override
    public int getMinimumLayer() {
        return LAYER_MIN;
    }

    /**
     * Get the maximum layer
     *
     * @return Maximum layer
     */
    @Override
    public int getMaximumLayer() {
        return LAYER_MAX;
    }
}