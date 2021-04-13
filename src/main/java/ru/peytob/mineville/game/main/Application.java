package ru.peytob.mineville.game.main;

import ru.peytob.mineville.game.state.InGame;
import ru.peytob.mineville.system.Window;

public class Application {
    private static boolean isInitialized = false;
    private static Window window;
    private static Game game;

    public static void initialize() {
        if (!isInitialized) {
            isInitialized = true;
            window = new Window("Mineville", 800, 600);
            game = new Game(new InGame(game));
        }
    }

    public static void run() {
        while (!window.isClose()) {
            window.pollEvents();
            game.tick();
            game.draw();
            window.display();
        }
    }

    public static void destroy() {
        game.destroy();
        window.destroy();
    }
}
