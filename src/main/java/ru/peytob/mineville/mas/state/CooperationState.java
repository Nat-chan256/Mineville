package ru.peytob.mineville.mas.state;

import ru.peytob.mineville.machine.IBehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.mas.AgentState;
import ru.peytob.mineville.mas.AgentStateMachine;

public class CooperationState extends AgentState {

    /** The state that state machine had before. */
    private AgentState previousState;

    /**
     * Constructor that sets the behavior tree and a state machine the state belongs to.
     * It also sets the state that state machine had before and the prototype.
     * The prototype is a state whose clone will be assigned to this instance.
     * @param _previousState previous state of the state machine
     * @param _prototype a state whose clone will be assigned to this instance
     */
    public CooperationState(AgentState _previousState, AgentState _prototype)
            throws ChildException {
        super(_prototype);
        previousState = _previousState;
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {
        return null;
    }

    @Override
    public AgentState clone() {
        try {
            return new CooperationState(previousState, this);
        }
        catch (ChildException ex)
        {
            System.out.println("Cloning cooperation state error: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public void update() throws InterruptedException {
        if (this.getBehaviorTree().getState() == Node.NodeState.SUCCESS ||
                this.getBehaviorTree().getState() == Node.NodeState.FAIL) {
            this.getParent().setState(previousState);
            this.getParent().getAgent().
                    getMessagesDeliveryService().
                    removeSender(
                            this.getParent().getAgent()
                    );
        }
    }
}
