package ru.peytob.mineville.machine;

import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.machine.nodes.Node.NodeState;

/**
 * Behavior Tree interface.
 */
public interface IBehaviorTree {

    /**
     * Getter for behavior tree state.
     * @return behavior tree state
     */
    NodeState getState();

    /**
     * Updates all tree nodes state. Also makes them perform their tasks.
     * @return tree state after this tick
     */
    NodeState tick();
}
