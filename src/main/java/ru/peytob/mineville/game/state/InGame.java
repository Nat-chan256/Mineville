package ru.peytob.mineville.game.state;

import org.lwjgl.glfw.GLFW;
import ru.peytob.mineville.game.main.Game;
import ru.peytob.mineville.graphic.Camera;
import ru.peytob.mineville.graphic.Mesh;
import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.math.Vec2;
import ru.peytob.mineville.math.Vec3;
import ru.peytob.mineville.opengl.shader.WorldShader;
import ru.peytob.mineville.system.Window;
import ru.peytob.mineville.system.WindowCallbackSet;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL33.*;

public class InGame extends AbstractState {
    Mesh mesh;

    Camera camera;

    float scale;

    Vec2 cursorPosition;

    public InGame(Game _game) {
        super(_game);

        Vec3 _textureCoordinates = new Vec3(0, 0, 0);
        Vec3 _position = new Vec3(0, 0, 0);
        Vec3 _tileSizesAbs = new Vec3(0, 0, 0);

        cursorPosition = game.getWindow().getCursorPosition();

        camera = new Camera(new Vec3(0, 0, -10 ), 0,  (float) Math.toRadians(90),
                (float) Math.toRadians(75), 800.0f / 600.0f);

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
        game.getShaderPack().getWorldShader().setProjectionMatrix(camera.computeProjection());

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glDepthMask(true);
    }

    @Override
    public void clear() {
        glClearColor(0.0f, 0.5f, 0.5f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void tick() {
        Vec3 position = camera.getPosition();
        Window window = game.getWindow();

        float speed = 0.15f;
        Vec3 cameraOffset = new Vec3(0 ,0, 0);
        if (glfwGetKey(window.getPointer(), GLFW_KEY_UP) == GLFW_PRESS || glfwGetKey(window.getPointer(), GLFW_KEY_W) == GLFW_PRESS)
            cameraOffset = cameraOffset.plus(camera.getFront().multiplication(speed));

        if (glfwGetKey(window.getPointer(), GLFW_KEY_DOWN) == GLFW_PRESS || glfwGetKey(window.getPointer(), GLFW_KEY_S) == GLFW_PRESS)
            cameraOffset = cameraOffset.minus(camera.getFront().multiplication(speed));

        if (glfwGetKey(window.getPointer(), GLFW_KEY_LEFT) == GLFW_PRESS || glfwGetKey(window.getPointer(), GLFW_KEY_A) == GLFW_PRESS)
            cameraOffset = cameraOffset.minus(camera.getRight().multiplication(speed));

        if (glfwGetKey(window.getPointer(), GLFW_KEY_RIGHT) == GLFW_PRESS || glfwGetKey(window.getPointer(), GLFW_KEY_D) == GLFW_PRESS)
            cameraOffset = cameraOffset.plus(camera.getRight().multiplication(speed));

        camera.move(cameraOffset);

        Mat4 result = Mat4.computeScaleMatrix(scale, scale, scale);

        game.getShaderPack().getWorldShader().setModelMatrix(result);
        game.getShaderPack().getWorldShader().setViewMatrix(camera.computeView());
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

        cbs.setMouseMovingCallback((window, dx, dy) -> {
            camera.lookAround((float) (dx - cursorPosition.x) * 0.1f,
                    (float) -(dy - cursorPosition.y) * 0.1f);

            cursorPosition.x = (float) dx;
            cursorPosition.y = (float) dy;
        });

        cbs.use(game.getWindow());
    }
}
