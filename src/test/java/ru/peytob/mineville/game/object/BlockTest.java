package ru.peytob.mineville.game.object;

import static org.junit.Assert.*;
import org.junit.Test;

public class BlockTest {

    @Test
    public void testTestEquals() {
        BlockBuilder block1Builder = new BlockBuilder();
        block1Builder.setId((short) 1);
        block1Builder.setTextId("block1");
        Block block1 = new Block(block1Builder);

        BlockBuilder block2Builder = new BlockBuilder();
        block2Builder.setId((short) 2);
        block2Builder.setTextId("block2");
        Block block2 = new Block(block2Builder);

        BlockBuilder block3Builder = new BlockBuilder();
        block3Builder.setId((short) 2);
        block3Builder.setTextId("is equals to block2 object");
        Block block3 = new Block(block3Builder);

        BlockBuilder block4Builder = new BlockBuilder();
        block4Builder.setId((short) 3);
        block4Builder.setTextId("block2");
        Block block4 = new Block(block4Builder);

        assertNotEquals(block1, block2);
        assertNotEquals(block1, block3);
        assertNotEquals(block1, block4);

        assertEquals(block2, block3);
        assertEquals(block3, block2);
        assertNotEquals(block2, block4);

        assertNotEquals(block3, block4);
        assertNotEquals(block4, block3);

        assertEquals(block1, block1);

        assertNotEquals(block3, block1);
    }
}