package ru.peytob.mineville.machine.nodes.decorators;

import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.mas.Ontology;

/**
 * Decorator Node class.
 * Used for converting child node's performing result to appropriate value.
 */
public abstract class DecoratorNode extends Node {

    /**
     * Constructor that sets the link on the ontology.
     * @param _ontology ontology of the tree the node belong to
     */
    public DecoratorNode(Ontology _ontology) {
        super(_ontology);
    }

    /**
     * Adding a child to the node.
     * @param _child child to be added
     * @throws ChildException when try to add more than one child to decorator node
     */
    @Override
    public void addChild(Node _child) throws ChildException {
        //Декоратор не может иметь больше одного потомка
        if (children.size() == 1)
            throw new ChildException("Decorator node can't have more than one child.");
        else
            super.addChild(_child);
    }
}
