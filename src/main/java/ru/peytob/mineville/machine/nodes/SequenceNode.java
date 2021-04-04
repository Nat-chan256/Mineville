package ru.peytob.mineville.machine.nodes;


import ru.peytob.mineville.machine.BehaviorTree.Context;

public class SequenceNode extends Node {

    public SequenceNode(Context _context) {
        super(_context);
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

        if (state == NodeState.SUCCESS)
        {
            currentSubtask = nextChild(currentSubtask);
            tick();
        }
        else if (state == NodeState.FAIL)
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
