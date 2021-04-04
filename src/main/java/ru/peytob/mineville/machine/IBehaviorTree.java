package ru.peytob.mineville.machine;

import ru.peytob.mineville.machine.nodes.Node.NodeState;

public interface IBehaviorTree {
    NodeState tick();
}
