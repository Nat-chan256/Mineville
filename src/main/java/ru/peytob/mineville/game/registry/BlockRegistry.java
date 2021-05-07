package ru.peytob.mineville.game.registry;

import ru.peytob.mineville.game.object.Block;
import ru.peytob.mineville.game.object.BlockBuilder;
import ru.peytob.mineville.graphic.Mesh;

public class BlockRegistry extends AbstractRegistry<Block> {
    private static final BlockRegistry instance = new BlockRegistry();

    private BlockRegistry() {
        Mesh cubeMesh = new Mesh(new float[]{
                -0.5f, 0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture
        });

        BlockBuilder airBuilder = new BlockBuilder();
        airBuilder.setId((short) 0);
        airBuilder.setTextId("air");
        airBuilder.setName("Air");
        airBuilder.setMesh(cubeMesh);

        new RegistryModifier().add(new Block(airBuilder));
    }

    public static BlockRegistry getInstance() {
        return instance;
    }
}
