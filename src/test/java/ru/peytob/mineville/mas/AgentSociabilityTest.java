package ru.peytob.mineville.mas;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.IBehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.machine.nodes.decorators.RepeatNode;
import ru.peytob.mineville.machine.nodes.decorators.UntilSuccessNode;

import java.util.ArrayList;
import java.util.List;

/** Class that test agent's sociability. */
public class AgentSociabilityTest {

    /** Check if agents can communicate with each other in order to cooperate. */
    @Test
    public void testAgentSociability()
    {
//        MultiplyMatricesAgent decrementingAgent1 = MultiplyMatricesAgentCommunity.GetNewAgent();
//        MultiplyMatricesAgent decrementingAgent2 = MultiplyMatricesAgentCommunity.GetNewAgent();
//
//        try {
//            Thread.sleep(10000);
//        }
//        catch(InterruptedException ex)
//        {
//            System.out.printf("Interrupted error : %s%n", ex.getMessage());
//        }
//
//
//        Assert.assertEquals();
    }

    /** Check if an agent receives signal that was sent by other agent. */
    @Test
    public void testReceivingSignals()
    {
        CalculatingAgent agent1 = SampleAgentsCommunity.getNewCalculatingAgent();
        CalculatingAgent agent2 = SampleAgentsCommunity.getNewCalculatingAgent();

        try {
            agent1.addDesire(new Desire<Double>(
                    (Double x) -> x != null,
                    ((CalculationsOntology) agent1.getOntology()).getX(),
                    new CalculateXState(agent1.getStateMachine()),
                    true
            ));
            agent1.addDesire(new Desire<Double>(
                    (Double y) -> y != null,
                    ((CalculationsOntology) agent1.getOntology()).getY(),
                    new CalculateYState(agent1.getStateMachine()),
                    true,
                    true
            ));

            agent2.addDesire(new Desire<Double>(
                    (Double y) -> y != null,
                    ((CalculationsOntology) agent2.getOntology()).getY(),
                    new CalculateYState(agent2.getStateMachine()),
                    true,
                    true
            ));
        }
        catch(ChildException | InterruptedException ex)
        {
            System.out.println("Adding desire error: " + ex.getMessage());
        }

        try {
            agent1.start();
            agent2.start();
        }
        catch(ChildException ex)
        {
            System.out.println("Launching agents error: " + ex.getMessage());
        }

        try {
            Thread.sleep(2000);
        } catch(InterruptedException ex)
        {
            System.out.println("Thread sleep error: " + ex.getMessage());
        }

        Assert.assertEquals(agent2.getCurrentIntention(), agent1.getCurrentIntention());
    }
}

class CalculatingAgent extends Agent
{
    public CalculatingAgent(Ontology _ontology,
                            MessagesDeliveryService _messagesDeliveryService) throws ChildException {
        super(_ontology, _messagesDeliveryService);
    }

    @Override
    public List<Desire> createDesiresList() throws ChildException {
        List<Desire> desiresList = new ArrayList<>();

        return desiresList;
    }

    @Override
    public AgentState createInitialState() throws ChildException {
        return null;
    }
}

class CalculationsOntology extends Ontology
{
    private Double x;
    private Double y;

    public Double getX()
    {
        return x;
    }

    public Double getY()
    {
        return y;
    }

    public void setY(Double _y)
    {
        y = _y;
    }
}

class CalculateXState extends AgentState
{
    /**
     * Constructor that sets the behavior tree and a state machine the state belongs to
     *
     * @param _parent a state machine the state belongs to
     */
    public CalculateXState(AgentStateMachine _parent) throws ChildException {
        super(_parent);
    }

    public CalculateXState(CalculateXState _target)
    {
        super(_target);
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {

        return new BehaviorTree(this.getOntology()) {
            @Override
            public Node createTreeStructure() throws ChildException {
                Node root = new UntilSuccessNode(this.getOntology());

                // Falsely create unreachable goal
                root.addChild(new LeafNode(this.getOntology()) {
                    @Override
                    public void performTask() {
                        this.setState(NodeState.RUNNING);
                    }
                });

                return root;
            }
        };

    }

    @Override
    public AgentState clone(){
        return new CalculateXState(this);
    }

    @Override
    public void update() throws InterruptedException {

    }
}

class CalculateYState extends AgentState
{
    /**
     * Constructor that sets the behavior tree and a state machine the state belongs to
     *
     * @param _parent a state machine the state belongs to
     */
    public CalculateYState(AgentStateMachine _parent) throws ChildException {
        super(_parent);
    }

    public CalculateYState(CalculateYState _target)
    {
        super(_target);
    }

    @Override
    public AgentState clone() {
        return new CalculateYState(this);
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {
        return new BehaviorTree(this.getOntology()) {
            @Override
            public Node createTreeStructure() throws ChildException {
                Node root = new UntilSuccessNode(this.getOntology());

                root.addChild(new LeafNode(this.getOntology()) {
                    @Override
                    public void performTask() {
                        ((CalculationsOntology)this.getOntology()).setY(100.0);
                        if (((CalculationsOntology)this.getOntology()).getY() != null)
                        {
                            this.setState(NodeState.SUCCESS);
                        }
                        else
                        {
                            this.setState(NodeState.FAIL);
                        }
                    }
                });

                return root;
            }
        };
    }

    @Override
    public void update() throws InterruptedException {

    }
}

class SampleAgentsCommunity extends AgentsCommunity
{
    private static SampleAgentsCommunity instance;

    /**
     * Constructor that sets ontology and initializes agents list.
     *
     * @param _ontology                ontology to set
     * @param _messagesDeliveryService
     */
    public SampleAgentsCommunity(Ontology _ontology, MessagesDeliveryService _messagesDeliveryService) {
        super(_ontology, _messagesDeliveryService);
    }


    public static CalculatingAgent getNewCalculatingAgent()
    {
        if (instance == null)
        {
            instance = new SampleAgentsCommunity(new CalculationsOntology(), new MessagesDeliveryService());
        }
        try {
            CalculatingAgent agent = new CalculatingAgent(instance.getOntology(),
                    instance.getMessagesDeliveryService());
            instance.registerAgent(agent);
            return agent;
        }
        catch(ChildException ex)
        {
            System.out.println("Agent creation error: " + ex.getMessage());
            return null;
        }
    }
}