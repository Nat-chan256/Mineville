package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.BehaviorTree.TaskController;
import ru.peytob.mineville.machine.BehaviorTree.Context;


public class SelectorNode extends Node {

    protected Node currentSubtask;

    public SelectorNode(TaskController _taskController, Context _context) {
        super(_taskController, _context);
        currentSubtask = null;
    }

    @Override
    public NodeState tick() {
        if (currentSubtask == null) {
            try {
                currentSubtask = children.elementAt(0);
            } catch (IndexOutOfBoundsException ex) {
                state = NodeState.ERROR;
                return state;
            }
        }

        try {
            state = currentSubtask.tick();
        } catch (ChildException ex) {
            state = NodeState.ERROR;
            return state;
        }

        if (state == NodeState.FAIL) {
            currentSubtask = nextChild(currentSubtask);
            if (currentSubtask == null)
                return state;
            else {
                state = NodeState.RUNNING;
                try {
                    currentSubtask.performTask();
                } catch (ChildException ex) {
                    state = NodeState.ERROR;
                }
                return state;
            }
        }

        return state;
    }

    @Override
    public void performTask() {
        try {
            currentSubtask.performTask();
        } catch (ChildException ex) {
            state = NodeState.ERROR;
        }
    }

    protected Node nextChild(Node _child) {
        for (int i = 0; i < children.size(); ++i)
            if (children.elementAt(i) == _child && i != children.size() - 1)
                return children.elementAt(i + 1);

        return null;
    }
}
