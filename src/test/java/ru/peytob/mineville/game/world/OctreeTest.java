package ru.peytob.mineville.game.world;

import org.junit.Test;
import ru.peytob.mineville.game.object.Block;
import ru.peytob.mineville.game.object.BlockBuilder;
import ru.peytob.mineville.math.Vec3i;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OctreeTest {
    @Test
    public void useScenario() {
        Vec3i position = new Vec3i(16, 32, 64);
        Octree octree = new Octree(position);

        assertEquals(0, octree.getBlocksInsideCount());
        assertEquals(position, octree.getPosition());
        assertEquals((short) 0, octree.getBlock(new Vec3i(6, 12, 5)).getId());

        BlockBuilder builder = new BlockBuilder();
        builder.setId((short) 1);
        Block block1 = new Block(builder);

        builder.setId((short) 2);
        Block block2 = new Block(builder);

        Vec3i firstBlockPosition = new Vec3i(1, 5, 3);
        octree. setBlock(firstBlockPosition, block1);

        assertEquals(1, octree.getBlocksInsideCount());
        assertEquals((short) 1, octree.getBlock(firstBlockPosition).getId());

        octree.setBlock(firstBlockPosition, block2);

        assertEquals(1, octree.getBlocksInsideCount());
        assertEquals((short) 2, octree.getBlock(firstBlockPosition).getId());

        octree.deleteBlock(new Vec3i(15, 15, 15));
        assertEquals(1, octree.getBlocksInsideCount());

        for (int i = 0; i < 10; ++i)  {
            octree.setBlock(new Vec3i(i, i, i), block2);
        }

        assertEquals(11, octree.getBlocksInsideCount());

        for (int i = 0; i < 10; i += 2)  {
            octree.setBlock(new Vec3i(i, i, i), block1);
        }

        assertEquals(11, octree.getBlocksInsideCount());

        for (int i = 0; i < 10; ++i)  {
            octree.deleteBlock(new Vec3i(i, i, i));
        }

        assertEquals(1, octree.getBlocksInsideCount());
    }
}