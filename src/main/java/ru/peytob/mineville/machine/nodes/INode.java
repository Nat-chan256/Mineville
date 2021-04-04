package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.machine.nodes.Node.ChildException;
import ru.peytob.mineville.machine.nodes.Node.NodeState;

public interface INode {
    void addChild(Node _child) throws ChildException;

    void setState(NodeState _state);

    NodeState tick() throws ChildException;

}
