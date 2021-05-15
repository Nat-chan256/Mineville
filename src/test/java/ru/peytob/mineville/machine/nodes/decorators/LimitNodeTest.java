package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.ContextOntology;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.mas.Ontology;

/** Test Limit Node. */
public class LimitNodeTest {

    /** Test the execution of limit node's tick */
    @Test
    public void testLimitNodeTick()
    {
        try {
            KnockThreeTimesBT bt = new KnockThreeTimesBT(new ContextOntology());

            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());
        }
        catch(ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }


    class KnockThreeTimesBT extends BehaviorTree
    {
        public KnockThreeTimesBT(Ontology _ontology) throws ChildException
        {
            super(_ontology);

        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new LimitNode(this.getOntology(), 3);
            root.addChild(new KnockDoorNode(this.getOntology()));
            return root;
        }

        class KnockDoorNode extends LeafNode
        {
            public KnockDoorNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                this.setState(NodeState.SUCCESS);
            }
        }
    }
}
