package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.nodes.ChildException;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard agent class.
 */
public abstract class Agent {

    /** List of agent's desires. */
    private List<Desire> desiresList;

    /** List of agent's intentions. */
    private  List<Desire> intentionsList;

    /** The knowledge base of the agent. */
    private Ontology ontology;

    /** State machine which determine what action agent will perform depending on its state. */
    private AgentStateMachine stateMachine;

    /** Constructor which sets the agent desires and intentions and launches the state machine. */
    public Agent(Ontology _ontology) throws ChildException
    {
        ontology = _ontology;

        desiresList = new ArrayList<>();
        intentionsList = new ArrayList<>();

        stateMachine = new AgentStateMachine(this);

        desiresList = createDesiresList();
        setIntentions();
        // Launch the state machine
        try {
            stateMachine.setState(createInitialState());
        } catch(InterruptedException ex)
        {
            System.out.println("Initializing state machine error: " + ex.getMessage());
        }
    }

    public Ontology getOntology()
    {
        return ontology;
    }

    public AgentStateMachine getStateMachine()
    {
        return stateMachine;
    }

    /** The main action of the agent. */
    private void act()
    {
        stateMachine.act();
    }

    /**
     * Creates the list of desires which is assigned to desiresList field in the class.
     * @return list of agent's desires
     */
    public abstract List<Desire> createDesiresList();

    /**
     * Creates the initial state for state machine.
     * @return AgentState which will be set as the initial state for the state machine
     */
    public abstract AgentState createInitialState() throws ChildException;

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
