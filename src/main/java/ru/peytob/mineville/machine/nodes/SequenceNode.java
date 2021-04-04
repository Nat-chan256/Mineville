package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.BehaviorTree.TaskController;
import ru.peytob.mineville.machine.BehaviorTree.Context;

public class SequenceNode extends Node {

    public SequenceNode(TaskController _taskController, Context _context) {
        super(_taskController, _context);
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

        if (state == NodeState.SUCCESS)
            currentSubtask = nextChild(currentSubtask);

        if (currentSubtask != null)
            state = NodeState.RUNNING;

        return state;
    }

    @Override
    public void performTask() throws ChildException {
        if (currentSubtask == null)
            try {
                currentSubtask = children.elementAt(0);
            } catch (IndexOutOfBoundsException ex) {
                state = NodeState.ERROR;
                throw new ChildException("Sequence node has no children");
            }
        try {
            currentSubtask.performTask();
        } catch (ChildException ex) {
            state = NodeState.ERROR;
        }
    }

    @Override
    public void setReady() throws ChildException {
        super.setReady();
        try {
            currentSubtask = children.elementAt(0);
        } catch (IndexOutOfBoundsException ex) {
            state = NodeState.ERROR;
            throw new ChildException("Sequence node has no children");
        }
    }

    protected Node nextChild(Node _child) {
        for (int i = 0; i < children.size(); ++i)
            if (children.elementAt(i) == _child && i != children.size() - 1)
                return children.elementAt(i + 1);

        return null;
    }

}
