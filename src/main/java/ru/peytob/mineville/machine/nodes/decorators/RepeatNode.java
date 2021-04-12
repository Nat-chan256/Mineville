package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.BehaviorTree.Context;

/**
 * Repeat node class.
 * Makes its child perform until interrupted.
 */
public class RepeatNode extends DecoratorNode {

    /**
     * Constructor that sets the link on the context.
     * @param context context of the tree the node belong to
     */
    public RepeatNode(Context context) {
        super(context);
    }

    /**
     * Sends a signal to make child perform.
     * @return RUNNING
     * @throws ChildException when decorator has no child
     */
    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) {
            state = NodeState.ERROR;
            throw new ChildException("Decorator has no child.");
        }

        NodeState childState = children.get(0).tick();

        if (childState == NodeState.SUCCESS) {
            children.get(0).setReady();
            state = NodeState.RUNNING;
        } else {
            state = childState;
        }

        return state;
    }

}
