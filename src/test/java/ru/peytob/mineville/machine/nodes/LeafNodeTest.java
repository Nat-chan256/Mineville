package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.mas.Ontology;

/** Test leaf node. */
public class LeafNodeTest {

    /** Test tree whose leaf counts the sum of two numbers. */
    class CalculationsTree extends BehaviorTree {

        class SumNode extends LeafNode{

            public SumNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {
                Integer a = (Integer)((ContextOntology)this.getOntology()).getVariable("a");
                Integer b = (Integer)((ContextOntology)this.getOntology()).getVariable("b");
                if (a != null && b != null)
                {
                    ((ContextOntology)this.getOntology()).setVariable("sum", a + b);
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }

        public CalculationsTree(Ontology _ontology) throws ChildException
        {
            super(_ontology);
        }

        public Integer getSum()
        {
            return (Integer)((ContextOntology)this.getOntology()).getVariable("sum");
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new SumNode(this.getOntology());

            ((ContextOntology)this.getOntology()).setVariable("a", 15);
            ((ContextOntology)this.getOntology()).setVariable("b", 7);
            return root;
        }

        @Override
        public Node.NodeState tick()  {
            try {
                return this.getRoot().tick();
            }
            catch(ChildException ex)
            {
                state = Node.NodeState.ERROR;
                return state;
            }
        }
    }

    /**
     * Check the execution of tick() method for leaf node.
     */
    @Test
    public void testLeafNodeTickAndPerformTask()
    {
        try {
            CalculationsTree ct = new CalculationsTree(new ContextOntology());

            Assert.assertEquals(Node.NodeState.RUNNING, ct.tick());
            Assert.assertEquals(Node.NodeState.SUCCESS, ct.tick());
            Assert.assertEquals(ct.getSum(), (Integer) 22);
        }
        catch(ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }
}
