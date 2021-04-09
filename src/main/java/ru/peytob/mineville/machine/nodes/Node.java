package ru.peytob.mineville.machine.nodes;

import java.util.Vector;

import ru.peytob.mineville.machine.BehaviorTree.Context;

public abstract class Node implements INode {

    public Vector<Node> children;

    protected Context context;
    protected Node currentSubtask;
    protected Node parent;
    protected NodeState state;

    public Node(Context _context) {
        children = new Vector<Node>();
        state = NodeState.READY;
        context = _context;
    }

    public void addChild(Node _child) throws ChildException {
        children.add(_child);
        if (children.size() == 1)
            currentSubtask = children.elementAt(0);
        _child.setParent(this);
    }

    public void resetCurrentSubtask() throws ChildException {
        try {
            currentSubtask = children.elementAt(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new ChildException("The node has no children");
        }
    }

    public abstract NodeState tick() throws ChildException;


    //Геттеры

    public Node getCurrentSubtask() {
        return currentSubtask;
    }

    public Node getParent() {
        return parent;
    }


    //Сеттеры

    public void setParent(Node _parent) {
        parent = _parent;
    }

    //Переводит данный узел вместе со всеми его детьми в состояние "ready"
    public void setReady() throws ChildException {
        state = NodeState.READY;
        for (Node child : children)
            child.setReady();
    }

    public void setState(NodeState _state) {
        state = _state;
    }


    public enum NodeState {
        SUCCESS, FAIL, RUNNING, ERROR, READY
    }

    public static class ChildException extends Exception {
        String message;

        public ChildException(String _message) {
            message = _message;
        }

        @Override
        public String toString() {
            return message;
        }
    }

}
