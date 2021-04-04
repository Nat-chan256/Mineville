package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.BehaviorTree.TaskController;
import ru.peytob.mineville.machine.BehaviorTree.Context;

public abstract class LeafNode extends Node {

    public LeafNode(TaskController _taskController, Context _context) {
        super(_taskController, _context);
    }

    @Override
    public NodeState tick() {
        if (state == NodeState.READY)
            performTask();
        return state;
    }

    @Override
    public void performTask() {
        taskController.performTask(this);
    }

    public void doAction() {
        state = NodeState.RUNNING;
    }
}
