package ru.peytob.mineville.game.main;

import ru.peytob.mineville.game.state.AbstractState;
import ru.peytob.mineville.game.state.EmptyState;
import ru.peytob.mineville.system.Window;

public class Game {
    private AbstractState state;
    private final Window window;

    public Game(Window _window) {
        setState(new EmptyState(this));
        this.window = _window;
    }

    public void clear() {
        state.clear();
    }

    public void tick() {
        state.tick();
    }

    public void draw() {
        state.draw();
    }

    public void stop() {
        window.close();
    }

    public void destroy() {
    }

    public void setState(AbstractState _next) {
        if (state != null) {
            state.onChange();
        }
        state = _next;
        state.onLoad();
    }

    public Window getWindow() {
        return window;
    }
}
