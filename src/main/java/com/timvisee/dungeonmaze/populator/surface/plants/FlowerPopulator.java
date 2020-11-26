package com.timvisee.dungeonmaze.populator.surface.plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;

import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulator;
import com.timvisee.dungeonmaze.populator.surface.SurfaceBlockPopulatorArgs;
import org.bukkit.Tag;

public class FlowerPopulator extends SurfaceBlockPopulator {

    /** General populator constants. */
    private static final int CHUNK_ITERATIONS = 10;
    private static final float CHUNK_ITERATIONS_CHANCE = .15f;

	@Override
	public void populateSurface(SurfaceBlockPopulatorArgs args) {
		final Chunk chunk = args.getSourceChunk();
		final Random rand = args.getRandom();
        final int xFlower = rand.nextInt(16);
        final int zFlower = rand.nextInt(16);

        // Get the surface level at the location of the flower
        final int ySurface = args.getSurfaceLevel(xFlower, zFlower);

        // Make sure the surface block is grass
        if(chunk.getBlock(xFlower, ySurface, zFlower).getType() == Material.GRASS) {
            final int flowerY = ySurface + 1;

            // Spawn the flower
            if (rand.nextInt(2) == 0)
                chunk.getBlock(xFlower, flowerY, zFlower).setType(Material.SUNFLOWER);

            else {
                chunk.getBlock(xFlower, flowerY, zFlower).setType(getRandomFlowerType(rand));
            }
        }
	}
	
	/**
	 * Get a random flower type
	 * @param rand Random instance
	 * @return Random flower type ID
	 */
	public Material getRandomFlowerType(Random rand) {
		List<Material> flowers = new ArrayList<>(Tag.FLOWERS.getValues());
		return flowers.get(rand.nextInt(flowers.size()));
	}

    @Override
    public int getChunkIterations() {
        return CHUNK_ITERATIONS;
    }

    @Override
    public float getChunkIterationsChance() {
        return CHUNK_ITERATIONS_CHANCE;
    }
}