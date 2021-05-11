package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.IMachine;
import ru.peytob.mineville.machine.IState;

public abstract class AgentState implements IState {

    /** A machine the state belongs to. */
    protected IMachine parent;

    @Override
    public abstract void act();

    @Override
    public void destroy() {

    }

    @Override
    public void setParent(IMachine _machine) {

    }

}