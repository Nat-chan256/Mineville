package ru.peytob.mineville.machine.nodes.decorators;

import ru.peytob.mineville.machine.BehaviorTree.TaskController;
import ru.peytob.mineville.machine.BehaviorTree.Context;
import ru.peytob.mineville.machine.nodes.Node;

public abstract class DecoratorNode extends Node {

    public DecoratorNode(TaskController taskController, Context context) {
        super(taskController, context);
    }

    @Override
    public void addChild(Node _child) throws ChildException {
        //Декоратор не может иметь больше одного потомка
        if (children.size() == 1)
            throw new ChildException("Decorator node can't have more than one child.");
        else
            super.addChild(_child);
    }

    @Override
    public void performTask() throws ChildException {
        if (children.size() == 0) {
            state = NodeState.ERROR;
            throw new ChildException("Decorator has no child.");
        }

        children.elementAt(0).performTask();
    }
}
