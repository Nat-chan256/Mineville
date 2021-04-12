package ru.peytob.mineville.game.state;

public interface IState {
    void tick();

    void draw();

    void onChange();

    void onLoad();
}
