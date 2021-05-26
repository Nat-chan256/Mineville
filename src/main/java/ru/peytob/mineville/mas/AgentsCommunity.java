package ru.peytob.mineville.mas;

import java.util.ArrayList;
import java.util.List;

/** The class that stores all agents of the same type, */
public class AgentsCommunity {

    /** Agents "living" in the community. */
    private List<Agent> agents;

    /** The object which sends signals of agents. */
    private MessagesDeliveryService messagesDeliveryService;

    /** Common ontology for agents of the community. */
    private Ontology ontology;

    /**
     * Constructor that sets ontology and initializes agents list.
     * @param _ontology ontology to set
     */
    public AgentsCommunity(Ontology _ontology, MessagesDeliveryService _messagesDeliveryService)
    {
        agents = new ArrayList<>();
        ontology = _ontology;
        messagesDeliveryService = _messagesDeliveryService;
        messagesDeliveryService.setAgentsCommunity(this);
    }

    public List<Agent> getAgents()
    {
        return agents;
    }

    public MessagesDeliveryService getMessagesDeliveryService()
    {
        return messagesDeliveryService;
    }

    public Ontology getOntology()
    {
        return ontology;
    }

    /**
     * Adds the agent to agents list.
     * @param _agent agent to be added to the agents list
     */
    public void registerAgent(Agent _agent)
    {
        agents.add(_agent);
    }
}
