package ru.peytob.mineville.game.registry;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractRegistrable that = (AbstractRegistrable) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
