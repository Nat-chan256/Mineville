package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.BehaviorTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard agent class.
 */
public abstract class Agent {

    /** Current behavior to achieve current intention. */
    protected BehaviorTree currentBehavior;

    /** List of agent's desires. */
    protected List<Desire> desiresList;

    protected  List<Desire> intentionsList;

    /** The knowledge base of the agent. */
    private Ontology ontology;

    /** Thread in which */
    protected Thread thread;

    /** Constructor which sets the agent desires and intentions and calls act() method. */
    public Agent()
    {
        desiresList = new ArrayList<>();
        intentionsList = new ArrayList<>();

        setDesires();
        setIntentions();
        setBehaviorTree();

        thread = new Thread(() -> act());
        thread.start();
    }

    /** Method aimed at agent's goals achievement. */
    public abstract void act();

    /** Sets current behaviour tree of agent. */
    public abstract void setBehaviorTree();

    /** Sets the desires of the agent. */
    public abstract void setDesires();

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
