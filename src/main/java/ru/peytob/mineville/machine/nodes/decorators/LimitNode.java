package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.BehaviorTree.Context;

public class LimitNode extends DecoratorNode {

    private int limit;
    private int counter;

    public LimitNode(Context context, int limit) {
        super(context);
        this.limit = limit;
    }

    /** Sets the node state as "ready" and nullifies the counter. */
    @Override
    public void setReady() throws ChildException
    {
        super.setReady();
        counter = 0;
    }

    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) throw new ChildException("Decorator has no child.");

        if (counter >= limit)
        {
            return state;
        }
        
        state = children.elementAt(0).tick();

        if (state == NodeState.SUCCESS) {
            counter++;
            if (counter == limit)
            {
                return state;
            }
            children.elementAt(0).setReady();
            children.elementAt(0).tick();
            state = NodeState.RUNNING;
        }

        return state;
    }

}
