package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.BehaviorTree.Context;

public class RepeatNode extends DecoratorNode {

    public RepeatNode(Context context) {
        super(context);
    }

    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) {
            state = NodeState.ERROR;
            throw new ChildException("Decorator has no child.");
        }

        NodeState childState = children.elementAt(0).tick();

        if (childState == NodeState.SUCCESS)
        {
            children.elementAt(0).setReady();
            state = NodeState.RUNNING;
        }
        else
        {
            state = childState;
        }

        return state;
    }

}
