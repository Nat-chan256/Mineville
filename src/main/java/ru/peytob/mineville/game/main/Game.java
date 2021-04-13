package ru.peytob.mineville.game.main;

import ru.peytob.mineville.game.state.AbstractState;

public class Game {
    private AbstractState state;

    public Game(AbstractState _startState) {
        setState(_startState);
    }

    public void tick() {
        state.tick();
    }

    public void draw() {
        state.draw();
    }

    public void destroy() {
    }

    public void setState(AbstractState _next) {
        state.onChange();
        state = _next;
        state.onLoad();
    }
}
