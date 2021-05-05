package ru.peytob.mineville.game.state;

import org.lwjgl.glfw.GLFW;
import ru.peytob.mineville.game.main.Game;
import ru.peytob.mineville.graphic.Mesh;
import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.math.Vec3;
import ru.peytob.mineville.opengl.shader.WorldShader;
import ru.peytob.mineville.system.WindowCallbackSet;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL33.*;

public class InGame extends AbstractState {
    Mesh mesh;

    float scale;
    Vec3 position;
    Vec3 rotation;

    public InGame(Game _game) {
        super(_game);

        Vec3 _textureCoordinates = new Vec3(0, 0, 0);
        Vec3 _position = new Vec3(0, 0, 0);
        Vec3 _tileSizesAbs = new Vec3(0, 0, 0);

        mesh = new Mesh(new float[]{
                _position.x + -0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + -0.5f, _position.z + 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + -0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                _textureCoordinates.x + _tileSizesAbs.x, _textureCoordinates.y + _tileSizesAbs.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y, // texture

                _position.x + 0.5f, _position.y + 0.5f, _position.z + -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                _textureCoordinates.x, _textureCoordinates.y + _tileSizesAbs.y, // texture
        });

        scale = 1.0f;
        position = new Vec3(0, 0, 0);
        rotation = new Vec3(0, 0, 0);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glDepthMask(true);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void tick() {
        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_W) == GLFW_PRESS) {
            scale += 0.01;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_S) == GLFW_PRESS) {
            scale -= 0.01;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_UP) == GLFW_PRESS) {
            position.y += 0.01;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_DOWN) == GLFW_PRESS) {
            position.y -= 0.01;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_LEFT) == GLFW_PRESS) {
            position.x -= 0.01;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_RIGHT) == GLFW_PRESS) {
            position.x += 0.01;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_A) == GLFW_PRESS) {
            rotation.z -= 0.1;
        }

        if (glfwGetKey(game.getWindow().getPointer(), GLFW.GLFW_KEY_D) == GLFW_PRESS) {
            rotation.z += 0.1;
        }

        Mat4 result = Mat4.computeScaleMatrix(scale, scale, scale);
        result = Mat4.computeRotationZ(rotation.z).multiplication(result);
        result = Mat4.computeRotationY(1.5f).multiplication(result);
        result = Mat4.computeTranslation(position.x, position.y, position.z).multiplication(result);
        game.getShaderPack().getWorldShader().setModelMatrix(result);
    }

    @Override
    public void draw() {
        game.getShaderPack().getWorldShader().use();
        mesh.draw();
    }

    @Override
    public void onChange() {
        System.out.println("InGame: change");
    }

    @Override
    public void onLoad() {
        System.out.println("InGame: load");
        WindowCallbackSet cbs = new WindowCallbackSet();
        cbs.setKeyCallback((window, key, scan, action, mods) -> {
            if (key == GLFW.GLFW_KEY_Q) {
                game.stop();
            }
        });
        cbs.use(game.getWindow());
    }
}
