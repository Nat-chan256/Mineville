package ru.peytob.mineville.game.state;

import org.lwjgl.glfw.GLFW;
import ru.peytob.mineville.game.main.Game;
import ru.peytob.mineville.graphic.Mesh;
import ru.peytob.mineville.system.WindowCallbackSet;

public class InGame extends AbstractState {
    Mesh mesh;
    public InGame(Game _game) {
        super(_game);
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
