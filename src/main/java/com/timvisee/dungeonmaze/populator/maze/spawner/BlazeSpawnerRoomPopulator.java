package com.timvisee.dungeonmaze.populator.maze.spawner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.timvisee.dungeonmaze.Core;
import com.timvisee.dungeonmaze.populator.maze.structure.AbandonedDefenceCastleRoomPopulator;
import com.timvisee.dungeonmaze.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.event.generation.GenerationSpawnerEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;

public class BlazeSpawnerRoomPopulator extends MazeRoomBlockPopulator {

	// TODO: Fix the treasure chests not spawning!

    /** General populator constants. */
	private static final int LAYER_MIN = 1;
	private static final int LAYER_MAX = 4;
	private static final float ROOM_CHANCE = .002f;

    /** Populator constants. */
	private static final double SPAWN_DISTANCE_MIN = 5; // Chunks

    // TODO: Implement this!
	public static final double CHANCE_SPANWER_ROOM_ADDITION_EACH_LEVEL = -0.167; /* to 1 */

	@Override
	public void populateRoom(MazeRoomBlockPopulatorArgs args) {
		final World world = args.getWorld();
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
		final int x = args.getRoomChunkX();
		final int y = args.getChunkY();
		final int yFloor = args.getFloorY();
		final int yCeiling = args.getCeilingY();
		final int z = args.getRoomChunkZ();
		
		// Make sure the distance between the spawn and the current chunk is allowed
		if(NumberUtils.distanceFromZero(chunk.getX(), chunk.getZ()) < SPAWN_DISTANCE_MIN)
			return;

        // Register the current room as constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        // Netherbrick floor in the bottom of the room
        for(int xx = x; xx <= x + 7; xx += 1)
            for(int zz = z; zz <= z + 7; zz += 1)
                chunk.getBlock(xx, yFloor, zz).setType(Material.NETHER_BRICKS);

        // Cobblestone layer underneath the stone floor
        for(int xx = x; xx <= x + 7; xx += 1)
            for(int zz = z + 1; zz <= z + 6; zz += 1)
                chunk.getBlock(xx, yFloor - 1, zz).setType(Material.COBBLESTONE);

        // Break out the walls and things inside the room
        for (int xx = 0; xx < 8; xx++)
            for (int yy = yFloor + 1; yy < yCeiling; yy++)
                for(int zz = 0; zz < 8; zz++)
                    chunk.getBlock(x + xx, yy, z + zz).setType(Material.AIR);

        // Generate corners
        for(int yy = yFloor + 1; yy < yCeiling; yy++) {
            chunk.getBlock(x, yy, z).setType(Material.NETHER_BRICKS);
            chunk.getBlock(x + 7, yy, z).setType(Material.NETHER_BRICKS);
            chunk.getBlock(x, yy, z + 7).setType(Material.NETHER_BRICKS);
            chunk.getBlock(x + 7, yy, z + 7).setType(Material.NETHER_BRICKS);
        }

        // Generate fences in the corners
        for(int yy = yFloor + 1; yy < yCeiling; yy++) {
            chunk.getBlock(x + 1, yy, z).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x, yy, z + 1).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 6, yy, z).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 7, yy, z + 1).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 1, yy, z + 7).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x, yy, z + 6).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 6, yy, z + 7).setType(Material.NETHER_BRICK_FENCE);
            chunk.getBlock(x + 7, yy, z + 6).setType(Material.NETHER_BRICK_FENCE);
        }

        // Generate platform in the middle
        for (int xx=x + 2; xx <= x + 5; xx+=1)
            for (int zz=z + 2; zz <= z + 5; zz+=1)
                chunk.getBlock(xx, yFloor + 1, zz).setType(Material.NETHER_BRICKS);

        // Generate stairs off the platform
        Stairs stairs = (Stairs)Material.NETHER_BRICK_STAIRS.createBlockData();
        stairs.setFacing(BlockFace.SOUTH);
        chunk.getBlock(x + 3, yFloor + 1, z + 2).setBlockData(stairs);
        chunk.getBlock(x + 4, yFloor + 1, z + 2).setBlockData(stairs);
        stairs.setFacing(BlockFace.WEST);
        chunk.getBlock(x + 3, yFloor + 1, z + 5).setBlockData(stairs);
        chunk.getBlock(x + 4, yFloor + 1, z + 5).setBlockData(stairs);

        stairs.setFacing(BlockFace.NORTH);
        chunk.getBlock(x + 2, yFloor + 1, z + 3).setBlockData(stairs);
        chunk.getBlock(x + 2, yFloor + 1, z + 4).setBlockData(stairs);
        stairs.setFacing(BlockFace.EAST);
        chunk.getBlock(x + 5, yFloor + 1, z + 3).setBlockData(stairs);
        chunk.getBlock(x + 5, yFloor + 1, z + 4).setBlockData(stairs);

        // Generate poles on the platform
        chunk.getBlock(x + 2, yFloor + 2, z + 2).setType(Material.NETHER_BRICK_FENCE);
        chunk.getBlock(x + 5, yFloor + 2, z + 2).setType(Material.NETHER_BRICK_FENCE);
        chunk.getBlock(x + 2, yFloor + 2, z + 5).setType(Material.NETHER_BRICK_FENCE);
        chunk.getBlock(x + 5, yFloor + 2, z + 5).setType(Material.NETHER_BRICK_FENCE);

        // Generate the spawner
        if(Core.getConfigHandler().isMobSpawnerAllowed("Blaze")) {
            int spawnerX = x + 3 + rand.nextInt(2);
            int spawnerY = yFloor + 2;
            int spawnerZ = z + 3 + rand.nextInt(2);
            Block spawnerBlock = chunk.getBlock(spawnerX, spawnerY, spawnerZ);

            // Call the spawner generation event
            GenerationSpawnerEvent event = new GenerationSpawnerEvent(spawnerBlock, EntityType.BLAZE, GenerationSpawnerEvent.GenerationSpawnerCause.BLAZE_SPAWNER_ROOM, rand);
            Bukkit.getServer().getPluginManager().callEvent(event);

            // Apply the generation
			event._apply();
        }

        // Generate hidden content/resources underneath the platform
        // Generate a list of chest contents
        List<ItemStack> contents = generateChestContents(rand);
        Block chest1 = chunk.getBlock(x + 3, yFloor, z + 3);

        // Call the chest generation event
        GenerationChestEvent event1 = new GenerationChestEvent(chest1, rand, contents, MazeStructureType.BLAZE_SPAWNER_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event1);

        // Spawn the chest unless the even is cancelled
        if(!event1.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event1.getBlock().getType() != Material.CHEST)
                return;

            // Add the contents to the chest
            ChestUtils.addItemsToChest(event1.getBlock(), event1.getContents(), !event1.getAddContentsInOrder(), rand);
        }

        // Generate a list of chest contents
        List<ItemStack> contents2 = generateChestContents(rand);
        Block chest2 = chunk.getBlock(x + 4, yFloor, z + 4);

        // Call the chest generation event
        GenerationChestEvent event2 = new GenerationChestEvent(chest2, rand, contents2, MazeStructureType.BLAZE_SPAWNER_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event2);

        // Spawn the chest unless the even is cancelled
        if(!event2.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event2.getBlock().getType() != Material.CHEST)
                return;

            // Add the contents to the chest
            ChestUtils.addItemsToChest(event2.getBlock(), event2.getContents(), !event2.getAddContentsInOrder(), rand);
        }
    }
	
	private List<ItemStack> generateChestContents(Random random) {
		return AbandonedDefenceCastleRoomPopulator.genChestContent(random);
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