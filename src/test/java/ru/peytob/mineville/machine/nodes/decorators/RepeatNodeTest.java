package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.ContextOntology;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.mas.Ontology;

/** Test the repeat node*/
public class RepeatNodeTest {

    /** Test the execution of repeat node's tick */
    @Test
    public void testRepeatNodeTick()
    {
        try {
            EndlessBT bt = new EndlessBT(new ContextOntology());

            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        }
        catch(ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }

    class EndlessBT extends BehaviorTree
    {
        public EndlessBT(Ontology _ontology) throws ChildException
        {
            super(_ontology);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new RepeatNode(this.getOntology());

            root.addChild(new EmptyLeafNode(this.getOntology()));
            return root;
        }

        class EmptyLeafNode extends LeafNode
        {

            public EmptyLeafNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                this.setState(NodeState.SUCCESS);
            }
        }
    }
}
