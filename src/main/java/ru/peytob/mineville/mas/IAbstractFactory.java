package ru.peytob.mineville.mas;

/** Abstract factory pattern.
 *  Allows to get an agent instance and an ontology instance.
 * */
public interface IAbstractFactory {

    /**
     * Creates new agent and returns its instance.
     * @return new agent's instance
     */
    Agent getNewAgent();

    /**
     * Returns the ontology used by the agents of the community which implements the interface.
     * Doesn't create new ontology instance if it already exists.
     * @return an ontology instance.
     */
    Ontology getOntology();
}
