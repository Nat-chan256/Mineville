package ru.peytob.mineville.machine;

public interface IMachine {
    interface IState {
        default void destroy() {

        }

        void setParent(IMachine machine);

        void doSomething();
    }

    void setState(IState state);

    void doSomething() throws InterruptedException;
}
