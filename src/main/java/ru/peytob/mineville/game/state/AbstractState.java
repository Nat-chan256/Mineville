package ru.peytob.mineville.game.state;

import ru.peytob.mineville.game.main.Game;

public abstract class AbstractState {
    Game game;

    public AbstractState(Game _game) {
        this.game = _game;
    }

    public abstract void clear();

    public abstract void tick();

    public abstract void draw();

    public abstract void onChange();

    public abstract void onLoad();
}
