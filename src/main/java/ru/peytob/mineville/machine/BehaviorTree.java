package ru.peytob.mineville.machine;

import java.util.HashMap;
import java.util.Map;

import ru.peytob.mineville.machine.nodes.Node.NodeState;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.mas.Ontology;

/**
 * Behavior Tree class.
 */
public abstract class BehaviorTree implements IBehaviorTree {

    /** Root node. */
    private Node root;

    /** An ontology of an agent the behavior tree belongs to. */
    private Ontology ontology;

    /** Current state of the tree. */
    protected NodeState state;

    public NodeState getState() {
        return state;
    }

    /**
     * Constructor that initialize ontology variable.
     */
    public BehaviorTree(Ontology _ontology) throws ChildException {
        ontology = _ontology;
        state = NodeState.READY;
        root = createTreeStructure();
    }

    public Ontology getOntology()
    {
        return ontology;
    }

    /**
     * Sets the states of all the tree nodes as "ready"
     */
    public void setReady() {
        root.setReady();
    }

    protected Node getRoot()
    {
        return root;
    }

    /**
     * Creates the structure of the tree, i.e. creates all the nodes and connects them with each other.
     * The method is called in the constructor where the return value of the method is assigned to the rood field.
     * @return the root of the tree
     */
    public abstract Node createTreeStructure() throws ChildException;

    /**
     * Updates all tree nodes state. Also makes them perform their tasks.
     * @return tree state after this tick
     */
    public NodeState tick() {
        try {
            state = root.tick();
            return state;
        } catch (ChildException ex) {
            return NodeState.ERROR;
        }
    }
}
