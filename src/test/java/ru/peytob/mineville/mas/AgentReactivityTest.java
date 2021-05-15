package ru.peytob.mineville.mas;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.IBehaviorTree;
import ru.peytob.mineville.machine.IMachine;
import ru.peytob.mineville.machine.IState;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.machine.nodes.decorators.RepeatNode;
import ru.peytob.mineville.machine.nodes.decorators.UntilSuccessNode;

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

        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
            System.out.println("Thread sleep error: " + ex.getMessage());
        }

        AbstractOntology ontology = AbstractAgentsManager.getOntology();

        try {
            ontology.setANotAvailable();
            ontology.setBAvailable();
        } catch (InterruptedException ex)
        {
            System.out.println("Setting variables available error: " + ex.getMessage());
        }
        Integer a = ontology.getA();

        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
            System.out.println("Thread sleep error: " + ex.getMessage());
        }

        // Check if a didn't change
        Assert.assertEquals(a, ontology.getA());
        // Check if b changed
        Assert.assertNotEquals((Integer)0, ontology.getB());
    }
}

class AbstractAgent extends Agent
{

    /**
     * Constructor which sets the agent desires and intentions and calls act() method.
     *
     * @param _ontology
     */
    public AbstractAgent(Ontology _ontology) throws ChildException {
        super(_ontology);
        this.getOntology().setObserver(this.getStateMachine());
    }

    @Override
    public List<Desire> createDesiresList() {
        List<Desire> desiresList = new ArrayList<>();

        desiresList.add(
                new Desire<Integer>(
                        (Integer x) -> x == 100,
                        ((AbstractOntology)this.getOntology()).getA()
                )
        );
        desiresList.add(
                new Desire<Integer>(
                        (Integer x) -> x == 100,
                        ((AbstractOntology)this.getOntology()).getB()
                )
        );

        return desiresList;
    }

    @Override
    public AgentState createInitialState() throws ChildException {
        AgentState initialState;

        if (((AbstractOntology)this.getOntology())
                .isAAvailable())
        {
            initialState = new ChangeAState(this.getStateMachine());
        }
        else if (((AbstractOntology)this.getOntology())
                .isBAvailable())
        {
            initialState = new ChangeBState(this.getStateMachine());
        }
        else
        {
            initialState = new WaitState(this.getStateMachine());
        }

        return initialState;
    }

}

class AbstractAgentsManager {
    private static List<AbstractAgent> agentsList;

    private static AbstractOntology ontology;

    public static AbstractAgent getNewAgent()
    {
        if (ontology == null)
        {
            ontology = new AbstractOntology();
        }

        try
        {
            AbstractAgent agent = new AbstractAgent(ontology);

            if (agentsList == null)
            {
                agentsList = new ArrayList<>();
            }
            agentsList.add(agent);
            return agent;
        } catch (ChildException ex)
        {
            System.out.println("Getting agent error: " + ex.getMessage());
        }
        return null;
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

    public AbstractOntology()
    {
        a = 0;
        b = 0;
        isAAvailable = true;
        isBAvailable = false;
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

    public void setANotAvailable() throws InterruptedException
    {
        isAAvailable = false;
        notifyObservers();
    }

    public void setBAvailable() throws InterruptedException
    {
        isBAvailable = true;
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
}

class ChangeAState extends AgentState
{
    private Random rand;

    public ChangeAState(AgentStateMachine _parent) throws ChildException
    {
        super(_parent);
        rand = new Random();
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {
        return new ChangeABT(this.getParent().getOntology());
    }

    @Override
    public void update() throws InterruptedException {
        // If a stopped being available
        if (!((AbstractOntology)this.getOntology()).isAAvailable())
        {
            try{
            // Change state of parent
            if (((AbstractOntology)this.getOntology()).isBAvailable())
            {
                this.getParent().setState(new ChangeBState(this.getParent()));
            }
            else
            {
                this.getParent().setState(new WaitState(this.getParent()));
            }
            }
            catch (ChildException ex)
            {
                System.out.println("Updating state error: " + ex.getMessage());
            }
        }
    }

    class ChangeABT extends BehaviorTree
    {

        /**
         * Constructor that initialize ontology variable.
         *
         * @param _ontology ontology of an agent the behavior tree belongs to
         */
        public ChangeABT(Ontology _ontology) throws ChildException {
            super(_ontology);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new UntilSuccessNode(this.getOntology());

            ChangeANode leafNode = new ChangeANode(this.getOntology());

            root.addChild(leafNode);

            return root;
        }

        class ChangeANode extends LeafNode
        {

            /**
             * Constructor that sets the link on the ontology.
             *
             * @param _ontology ontology of the tree the node belong to
             */
            public ChangeANode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                int newAValue = rand.nextInt(100);
                //System.out.println("a value = " + newAValue);
                ((AbstractOntology)this.getOntology()).setA(newAValue);
                if (newAValue == 100)
                {
                    this.setState(NodeState.SUCCESS);
                }
                else
                {
                    this.setState(NodeState.FAIL);
                }
            }


        }
    }
}

class ChangeBState extends AgentState
{
    private Random rand;
    
    public ChangeBState(AgentStateMachine _parent) throws ChildException
    {
        super(_parent);
        rand = new Random();
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {
        return new ChangeBBT(((AgentStateMachine)this.getParent()).getOntology());
    }

    @Override
    public void update() throws InterruptedException {
        // If b stopped being available
        if (!((AbstractOntology)this.getOntology()).isBAvailable())
        {
            try{
                // Change state of parent
                if (((AbstractOntology)this.getOntology()).isAAvailable())
                {
                    this.getParent().setState(new ChangeAState(this.getParent()));
                }
                else
                {
                    this.getParent().setState(new WaitState(this.getParent()));
                }
            }
            catch (ChildException ex)
            {
                System.out.println("Updating state error: " + ex.getMessage());
            }
        }
    }

    class ChangeBBT extends BehaviorTree
    {

        /**
         * Constructor that initialize ontology variable.
         *
         * @param _ontology
         */
        public ChangeBBT(Ontology _ontology) throws ChildException {
            super(_ontology);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new UntilSuccessNode(this.getOntology());

            ChangeBNode leafNode = new ChangeBNode(this.getOntology());

            root.addChild(leafNode);

            return root;
        }

        class ChangeBNode extends LeafNode
        {

            /**
             * Constructor that sets the link on the ontology.
             *
             * @param _ontology ontology of the tree the node belong to
             */
            public ChangeBNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                int newBValue = rand.nextInt(100);
                ((AbstractOntology)this.getOntology()).setB(newBValue);
                if (newBValue == 100)
                {
                    this.setState(NodeState.SUCCESS);
                }
                else
                {
                    this.setState(NodeState.FAIL);
                }
            }
        }
    }
}

class WaitState extends AgentState
{
    public WaitState(AgentStateMachine _parent) throws ChildException
    {
        super(_parent);
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {
        return new DoNothingBT((this.getParent()).getOntology());
    }

    @Override
    public void update() throws InterruptedException {

        try {
            if (((AbstractOntology) this.getOntology()).isAAvailable())
            {
                this.getParent().setState(new ChangeAState(this.getParent()));
            }
            else if (((AbstractOntology) this.getOntology()).isBAvailable())
            {
                this.getParent().setState(new ChangeBState(this.getParent()));
            }
        } catch(ChildException ex)
        {
            System.out.println("Updating state error: " + ex.getMessage());
        }
    }

    class DoNothingBT extends BehaviorTree
    {

        /**
         * Constructor that initialize ontology variable.
         *
         * @param _ontology
         */
        public DoNothingBT(Ontology _ontology) throws ChildException {
            super(_ontology);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new RepeatNode(this.getOntology());
            root.addChild(new EmptyNode(this.getOntology()));
            return root;
        }

        class EmptyNode extends LeafNode
        {

            /**
             * Constructor that sets the link on the ontology.
             *
             * @param _ontology ontology of the tree the node belong to
             */
            public EmptyNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                setState(NodeState.RUNNING);
            }
        }
    }
}