package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.IMachine;
import ru.peytob.mineville.machine.IState;

/** State machine for an agent. */
public class AgentStateMachine implements IMachine, IObserver {

    /** An agent the state machine belongs to. */
    private Agent agent;

    /** Current state of state machine. */
    private AgentState currentState;

    /**
     * Constructor that sets an agent the state machine belongs to.
     * @param _agent an agent the state machine belongs to
     */
    public AgentStateMachine(Agent _agent)
    {
        agent = _agent;
    }

    public Agent getAgent()
    {
        return agent;
    }

    public Ontology getOntology()
    {
        return agent.getOntology();
    }

    public AgentState getState()
    {
        return currentState;
    }

    @Override
    public void setState(IState _state) throws InterruptedException {
        if (currentState != null) {
            currentState.interrupt();
        }
        currentState = (AgentState)_state;
    }

    @Override
    public void act() {
        currentState.act();
    }

    @Override
    public void update() throws InterruptedException {
        currentState.update();
    }
}
