package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.IMachine;
import ru.peytob.mineville.machine.IState;

/** State machine for an agent. */
public class AgentStateMachine implements IMachine {

    private Agent agent;

    /** Current state of state machine. */
    private AgentState currentState;

    public Ontology getOntology()
    {
        return agent.getOntology();
    }

    @Override
    public void setState(IState _state) {
        currentState = (AgentState)_state;
    }

    @Override
    public void act() {
        currentState.act();
    }
}
