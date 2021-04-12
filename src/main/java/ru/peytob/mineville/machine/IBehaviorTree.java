package ru.peytob.mineville.machine;

import ru.peytob.mineville.machine.nodes.Node.NodeState;

/**
 * Behavior Tree interface.
 */
public interface IBehaviorTree {

    /**
     * Updates all tree nodes state. Also makes them perform their tasks.
     * @return tree state after this tick
     */
    NodeState tick();
}
