package ru.peytob.mineville.machine.nodes.decorators;

import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.mas.Ontology;

/**
 * Repeat node class.
 * Makes its child perform until interrupted.
 */
public class RepeatNode extends DecoratorNode {

    /**
     * Constructor that sets the link on the ontology.
     * @param _ontology ontology of the tree the node belong to
     */
    public RepeatNode(Ontology _ontology) {
        super(_ontology);
    }

    /**
     * Sends a signal to make child perform.
     * @return RUNNING
     * @throws ChildException when decorator has no child
     */
    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) {
            state = NodeState.ERROR;
            throw new ChildException("Decorator has no child.");
        }

        NodeState childState = children.get(0).tick();

        if (childState == NodeState.SUCCESS) {
            children.get(0).setReady();
            state = NodeState.RUNNING;
        } else {
            state = childState;
        }

        return state;
    }

}
