package ru.peytob.mineville.machine;

/**
 * State machine implementation.
 */
public class StateMachine implements IMachine {

    /** Current state of machine. */
    private State state;

    /**
     * Constructor that sets initial state.
     * @param _initialState initial state of machine
     */
    public StateMachine(State _initialState) {
        state = _initialState;
        state.setParent(this);
    }

    /**
     * Sets current state of machine.
     * @param _state state to be set
     */
    public void setState(IState _state) {
        state.destroy();
        state = (State) _state;
        state.setParent(this);
    }

    /**
     * Makes current state perform its task.
     */
    public void doSomething() {
        state.doSomething();
    }


    /**
     * Abstract state machine state.
     */
    protected abstract class State implements IState {
        /** Machine the state belongs to. */
        protected IMachine parent;

        /** Counter used to change the state. */
        protected int counter = 0;

        /**
         * Sets the machine the state belongs to.
         * @param _machine machine the state belong to
         */
        @Override
        public void setParent(IMachine _machine) {
            parent = _machine;
        }
    }


    /**
     * Class that demonstrates the state example.
     */
    protected class FirstState extends State {

        public FirstState() {
            System.out.println("SmtFirstState: start.");
        }

        @Override
        public void destroy() {
            System.out.println("SmtFirstState: destroy. We will remember you, bro.");
        }

        @Override
        public void doSomething() {
            counter++;
            if (counter == 30)
                parent.setState(new SecondState());
        }
    }

    /**
     * Class that demonstrates the state example.
     *
     */
    protected class SecondState extends State {
        public SecondState() {
            System.out.println("SmtSecondState: start.");
        }

        @Override
        public void destroy() {
            System.out.println("SmtSecondState: destroy.");
        }

        @Override
        public void doSomething() {
            counter++;
            if (counter == 120)
                parent.setState(new FirstState());
        }
    }

}
