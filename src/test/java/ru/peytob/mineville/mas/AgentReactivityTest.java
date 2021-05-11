package ru.peytob.mineville.mas;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.IState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgentReactivityTest {

    /** Check if an agent can change its intentions depending on environment changes. */
    @Test
    public void testAgentsReactivity()
    {
        // Create some abstract agent which changes variable a or b depending on his state
        AbstractAgent agent = AbstractAgentsManager.getNewAgent();
        agent.act();

        AbstractOntology ontology = AbstractAgentsManager.getOntology();

        ontology.setANotAvailable();
        Integer a = ontology.getA();

        Integer b = ontology.getB();
        ontology.setBAvailable();


        // Check if a didn't change
        Assert.assertNotEquals(a, ontology.getA());
        // Check if b changed
        Assert.assertNotEquals(b, ontology.getB());
    }
}

class AbstractAgent extends Agent
{

    /**
     * Constructor which sets the agent desires and intentions and calls act() method.
     *
     * @param _ontology
     */
    public AbstractAgent(Ontology _ontology) {
        super(_ontology);
    }

    @Override
    public void setDesires() {
        desiresList.add(
                new Desire<Integer>(
                        (Integer x) -> x == 100,
                        ((AbstractOntology)ontology).getA()
                )
        );
        desiresList.add(
                new Desire<Integer>(
                        (Integer x) -> x == 100,
                        ((AbstractOntology)ontology).getB()
                )
        );
    }

    @Override
    public void setInitialState() {
        IState initialState;

        if (((AbstractOntology)ontology)
                .isAAvailable())
        {
            initialState = new ChangeAState();
        }
        else if (((AbstractOntology)ontology)
                .isBAvailable())
        {
            initialState = new ChangeBState();
        }
        else
        {
            initialState = new WaitState();
        }

        stateMachine.setState(initialState);
    }
}

class AbstractAgentsManager
{
    private static List<AbstractAgent> agentsList;

    private static AbstractOntology ontology;

    public static AbstractAgent getNewAgent()
    {
        if (ontology == null)
        {
            ontology = new AbstractOntology();
        }

        AbstractAgent agent = new AbstractAgent(ontology);

        if (agentsList == null)
        {
            agentsList = new ArrayList<>();
        }
        agentsList.add(agent);
        return agent;
    }

    public static AbstractOntology getOntology()
    {
        return ontology;
    }
}

class AbstractOntology extends Ontology
{
    private Integer a;
    private Integer b;
    private boolean isAAvailable;
    private boolean isBAvailable;
    private List<? extends IObserver> observersList;

    public AbstractOntology()
    {
        a = 0;
        b = 0;
        isAAvailable = false;
        isBAvailable = false;
        observersList = new ArrayList<>();
    }

    public Integer getA()
    {
        return a;
    }

    public Integer getB()
    {
        return b;
    }

    public void setA(int _val)
    {
        a = _val;
    }

    public void setANotAvailable()
    {
        isAAvailable = false;
        notifyObservers();
    }

    public void setB(int _val)
    {
        b = _val;
    }

    public boolean isAAvailable()
    {
        return isAAvailable;
    }

    public boolean isBAvailable()
    {
        return isBAvailable;
    }

    @Override
    public void notifyObservers()
    {
        for (IObserver observer : observersList)
        {
            observer.update();
        }
    }
}

class ChangeAState extends AgentState
{
    private Random rand;

    public ChangeAState()
    {
        rand = new Random();
    }

    @Override
    public void act() {
        ((AbstractOntology)
                ((AgentStateMachine)parent).getOntology())
                .setA(rand.nextInt(100));
    }
}

class ChangeBState extends AgentState
{
    private Random rand;
    
    public ChangeBState()
    {
        rand = new Random();
    }
    
    @Override
    public void act() {
        ((AbstractOntology)
                ((AgentStateMachine)parent).getOntology())
                .setB(rand.nextInt(100));
    }
}

class WaitState extends AgentState
{
    @Override
    public void act() {

    }
}