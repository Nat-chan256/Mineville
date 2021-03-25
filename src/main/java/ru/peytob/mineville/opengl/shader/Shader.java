package ru.peytob.mineville.opengl.shader;

import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    private final int id;
    private final int type;

    public Shader(String _code, int _type) throws RuntimeException {
        type = _type;
        id = glCreateShader(type);
        GL33.glShaderSource(id, _code);
        GL33.glCompileShader(id);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer intBuffer = stack.mallocInt(1);
            GL33.glGetShaderiv(id, GL33.GL_COMPILE_STATUS, intBuffer);
            if (intBuffer.get(0) == 0) {
                throw new RuntimeException("Shader not compiled: " + GL33.glGetShaderInfoLog(id));
            }
        }
    }

    public void destroy() {
        glDeleteShader(id);
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }
}
