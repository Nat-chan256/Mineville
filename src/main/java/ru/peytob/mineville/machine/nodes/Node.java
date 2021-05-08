package ru.peytob.mineville.machine.nodes;

import java.util.ArrayList;
import java.util.List;

import ru.peytob.mineville.machine.BehaviorTree.Context;

/**
 * Node class.
 * Represents Behavior Tree abstract node.
 */
public abstract class Node implements INode {

    /** The list of the node's children. */
    protected List<Node> children;

    /** Context of tree the node belongs to. Keeps all necessary variables. */
    protected Context context;

    /** Current subtask to be performed. */
    protected Node currentSubtask;

    /** Current state of node. */
    protected NodeState state;

    /**
     * Constructor that sets the link on the context.
     * @param _context context of the tree the node belong to
     */
    public Node(Context _context) {
        children = new ArrayList<Node>();
        state = NodeState.READY;
        context = _context;
    }

    public List<Node> getChildren() {
        return children;
    }

    public NodeState getState() {
        return state;
    }

    /**
     * Adding the child node.
     * @param _child child to be added
     * @throws ChildException may be thrown when the node has specific rules for child adding.
     */
    public void addChild(Node _child) throws ChildException {
        children.add(_child);
        if (children.size() == 1)
            currentSubtask = children.get(0);
    }


    /**
     * Updates the node's state and makes its children perform if they exist.
     * @return current state of the node
     * @throws ChildException when node has no children although it supposed to have
     */
    public abstract NodeState tick() throws ChildException;


    /**
     * Set state as READY for this node and all its children.
     */
    public void setReady() {
        state = NodeState.READY;
        for (Node child : children)
            child.setReady();
    }

    /**
     * Sets the specific state of node.
     * @param _state state to be set
     */
    public void setState(NodeState _state) {
        state = _state;
    }


    /**
     * All possible node states.
     */
    public enum NodeState {
        SUCCESS, FAIL, RUNNING, ERROR, READY
    }

}
