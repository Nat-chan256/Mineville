package ru.peytob.mineville.machine.nodes.decorators;



import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.mas.Ontology;

/**
 * Limit node class.
 * Sets the number of times the child node must be performed.
 */
public class LimitNode extends DecoratorNode {

    /** The number of times the child node must be performed. */
    private int limit;

    /** The number of times the child node has been performed. */
    private int counter;

    /**
     * Constructor that sets the link on the context and limit.
     * @param _ontology ontology of the tree the node belong to
     * @param limit number of time the child node must be performed
     */
    public LimitNode(Ontology _ontology, int limit) {
        super(_ontology);
        this.limit = limit;
    }

    /**
     * Sets the node state as "ready" and nullifies the counter.
     */
    @Override
    public void setReady() {
        super.setReady();
        counter = 0;
    }

    /**
     * Sends a signal to update node's state.
     * @return RUNNING, if counter < limit; child node's state - otherwise.
     * @throws ChildException when decorator has no child
     */
    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) throw new ChildException("Decorator has no child.");

        if (counter >= limit) {
            return state;
        }

        state = children.get(0).tick();

        if (state == NodeState.SUCCESS) {
            counter++;
            if (counter == limit) {
                return state;
            }
            children.get(0).setReady();
            children.get(0).tick();
            state = NodeState.RUNNING;
        }

        return state;
    }

}
