package ru.peytob.mineville.opengl.shader;

import ru.peytob.mineville.math.Mat4;

import static org.lwjgl.opengl.GL33.*;

public class WorldShader extends AbstractShaderProgram {
    private int modelMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;

    public WorldShader() {
        super();
    }

    @Override
    protected void searchUniforms() throws RuntimeException {
        final String modelMatrix = "u_model";
        modelMatrixLocation = glGetUniformLocation(id, modelMatrix);
        System.out.println(modelMatrixLocation);
        if (modelMatrixLocation == -1) {
            throw new RuntimeException("Uniform " + modelMatrix + " not found.");
        }

        final String projectionMatrix = "u_projection";
        projectionMatrixLocation = glGetUniformLocation(id, projectionMatrix);
        if (projectionMatrixLocation == -1) {
            throw new RuntimeException("Uniform " + projectionMatrix + " not found.");
        }

        final String viewMatrix = "u_view";
        viewMatrixLocation = glGetUniformLocation(id, viewMatrix);
        if (viewMatrixLocation == -1) {
            throw new RuntimeException("Uniform " + viewMatrix + " not found.");
        }
    }

    public void setModelMatrix(Mat4 _matrix) {
        glUniformMatrix4fv(modelMatrixLocation, false, _matrix.toFloatArray());
    }

    public void setViewMatrix(Mat4 _matrix) {
        glUniformMatrix4fv(viewMatrixLocation, false, _matrix.toFloatArray());
    }

    public void setProjectionMatrix(Mat4 _matrix) {
        glUniformMatrix4fv(projectionMatrixLocation, false, _matrix.toFloatArray());
    }
}
