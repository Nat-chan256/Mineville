package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.mas.signal.CooperateSignal;
import ru.peytob.mineville.mas.signal.Signal;
import ru.peytob.mineville.mas.state.CooperationState;
import ru.peytob.mineville.mas.state.WaitState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Standard agent class.
 */
public abstract class Agent {

    /** List of agent's desires. */
    private List<Desire> desiresList;

    /** List of agent's intentions. */
    private List<Desire> intentions;

    /** Agent is useless when it achieved all its desires. */
    private boolean isUseless;

    /** The service that will send signals to other agents. */
    private MessagesDeliveryService messagesDeliveryService;

    /** The knowledge base of the agent. */
    private Ontology ontology;

    /** State machine which determine what action agent will perform depending on its state. */
    private AgentStateMachine stateMachine;

    /** Constructor which sets the agent desires and intentions and launches the state machine. */
    public Agent(Ontology _ontology) throws ChildException
    {
        ontology = _ontology;

        desiresList = new ArrayList<>();
        intentions = new ArrayList<>();

        stateMachine = new AgentStateMachine(this);

        desiresList = createDesiresList();
        setIntentions();
    }

    /**
     * Constructor which sets ontology and messages delivery service.
     * @param _ontology ontology which agent uses
     * @param _messagesDeliveryService messages delivery service which will send the agent's signals
     * @throws ChildException if agent's behavior tree structure is incorrect
     */
    public Agent(Ontology _ontology, MessagesDeliveryService _messagesDeliveryService) throws ChildException
    {
        this(_ontology);
        messagesDeliveryService = _messagesDeliveryService;

        Desire currentIntention = findCurrentIntention();
        if (currentIntention != null && currentIntention.isShareable())
        {
            messagesDeliveryService.addSender(this);
        }
    }

    public Desire getCurrentIntention()
    {
        return findCurrentIntention();
    }

    public List<Desire> getDesiresList()
    {
        return desiresList;
    }

    public MessagesDeliveryService getMessagesDeliveryService() {
        return messagesDeliveryService;
    }

    public Ontology getOntology()
    {
        return ontology;
    }

    public AgentStateMachine getStateMachine()
    {
        return stateMachine;
    }

    /** Sets the intentions, i.e. the subset of desires which the agent can achieve at the moment. */
    public void setIntentions()
    {
        for (Desire desire : desiresList)
        {
            if (desire.isAvailable() && !intentions.contains(desire))
            {
                intentions.add(desire);
            }
        }
    }

    public void setOntology(Ontology _ontology)
    {
        ontology = _ontology;
    }

    /**
     * Inserts the desire to the desires list and updates intentions.
     * @param _desire desire to be inserted into desires list
     */
    public void addDesire(Desire<? extends Object> _desire) throws InterruptedException, ChildException
    {
        desiresList.add(_desire);
        updateIntentions();
    }

    /**
     * Creates the list of desires which is assigned to desiresList field in the class.
     * @return list of agent's desires
     */
    public abstract List<Desire> createDesiresList() throws ChildException;

    /**
     * Creates the initial state for state machine.
     * @return AgentState which will be set as the initial state for the state machine
     */
    public abstract AgentState createInitialState() throws ChildException;

    /** Sets isUseless flag as true, so the agent will be removed from agents' community. */
    private void destroy()
    {
        isUseless = true;
    }

    /**
     * Finds current agent's intention.
     * @return desire the agent tries to achieve in the moment or null, if it's not found
     */
    private Desire findCurrentIntention()
    {
        for (Desire intention : intentions)
        {
            if (intention.getAppropriateState() == stateMachine.getState())
            {
                return intention;
            }
        }
        return null;
    }

    public void receiveSignal(Signal _signalToReceive) throws InterruptedException, ChildException
    {
        if (_signalToReceive.getClass() == CooperateSignal.class)
        {
            for(Desire intention : intentions)
            {
                if (intention.equals(((CooperateSignal) _signalToReceive).getIntention()))
                {
                    stateMachine.setState(
                            new CooperationState(
                                    stateMachine.getState(),
                                    ((CooperateSignal) _signalToReceive).getIntention().getAppropriateState()
                            )
                    );
                    return;
                }
            }
            /*if (intentionsList.contains(((CooperateSignal)_signalToReceive).getIntention()))
            {
                stateMachine.setState(
                        new CooperationState(
                                stateMachine.getState(),
                                ((CooperateSignal) _signalToReceive).getIntention().getAppropriateState()
                        )
                );
            }*/
        }
    }

    /** Launches the state machine and sends cooperate signals if necessary. */
    public void start() throws ChildException
    {
        // Launch the state machine
        try {
            if (stateMachine.getState() == null) {
                stateMachine.setState(createInitialState());
            }
        } catch(InterruptedException ex)
        {
            System.out.println("Initializing state machine error: " + ex.getMessage());
        }
        Desire<? extends Object> currentIntention = findCurrentIntention();
        if (currentIntention.isShareable())
        {
            messagesDeliveryService.addSender(this);
            if (!messagesDeliveryService.isLaunched())
            {
                messagesDeliveryService.start();
            }
        }
    }

    /** Sets new intentions or delete them if necessary.
     * If the agent has no desires, it is destroyed
     * */
    public void updateIntentions() throws InterruptedException, ChildException
    {
        // Remove achieved intentions and desires
        List<Desire> listToRemove = new ArrayList<>();
        for (Desire intention : intentions)
        {
            if (intention.isAchieved())
            {
                listToRemove.add(intention);
            }
        }
        intentions.removeAll(listToRemove);
        listToRemove.clear();
        for (Desire desire : desiresList)
        {
            if (desire.isAchieved())
            {
                listToRemove.add(desire);
            }

        }
        desiresList.removeAll(listToRemove);

        if (desiresList.size() == 0)
        {
            this.destroy();
        }
        else
        {
            setIntentions();
            if (intentions.size() > 0)
            {

                stateMachine.setState(intentions.get(0).getAppropriateState());
                Desire currentIntention = findCurrentIntention();
                if (currentIntention != null && currentIntention.isShareable())
                {
                    messagesDeliveryService.addSender(this);
                }
            }
            else
            {
                stateMachine.setState(new WaitState(this));
            }
        }
    }
}
