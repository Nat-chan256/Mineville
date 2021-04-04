package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.BehaviorTree.TaskController;
import ru.peytob.mineville.machine.BehaviorTree.Context;

//Лучше этот класс пока не использовать

public class ParallelNode extends Node {

    private ParallelPolicy parallelPolicy;

    public ParallelNode(TaskController _taskController, Context _context) {
        super(_taskController, _context);
        parallelPolicy = ParallelPolicy.SEQUENCE;
    }

    public ParallelNode(TaskController _taskController, Context _context, ParallelPolicy _parallelPolicy) {
        super(_taskController, _context);
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

    @Override
    public void performTask() {
        state = NodeState.RUNNING;
        for (Node child : children)
            try {
                child.performTask();
            } catch (ChildException ex) {
                state = NodeState.ERROR;
            }
    }

    public enum ParallelPolicy {
        SEQUENCE, SELECTOR
    }
}
