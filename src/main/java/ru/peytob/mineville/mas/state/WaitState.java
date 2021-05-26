package ru.peytob.mineville.mas.state;

import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.IBehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.machine.nodes.SequenceNode;
import ru.peytob.mineville.machine.nodes.decorators.UntilSuccessNode;
import ru.peytob.mineville.mas.Agent;
import ru.peytob.mineville.mas.AgentState;
import ru.peytob.mineville.mas.Desire;
import ru.peytob.mineville.mas.Ontology;

import java.util.List;

/** The state that does nothing but periodically checks if at least one agent's desire has become available. */
public class WaitState extends AgentState {

    /** An agent which is in the wait state. */
    private Agent agent;

    /**
     * Constructor that sets the behavior tree and a state machine the state belongs to
     *
     * @param _agent an agent which is in the wait state
     */
    public WaitState(Agent _agent) throws ChildException {
        super(_agent.getStateMachine());
        agent = _agent;
    }

    public WaitState(WaitState _target)
    {
        super(_target);
    }

    @Override
    public IBehaviorTree createBehaviorTree() throws ChildException {
        return new CheckAgentDesiresBT(new Ontology());
    }

    @Override
    public AgentState clone(){
        return new WaitState(this);
    }

    @Override
    public void update() throws InterruptedException {

    }

    class CheckAgentDesiresBT extends BehaviorTree
    {

        /**
         * Constructor that initialize ontology variable.
         *
         * @param _ontology
         */
        public CheckAgentDesiresBT(Ontology _ontology) throws ChildException {
            super(_ontology);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            UntilSuccessNode root = new UntilSuccessNode(this.getOntology());
            SequenceNode sequenceNode = new SequenceNode(this.getOntology());
            SleepNode sleepNode = new SleepNode(this.getOntology());
            CheckAgentDesiresNode checkAgentsDesiresNode = new CheckAgentDesiresNode(this.getOntology());
            sequenceNode.addChild(sleepNode);
            sequenceNode.addChild(checkAgentsDesiresNode);
            root.addChild(sequenceNode);
            return root;
        }

        class SleepNode extends LeafNode
        {
            /**
             * Constructor that sets the link on the ontology.
             *
             * @param _ontology ontology of the tree the node belong to
             */
            public SleepNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                try {
                    Thread.sleep(this.getOntology().getWaitingPeriod());
                }
                catch(InterruptedException ex)
                {
                    System.out.printf("Performing sleep node action interruption: %s", ex.getMessage());
                }
                finally {
                    this.setState(NodeState.SUCCESS);
                }
            }
        }

        class CheckAgentDesiresNode extends LeafNode
        {
            /**
             * Constructor that sets the link on the ontology.
             *
             * @param _ontology ontology of the tree the node belong to
             */
            public CheckAgentDesiresNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                List<Desire> desiresList = agent.getDesiresList();
                for (Desire desire : desiresList)
                {
                    if (desire.isAvailable())
                    {
                        this.setState(NodeState.SUCCESS);
                        return;
                    }
                }
                this.setState(NodeState.FAIL);
            }
        }
    }
}
