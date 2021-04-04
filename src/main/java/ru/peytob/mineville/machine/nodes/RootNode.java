package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.BehaviorTree.TaskController;
import ru.peytob.mineville.machine.BehaviorTree.Context;

public class RootNode extends Node {

    public RootNode(TaskController _taskController, Context _context) {
        super(_taskController, _context);
    }

    public RootNode(TaskController _taskController, Context _context, Node... _children) {
        super(_taskController, _context);
        for (Node child : _children)
            children.add(child);
    }

    @Override
    public NodeState tick() {
        boolean readyFlag = true;
        for (Node child : children) {
            NodeState childState;
            try {
                childState = child.tick();
            } catch (ChildException ex) {
                state = NodeState.ERROR;
                return state;
            }

            if (childState == NodeState.ERROR) {
                state = NodeState.ERROR;
                return state;
            } else if (childState == NodeState.FAIL) {
                state = NodeState.FAIL;
                return state;
            } else if (childState == NodeState.RUNNING) {
                state = NodeState.RUNNING;
                return state;
            } else if (childState == NodeState.SUCCESS)
                readyFlag = false;
        }

        if (readyFlag) return NodeState.READY;
        else return NodeState.SUCCESS;
    }

    @Override
    public void performTask() {
        for (Node child : children)
            try {
                child.performTask();
            } catch (ChildException ex) {
                state = NodeState.ERROR;
            }
    }
}
