package ru.peytob.mineville.mas;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard agent class.
 */
public abstract class Agent {

    /** List of agent's desires. */
    protected List<Desire> desiresList;

    /** List of agent's intentions. */
    protected  List<Desire> intentionsList;

    /** The knowledge base of the agent. */
    protected Ontology ontology;

    /** State machine which determine what action agent will perform depending on its state. */
    protected AgentStateMachine stateMachine;

    /** Thread in which */
    protected Thread thread;

    /** Constructor which sets the agent desires and intentions and calls act() method. */
    public Agent(Ontology _ontology)
    {
        ontology = _ontology;

        desiresList = new ArrayList<>();
        intentionsList = new ArrayList<>();

        stateMachine = new AgentStateMachine();

        setDesires();
        setIntentions();
        setInitialState();

        thread = new Thread(() -> stateMachine.act());
    }

    /** The main action of the agent. */
    public void act()
    {
        thread.start();
    }

    /** Sets the desires of the agent. */
    public abstract void setDesires();

    /** Sets the initial state of the state machine. */
    public abstract void setInitialState();

    /** Sets the intentions, i.e. the subset of desires which the agent can achieve at the moment. */
    public void setIntentions()
    {
        for (Desire desire : desiresList)
        {
            if (desire.isAvailable())
            {
                intentionsList.add(desire);
            }
        }
    }

    public void setOntology(Ontology _ontology)
    {
        ontology = _ontology;
    }
}
