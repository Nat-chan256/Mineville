package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.mas.Ontology;

/**
 * Selector node.
 * Consistently performs its children tasks until one of them succeeds.
 */
public class SelectorNode extends Node {

    /**
     * Constructor that sets the link on the context.
     * @param _ontology context of the tree the node belong to
     */
    public SelectorNode(Ontology _ontology) {
        super(_ontology);
        currentSubtask = null;
    }

    /**
     * Set state as READY for this node and all its children.
     * Also resets current subtask.
     */
    @Override
    public void setReady() {
        super.setReady();
        if (children.size() > 0)
        {
            currentSubtask = children.get(0);
        }
    }


    /**
     * Adding the child.
     * If the child is the first one for this node, sets this child as current subtask.
     * @param _child node to add to the children list
     */
    @Override
    public void addChild(Node _child) {
        children.add(_child);
        if (children.size() == 1) {
            currentSubtask = children.get(0);
        }
    }


    /**
     * Updates the node's state and makes its children perform their tasks.
     * @return SUCCESS if current subtask returned SUCCESS;
     *         RUNNING if current subtask returned RUNNING or FAIL;
     *         FAIL if current subtask returned FAIL and it appeared the last subtask from children list;
     *         child node's state otherwise
     */
    @Override
    public NodeState tick() {
        if (children.size() == 0) {
            state = NodeState.ERROR;
            return state;
        }

        if (currentSubtask == null) {
            return state;
        }

        try {
            state = currentSubtask.tick();
        } catch (ChildException ex) {
            state = NodeState.ERROR;
            return state;
        }

        if (state == NodeState.FAIL) {
            currentSubtask = nextChild(currentSubtask);
            tick();
        } else if (state == NodeState.SUCCESS) {
            currentSubtask = children.get(0);
            return state;
        }

        if (currentSubtask != null) {
            state = NodeState.RUNNING;
        }

        return state;
    }

    /**
     * Finds the next child in children list.
     * @param _child child node whose successor we want to find
     * @return the next child in the children list or the one if _child is last in the list
     */
    protected Node nextChild(Node _child) {
        for (int i = 0; i < children.size(); ++i)
            if (children.get(i) == _child && i != children.size() - 1)
                return children.get(i + 1);

        return null;
    }
}
