package com.timvisee.dungeonmaze.populator.maze.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.timvisee.dungeonmaze.DungeonMaze;
import com.timvisee.dungeonmaze.event.generation.GenerationChestEvent;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulator;
import com.timvisee.dungeonmaze.populator.maze.MazeRoomBlockPopulatorArgs;
import com.timvisee.dungeonmaze.populator.maze.MazeStructureType;
import com.timvisee.dungeonmaze.util.ChestUtils;

public class LibraryRoomPopulator extends MazeRoomBlockPopulator {

    /** General populator constants. */
	private static final int LAYER_MIN = 3;
	private static final int LAYER_MAX = 7;
	private static final float ROOM_CHANCE = .002f;

    // TODO: Implement this feature!
	public static final double CHANCE_LIBRARY_ADDITION_EACH_LEVEL = -0.167; /* to 1 */

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

        // Register the current room als constant room
        DungeonMaze.instance.registerConstantRoom(world.getName(), chunk, x, y, z);

        //stone floor in the bottom of the room
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                chunk.getBlock(x2, yFloor, z2).setType(Material.STONE);

        // Cobblestone layer underneath the stone floor
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
                chunk.getBlock(x2, yFloor - 1, z2).setType(Material.COBBLESTONE);

        // Make stone walls on each side of the room
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                chunk.getBlock(x2, y2, z).setType(Material.STONE_BRICKS);
        for(int x2 = x + 1; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                chunk.getBlock(x2, y2, z + 7).setType(Material.STONE_BRICKS);
        for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                chunk.getBlock(x, y2, z2).setType(Material.STONE_BRICKS);
        for(int z2 = z + 1; z2 <= z + 6; z2 += 1)
            for(int y2 = yFloor; y2 <= yCeiling + 5; y2 += 1)
                chunk.getBlock(x + 7, y2, z2).setType(Material.STONE_BRICKS);

        // Generate some holes in the wall to make some kind of doors
        for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x2, y2, z).setType(Material.AIR);
        for(int x2 = x + 3; x2 <= x + 4; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x2, y2, z + 7).setType(Material.AIR);
        for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x, y2, z2).setType(Material.AIR);
        for(int z2 = z + 3; z2 <= z + 4; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x + 7, y2, z2).setType(Material.AIR);

        // Generate the bookshelves, one on each side
        for(int x2 = x + 5; x2 <= x + 6; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x2, y2, z + 1).setType(Material.BOOKSHELF);
        for(int x2 = x + 1; x2 <= x + 2; x2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x2, y2, z + 6).setType(Material.BOOKSHELF);
        for(int z2 = z + 1; z2 <= z + 2; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x + 1, y2, z2).setType(Material.BOOKSHELF);
        for(int z2 = z + 5; z2 <= z + 6; z2 += 1)
            for(int y2 = yFloor + 1; y2 <= yFloor + 3; y2 += 1)
                chunk.getBlock(x + 6, y2, z2).setType(Material.BOOKSHELF);

        /* // Make the two pilars - Change to enchant table
        for (int y2 = yFloor + 1; y2 <= yFloor + 3; y2+=1) {
            c.getBlock(x + 3, y2, z + 4).setType(Material.PUMPKIN);
            c.getBlock(x + 4, y2, z + 3).setType(Material.PUMPKIN);
        } */

        // Add enchant tables supports
        chunk.getBlock(x + 3, yFloor +1, z + 4).setType(Material.BOOKSHELF);
        chunk.getBlock(x + 4, yFloor +1, z + 3).setType(Material.BOOKSHELF);
        // Add the two enchant tables
        chunk.getBlock(x + 3, yFloor +2, z + 4).setType(Material.ENCHANTING_TABLE);
        chunk.getBlock(x + 4, yFloor +2, z + 3).setType(Material.ENCHANTING_TABLE);
        // Add the two chests
        chunk.getBlock(x + 3, yFloor + 1, z + 3).setType(Material.CHEST);

        // Call the Chest generation event
        GenerationChestEvent event = new GenerationChestEvent(chunk.getBlock(x + 3, yFloor + 1, z + 3), rand, genChestContent(rand), MazeStructureType.LIBRARY_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event);

        // Do the event
        if(!event.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        chunk.getBlock(x + 4, yFloor + 1, z + 4).setType(Material.CHEST);

        // Call the Chest generation event
        GenerationChestEvent event2 = new GenerationChestEvent(chunk.getBlock(x + 4, yFloor + 1, z + 4), rand, genChestContent(rand), MazeStructureType.LIBRARY_ROOM);
        Bukkit.getServer().getPluginManager().callEvent(event2);

        // Do the event
        if(!event2.isCancelled()) {
            // Make sure the chest is still there, a developer could change the chest through the event!
            if(event2.getBlock().getType() == Material.CHEST)
                // Add the contents to the chest
                ChestUtils.addItemsToChest(event.getBlock(), event.getContents(), !event.getAddContentsInOrder(), rand);
        }

        // Add 4 lanterns on each side of the room near the book shelves
        chunk.getBlock(x + 2, yFloor + 2, z + 1).setType(Material.LANTERN);
        chunk.getBlock(x + 6, yFloor + 2, z + 2).setType(Material.LANTERN);
        chunk.getBlock(x + 1, yFloor + 2, z + 5).setType(Material.LANTERN);
        chunk.getBlock(x + 5, yFloor + 2, z + 6).setType(Material.LANTERN);
	}
	
	public List<ItemStack> genChestContent(Random random) {
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