package ru.peytob.mineville.mas;

/** Observer interface. Used to implement "Observer" pattern. */
public interface IObserver {
    void update() throws InterruptedException;
}
