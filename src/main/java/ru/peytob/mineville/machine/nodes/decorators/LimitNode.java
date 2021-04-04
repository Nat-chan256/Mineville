package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.BehaviorTree.Context;

public class LimitNode extends DecoratorNode {

    private int limit;
    private int counter;

    public LimitNode(Context context, int limit) {
        super(context);
        limit = limit;
    }

    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) throw new ChildException("Decorator has no child.");

        if (counter >= limit)
            return state;

        NodeState childState = children.elementAt(0).tick();
        state = childState;

        if (state == NodeState.SUCCESS) {
            counter++;
            children.elementAt(0).setReady();
            state = NodeState.RUNNING;
        }

        return state;
    }

}
