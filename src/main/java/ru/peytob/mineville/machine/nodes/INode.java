package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.nodes.Node.ChildException;
import ru.peytob.mineville.machine.nodes.Node.NodeState;

/**
 * Interface for Behavior Tree nodes.
 */
public interface INode {

    /**
     * Adding a child to the node.
     * @param _child child to be added
     * @throws ChildException when child can't be added for some reason
     */
    void addChild(Node _child) throws ChildException;

    /**
     * Setter for
     * @param _state
     */
    void setState(NodeState _state);

    /**
     * Sends a signal to update node's state.
     * @return node state
     * @throws ChildException when there are some problems with node's children
     */
    NodeState tick() throws ChildException;

}
