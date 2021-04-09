package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.BehaviorTree.Context;
import ru.peytob.mineville.machine.nodes.Node;

public abstract class DecoratorNode extends Node {

    public DecoratorNode(Context context) {
        super(context);
    }

    @Override
    public void addChild(Node _child) throws ChildException {
        //Декоратор не может иметь больше одного потомка
        if (children.size() == 1)
            throw new ChildException("Decorator node can't have more than one child.");
        else
            super.addChild(_child);
    }
}
