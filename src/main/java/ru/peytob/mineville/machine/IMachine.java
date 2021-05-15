package ru.peytob.mineville.machine;

/**
 * State machine interface.
 */
public interface IMachine {

    /**
     * Sets the current state of machine.
     * @param state state to be set
     */
    void setState(IState state) throws InterruptedException;

    /**
     * The action that current state of machine must perform.
     * @throws InterruptedException
     */
    void act() throws InterruptedException;
}
