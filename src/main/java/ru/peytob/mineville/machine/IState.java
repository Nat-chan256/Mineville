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
     * Sets the machine the state belongs to.
     *
     * @param _machine machine the state belong to
     */
    void setParent(IMachine _machine);

    /**
     * The action that state must perform.
     */
    void act();
}
