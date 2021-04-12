package ru.peytob.mineville.game.main;

import ru.peytob.mineville.game.state.IState;

public class Game {
    private IState state;

    public Game(IState _startState) {
        this.state = _startState;
    }

    public void tick() {
        state.tick();
    }

    public void draw() {
        state.draw();
    }

    public void destroy() {
    }

    public void setState(IState _next) {
        state.onChange();
        state = _next;
        state.onLoad();
    }
}
