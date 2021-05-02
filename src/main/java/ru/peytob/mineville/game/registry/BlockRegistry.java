package ru.peytob.mineville.game.registry;

import ru.peytob.mineville.game.object.Block;
import ru.peytob.mineville.game.object.BlockBuilder;

public class BlockRegistry extends AbstractRegistry<Block> {
    private static final BlockRegistry instance = new BlockRegistry();

    private BlockRegistry() {
        BlockBuilder airBuilder = new BlockBuilder();
        airBuilder.setId((short) 0);
        airBuilder.setTextId("air");
        airBuilder.setName("Air");

        new RegistryModifier().add(new Block(airBuilder));
    }

    public static BlockRegistry getInstance() {
        return instance;
    }
}
