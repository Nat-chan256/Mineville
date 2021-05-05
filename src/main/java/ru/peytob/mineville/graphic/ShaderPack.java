package ru.peytob.mineville.graphic;

import ru.peytob.mineville.opengl.shader.*;

public class ShaderPack {
    private final WorldShader worldShader;

    public ShaderPack(WorldShader worldShader) {
        this.worldShader = worldShader;
    }

    public WorldShader getWorldShader() {
        return worldShader;
    }
}
