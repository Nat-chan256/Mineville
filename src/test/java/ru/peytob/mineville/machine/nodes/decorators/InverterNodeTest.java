package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.ContextOntology;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.mas.Ontology;

/** Test the Inverter Node. */
public class InverterNodeTest {

    /** Test the execution of inverter node's tick*/
    @Test
    public void testInverterNodeTick()
    {
        try {
            CheckDoorOpenBT bt = new CheckDoorOpenBT(new ContextOntology());

            // When the door is closed
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.FAIL, bt.tick());

            // When the door is open
            bt.setReady();
            bt.setDoorOpen();
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());
        }
        catch (ChildException ex)
        {
            System.out.println("Behavior tree creation exception: " + ex.getMessage());
        }
    }

    class CheckDoorOpenBT extends BehaviorTree
    {
        public CheckDoorOpenBT(Ontology _ontology) throws ChildException
        {
            super(_ontology);
        }

        public void setDoorOpen()
        {
            ((ContextOntology)this.getOntology()).setVariable("isDoorClosed", false);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new InverterNode(this.getOntology());

            root.addChild(new IsDoorOpenNode(this.getOntology()));

            ((ContextOntology)this.getOntology()).setVariable("isDoorClosed", true);

            return root;
        }

        class IsDoorOpenNode extends LeafNode
        {

            public IsDoorOpenNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask()
            {
                Boolean isDoorClosed = (Boolean)((ContextOntology)this.getOntology()).getVariable("isDoorClosed");
                if (isDoorClosed == null)
                {
                    state = NodeState.ERROR;
                }
                else if (isDoorClosed)
                {
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }
    }
}
