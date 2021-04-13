package ru.peytob.mineville.game.state;

import ru.peytob.mineville.game.main.Game;

public class InGame extends AbstractState {
    public InGame(Game _game) {
        super(_game);
    }

    @Override
    public void tick() {
        System.out.println("InGame: tick()");
    }

    @Override
    public void draw() {
        System.out.println("InGame: draw()");
    }

    @Override
    public void onChange() {
        System.out.println("InGame: change");
    }

    @Override
    public void onLoad() {
        System.out.println("InGame: load");
    }
}
