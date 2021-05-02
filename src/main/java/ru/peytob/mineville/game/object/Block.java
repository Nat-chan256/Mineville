package ru.peytob.mineville.game.object;

public class Block {
    private final String name;
    private final short id;

    public Block(String name, short id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public short getId() {
        return id;
    }
}
