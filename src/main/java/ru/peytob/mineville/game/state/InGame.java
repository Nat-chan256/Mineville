package ru.peytob.mineville.game.state;

import org.lwjgl.glfw.GLFW;
import ru.peytob.mineville.game.main.Game;
import ru.peytob.mineville.graphic.Mesh;
import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.math.Vec3;
import ru.peytob.mineville.opengl.shader.Shader;
import ru.peytob.mineville.opengl.shader.WorldShader;
import ru.peytob.mineville.system.WindowCallbackSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL33.*;

public class InGame extends AbstractState {
    Mesh mesh;

    float scale;
    Vec3 position;
    Vec3 rotation;

    private WorldShader shader;

    public InGame(Game _game) {
        super(_game);

        Shader vertexShader;
        Shader fragmentShader;
        try {
            vertexShader = new Shader(Files.readString(Path.of("src/main/resources/world.vert")), GL_VERTEX_SHADER);
            fragmentShader = new Shader(Files.readString(Path.of("src/main/resources/world.frag")), GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        shader = new WorldShader();
        shader.attachShader(vertexShader);
        vertexShader.destroy();
        shader.attachShader(fragmentShader);
        fragmentShader.destroy();
        shader.link();
        shader.use();
        shader.setModelMatrix(Mat4.computeIdentity());
        shader.setProjectionMatrix(Mat4.computeIdentity());
        shader.setViewMatrix(Mat4.computeIdentity());

        mesh = new Mesh(new float[]{
                0.5f, 0.5f, 0,
                0, 0, 0,
                0, 0,

                -0.5f, 0, 0,
                0, 0, 0,
                0, 0,

                0.5f, 0, 0,
                0, 0, 0,
                0, 0
        });

        scale = 1.0f;
        position = new Vec3(0, 0, 0);
        rotation = new Vec3(0, 0, 0);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT);
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
        result = Mat4.computeTranslation(position.x, position.y, position.z).multiplication(result);
        shader.setModelMatrix(result);
    }

    @Override
    public void draw() {
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
