package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.mas.Ontology;

/**
 * Inverter node class.
 * Changes the child node's performing result to the opposite value.
 */
public class InverterNode extends DecoratorNode {

    /**
     * Constructor that sets the link on the context.
     * @param _ontology ontology of the tree the node belong to
     */
    public InverterNode(Ontology _ontology) {
        super(_ontology);
    }

    /**
     * Sends a signal to update node's state.
     * @return SUCCESS if child node fails; FAIL if child node succeeds; child node's state - otherwise
     * @throws ChildException when decorator has no child
     */
    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) throw new ChildException("Decorator has no child.");

        NodeState childState = children.get(0).tick();
        if (childState == NodeState.SUCCESS) {
            state = NodeState.FAIL;
            return state;
        } else if (childState == NodeState.FAIL) {
            state = NodeState.SUCCESS;
            return state;
        } else if (childState == NodeState.RUNNING) {
            state = NodeState.RUNNING;
            return state;
        } else if (childState == NodeState.ERROR) {
            state = NodeState.ERROR;
            return state;
        } else {
            state = NodeState.READY;
            return state;
        }
    }
}
