package ru.peytob.mineville.machine;

/**
 * State of state machine interface.
 */
public interface IState {

    /**
     * Frees up resources the state took up.
     */
    default void destroy() {

    }

    /**
     * Returns a state machine the state belongs to.
     * @return state machine
     */
    IMachine getParent();

    /**
     * The action that state must perform.
     */
    void act();
}
