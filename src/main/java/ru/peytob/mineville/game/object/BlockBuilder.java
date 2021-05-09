package ru.peytob.mineville.game.object;

import ru.peytob.mineville.graphic.BlockModel;

public class BlockBuilder {
    private String textId;
    private Short id;
    private String name;
    private BlockModel model;

    public BlockBuilder() {
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlockModel getModel() {
        return model;
    }

    public void setModel(BlockModel mesh) {
        this.model = mesh;
    }
}
