package ru.peytob.mineville.machine.nodes;


import ru.peytob.mineville.machine.BehaviorTree.Context;

//Лучше этот класс пока не использовать

public class ParallelNode extends Node {

    private ParallelPolicy parallelPolicy;

    public ParallelNode(Context _context) {
        super(_context);
        parallelPolicy = ParallelPolicy.SEQUENCE;
    }

    public ParallelNode(Context _context, ParallelPolicy _parallelPolicy) {
        super(_context);
        parallelPolicy = _parallelPolicy;
    }

    @Override
    public NodeState tick() {
        if (parallelPolicy == ParallelPolicy.SEQUENCE) {
            for (Node child : children) {
                NodeState childState;
                try {
                    childState = child.tick();
                } catch (ChildException ex) {
                    state = NodeState.ERROR;
                    return state;
                }

                if (childState == NodeState.FAIL) {
                    state = NodeState.FAIL;
                    return state;
                } else if (childState == NodeState.ERROR) {
                    state = NodeState.ERROR;
                    return state;
                } else if (childState == NodeState.RUNNING) {
                    state = NodeState.RUNNING;
                    return state;
                }
            }
            state = NodeState.SUCCESS;
            return state;
        } else // parallelPolicy == ParallelPolicy.SELECTOR
        {
            boolean failState = true;
            for (Node child : children) {
                NodeState childState;
                try {
                    childState = child.tick();
                } catch (ChildException ex) {
                    state = NodeState.ERROR;
                    return state;
                }

                if (childState == NodeState.SUCCESS) {
                    state = NodeState.SUCCESS;
                    return state;
                } else if (childState == NodeState.ERROR) {
                    state = NodeState.ERROR;
                    return state;
                } else if (childState == NodeState.RUNNING) {
                    state = NodeState.RUNNING;
                    failState = false;
                }
            }
            if (failState) state = NodeState.FAIL;
            return state;
        }
    }

    public enum ParallelPolicy {
        SEQUENCE, SELECTOR
    }
}
