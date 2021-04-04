package ru.peytob.mineville.machine.nodes;


import ru.peytob.mineville.machine.BehaviorTree.Context;


public class SelectorNode extends Node {

    protected Node currentSubtask;

    public SelectorNode(Context _context) {
        super(_context);
        currentSubtask = null;
    }

    @Override
    public void setReady()
    {
        state = NodeState.READY;
        if (children.size() == 0)
        {
            return;
        }
        currentSubtask = children.elementAt(0);
        for (Node child : children)
        {
            try {
                child.setReady();
            }
            catch(ChildException ex)
            {
                continue;
            }
        }
    }


    /** Add the child.
     * If the child is the first one for this node, sets this child as current subtask.
     * @param child node to add to the children list
     * */
    @Override
    public void addChild(Node child)
    {
        children.add(child);
        if (children.size() == 1)
        {
            currentSubtask = children.elementAt(0);
        }
    }


    @Override
    public NodeState tick() {
        if (children.size() == 0)
        {
            state = NodeState.ERROR;
            return state;
        }

        if (currentSubtask == null)
        {
            return state;
        }

        try {
            state = currentSubtask.tick();
        } catch (ChildException ex) {
            state = NodeState.ERROR;
            return state;
        }

        if (state == NodeState.FAIL)
        {
            currentSubtask = nextChild(currentSubtask);
            tick();
        }
        else if (state == NodeState.SUCCESS)
        {
            currentSubtask = children.elementAt(0);
            return state;
        }

        if (currentSubtask != null)
        {
            state = NodeState.RUNNING;
        }

        return state;
    }

    protected Node nextChild(Node _child) {
        for (int i = 0; i < children.size(); ++i)
            if (children.elementAt(i) == _child && i != children.size() - 1)
                return children.elementAt(i + 1);

        return null;
    }
}
