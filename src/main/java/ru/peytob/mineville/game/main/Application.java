package ru.peytob.mineville.game.main;

import ru.peytob.mineville.game.object.Block;
import ru.peytob.mineville.game.object.BlockBuilder;
import ru.peytob.mineville.game.registry.BlockRegistry;
import ru.peytob.mineville.game.state.InGame;
import ru.peytob.mineville.graphic.BlockModel;
import ru.peytob.mineville.graphic.ShaderPack;
import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.opengl.shader.Shader;
import ru.peytob.mineville.opengl.shader.WorldShader;
import ru.peytob.mineville.system.Window;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class Application {
    private static boolean isInitialized = false;
    private static Window window;
    private static Game game;

    public static void initialize() {
        if (!isInitialized) {
            isInitialized = true;
            window = new Window("Mineville", 800, 600);

            loadBlocks();

            game = new Game(window, loadShaders());
            game.setState(new InGame(game));
        }
    }

    public static void run() {
        while (!window.isClose()) {
            window.pollEvents();
            game.tick();
            game.clear();
            game.draw();
            window.display();
        }
    }

    public static void destroy() {
        game.destroy();
        window.destroy();
    }

    public static ShaderPack loadShaders() {
        Shader vertexShader;
        Shader fragmentShader;
        try {
            vertexShader = new Shader(Files.readString(Path.of("src/main/resources/world.vert")), GL_VERTEX_SHADER);
            fragmentShader = new Shader(Files.readString(Path.of("src/main/resources/world.frag")), GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }

        WorldShader shader = new WorldShader();
        shader.attachShader(vertexShader);
        vertexShader.destroy();
        shader.attachShader(fragmentShader);
        fragmentShader.destroy();
        shader.link();
        shader.use();
        shader.setModelMatrix(Mat4.computeIdentity());
        shader.setProjectionMatrix(Mat4.computeIdentity());
        shader.setViewMatrix(Mat4.computeIdentity());

        return new ShaderPack(shader);
    }

    public static void loadBlocks() {
        // todo Create block loader.

        float[] northSide = new float[] {
                0.5f, 0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                0.0f, 0.0f, 1.0f, // normal
                0.0f, 0.0f, // texture
        };

        float[] southSide = new float[] {
                -0.5f, 0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                0.0f, 0.0f, -1.0f, // normal
                0.0f, 0.0f, // texture
        };

        float[] westSide = new float[] {
                -0.5f, 0.5f, 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                -1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture
        };

        float[] eastSide = new float[] {
                0.5f, 0.5f, -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                1.0f, 0.0f, 0.0f, // normal
                0.0f, 0.0f, // texture
        };

        float[] topSide = new float[] {
                -0.5f, 0.5f, -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, 0.5f, -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, 0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, 0.5f, -0.5f, // position
                0.0f, 1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture
        };

        float[] bottomSide = new float[] {
                0.5f, -0.5f, -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                0.5f, -0.5f, -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, 0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture

                -0.5f, -0.5f, -0.5f, // position
                0.0f, -1.0f, 0.0f, // normal
                0.0f, 0.0f, // texture
        };

        BlockModel model = new BlockModel(northSide, southSide, westSide, eastSide, topSide, bottomSide);

        BlockBuilder grassBuilder = new BlockBuilder();
        grassBuilder.setId((short) 1);
        grassBuilder.setTextId("grass");
        grassBuilder.setName("Grass");
        grassBuilder.setModel(model);

        BlockRegistry.getInstance().getRegistryModifier().add(new Block(grassBuilder));
    }
}
