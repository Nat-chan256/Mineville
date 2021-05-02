package ru.peytob.mineville.game.registry;

public abstract class AbstractRegistrable {
    private final String textId;
    private final Short id;

    public AbstractRegistrable(String textId, Short id) {
        this.textId = textId;
        this.id = id;
    }

    public String getTextId() {
        return textId;
    }

    public Short getId() {
        return id;
    }
}
