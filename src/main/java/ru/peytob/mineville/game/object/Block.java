package ru.peytob.mineville.game.object;

import ru.peytob.mineville.game.registry.AbstractRegistrable;

import ru.peytob.mineville.graphic.BlockModel;

public class Block extends AbstractRegistrable {
    private final BlockModel model;

    public Block(BlockBuilder _builder) {
        super(_builder.getTextId(), _builder.getId());
        this.model = _builder.getModel();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public BlockModel getModel() {
        return model;
    }
}
