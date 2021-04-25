package ru.peytob.mineville.game.state;

import org.lwjgl.glfw.GLFW;
import ru.peytob.mineville.game.main.Game;
import ru.peytob.mineville.graphic.Mesh;
import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.opengl.shader.Shader;
import ru.peytob.mineville.opengl.shader.WorldShader;
import ru.peytob.mineville.system.WindowCallbackSet;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL33.GL_VERTEX_SHADER;

public class InGame extends AbstractState {
    Mesh mesh;
    private WorldShader shader;

    public InGame(Game _game) {
        super(_game);

        Shader vertexShader = new Shader("#version 330 core\n" +
                "\n" +
                "layout (location = 0) in vec3 l_position;\n" +
                "layout (location = 1) in vec3 l_normal;\n" +
                "layout (location = 2) in vec2 l_texture;\n" +
                "\n" +
                "uniform mat4 u_projection;\n" +
                "uniform mat4 u_view;\n" +
                "uniform mat4 u_model;\n" +
                "\n" +
                "out VS_OUT\n" +
                "{\n" +
                "\tvec2 texture;\n" +
                "} VSO;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "\tgl_Position = vec4(l_position, 1.0);//u_projection * u_view * u_model * vec4(l_position, 1.0);\n" +
                "\tVSO.texture = l_texture; // Для красоты на время :З\n" +
                "}", GL_VERTEX_SHADER);

        Shader fragmentShader = new Shader("#version 330 core\n" +
                "\n" +
                "uniform sampler2D ut_diffuseAtlas;\n" +
                "\n" +
                "in VS_OUT\n" +
                "{\n" +
                "\tvec2 texture;\n" +
                "} VSO;\n" +
                "\n" +
                "out vec4 fsout_color;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "\tfsout_color = vec4(1, 1, 1, 0);\n" +
                "}", GL_FRAGMENT_SHADER);

        shader = new WorldShader();
        shader.attachShader(vertexShader);
        vertexShader.destroy();
        shader.attachShader(fragmentShader);
        fragmentShader.destroy();
        shader.link();
        shader.use();
        shader.setModelMatrix(new Mat4());
        shader.setProjectionMatrix(new Mat4());
        shader.setViewMatrix(new Mat4());

        mesh = new Mesh(new float[] {
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
    }

    @Override
    public void tick() {
//        System.out.println("InGame: tick()");
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
