package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;

public class CreeperSpawnerRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 5;
	private static final float ROOM_CHANCE = .003f;

    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 5; // Chunks

    // TODO: Implement this!
    public static final double CHANCE_SPAWNER_ADDITION_EACH_LEVEL = -0.333; /* to 1 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		World world = args.getWorld();
		Chunk chunk = args.getSourceChunk();
		Random rand = args.getRandom();
		int x = args.getRoomChunkX();
		int y = args.getChunkY();
		int yFloor = args.getFloorY();
		int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk.getX(), chunk.getZ(), x, y, z);

        // Create the core
        chunk.getBlock(x + 3, yFloor + 1, z + 4).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 4, yFloor + 1, z + 3).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 3, yFloor + 1, z + 2).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 2, yFloor + 1, z + 3).setType(Material.NETHER_BRICKS);
        chunk.getBlock(x + 3, yFloor + 2, z + 3).setType(Material.NETHER_BRICKS);

        // Create the spawner
        if(Core.getConfigHandler().isMobSpawnerAllowed("Creeper")) {
            Block spawnerBlock = chunk.getBlock(x + 3, yFloor + 1, z + 3);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.CREEPER, GenerationSpawnerEvent.GenerationSpawnerCause.CREEPER_SPAWNER_ROOM, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the event
            event._apply();
        }
    }

    @Override
    public float getRoomChance() {
        return ROOM_CHANCE;
    }
	
	/**
	 * Get the minimum layer
	 * @return Minimum layer
	 */
	@Override
	public int getMinimumLayer() {
		return LAYER_MIN;
	}
	
	/**
	 * Get the maximum layer
	 * @return Maximum layer
	 */
	@Override
	public int getMaximumLayer() {
		return LAYER_MAX;
	}
}