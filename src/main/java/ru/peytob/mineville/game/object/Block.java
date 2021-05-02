package ru.peytob.mineville.game.object;

import ru.peytob.mineville.game.registry.AbstractRegistrable;

public class Block extends AbstractRegistrable {
    public Block(BlockBuilder _builder) {
        super(_builder.getTextId(), _builder.getId());
    }
}
