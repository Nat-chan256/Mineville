package ru.peytob.mineville.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL33.*;

public class Mesh {
    /**
     * Vertex Array Object.
     */
    private final int vao;

    /**
     * Vertex Buffer Object.
     */
    private final int vbo;

    /**
     * Sizes of array.
     */
    private final int vertexesSizes;

    public Mesh(float[] _vertices) {
        vertexesSizes = _vertices.length;

        FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertexesSizes);
        vertexData.put(_vertices);
        vertexData.flip();

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

        // Position.
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * 4, 0L);

        // Normal.
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * 4, 3L * 4L);

        // UV-coordinates.
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * 4, 6L * 4L);
    }

    /**
     * Deletes mesh data from memory.
     */
    public void destroy() {
        glDeleteBuffers(vao);
        glDeleteBuffers(vbo);
    }

    /**
     * Render object.
     */
    public void draw() {
        glBindVertexArray(vao);
        glDrawArrays(GL_TRIANGLES, 0, vertexesSizes / 3);
    }
}
