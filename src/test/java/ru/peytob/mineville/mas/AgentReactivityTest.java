package ru.peytob.mineville.mas;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.IState;

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

        if (ontology.isAAvailable())
        {
            initialState = new ChangeAState();
        }

        stateMachine.setState(initialState);
    }
}

class AbstractOntology extends Ontology
{
    private Integer a;
    private Integer b;

    public AbstractOntology()
    {
        a = 0;
        b = 0;
    }

    public Integer getA()
    {
        return a;
    }

    public Integer getB()
    {
        return b;
    }
}