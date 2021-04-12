package ru.peytob.mineville.machine;

/**
 * State machine interface.
 */
public interface IMachine {

    /**
     * State of state machine interface.
     */
    interface IState {

        /**
         * Frees up resources the state took up.
         */
        default void destroy() {

        }

        /**
         * Sets the machine the state belongs to.
         * @param _machine machine the state belong to
         */
        void setParent(IMachine _machine);

        /**
         * The action that state must perform.
         */
        void doSomething();
    }

    /**
     * Sets the current state of machine.
     * @param state state to be set
     */
    void setState(IState state);

    /**
     * The action that current state of machine must perform.
     * @throws InterruptedException
     */
    void doSomething() throws InterruptedException;
}
