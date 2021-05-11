package ru.peytob.mineville.game.object;

import ru.peytob.mineville.game.registry.AbstractRegistrable;
import ru.peytob.mineville.graphic.Mesh;

public class Block extends AbstractRegistrable {
    private final Mesh mesh;

    public Block(BlockBuilder _builder) {
        super(_builder.getTextId(), _builder.getId());

        this.mesh = _builder.getMesh();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Mesh getMesh() {
        return mesh;
    }
}
