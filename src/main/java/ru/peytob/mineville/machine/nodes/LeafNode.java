package ru.peytob.mineville.machine.nodes;


import ru.peytob.mineville.machine.BehaviorTree.Context;

/**
 * Leaf node class.
 * Its inheritors perform specific tasks.
 */
public abstract class LeafNode extends Node {

    /**
     * Constructor that sets the link on the context.
     * @param _context context of the tree the node belong to
     */
    public LeafNode(Context _context) {
        super(_context);
    }

    /**
     * Performs task of the node and updates its state.
     * @return current state of the node
     */
    @Override
    public NodeState tick() {
        try {
            if (state == NodeState.READY) {
                state = NodeState.RUNNING;
            }
            return state;
        } finally {
            performTask();
        }
    }

    /**
     * Performing task given to this leaf node.
     * Assumes changing leaf node state
     */
    public void performTask() {
        state = NodeState.SUCCESS;
    }

}
