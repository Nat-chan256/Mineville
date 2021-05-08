package ru.peytob.mineville.machine.nodes.decorators;

import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.Node;

/**
 * The node that makes its child perform its tasks until its state is SUCCESS.
 */
public class UntilSuccessNode extends Node {

    /**
     * Constructor that sets the link on the context.
     *
     * @param context context of the tree the node belong to
     */
    public UntilSuccessNode(BehaviorTree.Context context) {
        super(context);
    }

    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) {
            state = NodeState.ERROR;
            throw new ChildException("Decorator has no child.");
        }

        NodeState childState = children.get(0).tick();

        if (childState == NodeState.FAIL) {
            children.get(0).setReady();
            state = NodeState.RUNNING;
        } else {
            state = childState;
        }

        return state;
    }
}
