package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.mas.Ontology;

/**
 * Leaf node class.
 * Its inheritors perform specific tasks.
 */
public abstract class LeafNode extends Node {

    /**
     * Constructor that sets the link on the ontology.
     * @param _ontology ontology of the tree the node belong to
     */
    public LeafNode(Ontology _ontology) {
        super(_ontology);
    }

    /**
     * Performs task of the node and updates its state.
     * @return current state of the node
     */
    @Override
    public NodeState tick() {
        if (state == NodeState.READY) {
            state = NodeState.RUNNING;
        }
        else if(state != NodeState.RUNNING)
        {
            return state;
        }
        new Thread(() -> performTask()).start();
        return state;
    }

    /**
     * Performing task given to this leaf node.
     * Assumes changing leaf node state.
     */
    public abstract void performTask();

}
