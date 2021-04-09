package ru.peytob.mineville.machine.nodes;


import ru.peytob.mineville.machine.BehaviorTree.Context;

public abstract class LeafNode extends Node {

    public LeafNode(Context _context) {
        super(_context);
    }

    @Override
    public NodeState tick() {
        try
        {
            if (state == NodeState.READY)
            {
                state = NodeState.RUNNING;
            }
            return state;
        }
        finally
        {
            performTask();
        }
    }

    /**
     * Performing task given to this leaf node.
     * Assumes changing leaf node state
     *  */
    public void performTask() {
        state = NodeState.SUCCESS;
    }

}
