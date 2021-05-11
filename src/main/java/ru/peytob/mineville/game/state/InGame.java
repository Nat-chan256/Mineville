package ru.peytob.mineville.game.state;

import org.lwjgl.glfw.GLFW;
import ru.peytob.mineville.game.main.Game;
import ru.peytob.mineville.game.registry.BlockRegistry;
import ru.peytob.mineville.game.world.Octree;
import ru.peytob.mineville.graphic.Camera;
import ru.peytob.mineville.math.Vec2;
import ru.peytob.mineville.math.Vec3;
import ru.peytob.mineville.math.Vec3i;
import ru.peytob.mineville.system.Window;
import ru.peytob.mineville.system.WindowCallbackSet;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL33.*;

public class InGame extends AbstractState {
    Camera camera;

    Vec2 cursorPosition;

    Octree octree;

    public InGame(Game _game) {
        super(_game);

        cursorPosition = game.getWindow().getCursorPosition();

        octree = new Octree(new Vec3i());
        for (int i = 0; i < 16; ++i) {
            octree.setBlock(new Vec3i(i, i, i), BlockRegistry.getInstance().get((short) 1));
            octree.setBlock(new Vec3i(i, i, 15 - i), BlockRegistry.getInstance().get((short) 1));
            octree.setBlock(new Vec3i(15 - i, i, i), BlockRegistry.getInstance().get((short) 1));
            octree.setBlock(new Vec3i(15 - i, i, 15 - i), BlockRegistry.getInstance().get((short) 1));
        }

        camera = new Camera(new Vec3(0, 0, -10 ), 0,  (float) Math.toRadians(90),
                (float) Math.toRadians(75), 800.0f / 600.0f);

        game.getShaderPack().getWorldShader().setProjectionMatrix(camera.computeProjection());
        game.getShaderPack().getWorldShader().setViewMatrix(camera.computeView());

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

        if (!cameraOffset.equals(new Vec3())) {
            camera.move(cameraOffset);
            game.getShaderPack().getWorldShader().setViewMatrix(camera.computeView());
            System.out.println(camera.getPosition());
        }
    }

    @Override
    public void draw() {
        game.getShaderPack().getWorldShader().use();
        octree.draw();
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

            game.getShaderPack().getWorldShader().setViewMatrix(camera.computeView());

            cursorPosition.x = (float) dx;
            cursorPosition.y = (float) dy;
        });

        cbs.use(game.getWindow());
    }
}
