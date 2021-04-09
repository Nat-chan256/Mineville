package ru.peytob.mineville.machine;

public class SmtMachine implements IMachine {
    protected abstract class SmtState implements IState {
        protected IMachine parent;
        protected int counter = 0;

        @Override
        public void setParent(IMachine _machine) {
            parent = _machine;
        }
    }

    protected class SmtFirstState extends SmtState {
        public SmtFirstState() {
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
                parent.setState(new SmtSecondState());
        }
    }

    protected class SmtSecondState extends SmtState {
        public SmtSecondState() {
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
                parent.setState(new SmtFirstState());
        }
    }


    private SmtState state;

    public SmtMachine(SmtState _initialState) {
        state = _initialState;
        state.setParent(this);
    }

    public void setState(IState _state) {
        state.destroy();
        state = (SmtState) _state;
        state.setParent(this);
    }

    public void doSomething() {
        state.doSomething();
    }
}
