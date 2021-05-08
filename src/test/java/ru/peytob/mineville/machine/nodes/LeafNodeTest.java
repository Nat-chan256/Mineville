package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;

/** Test leaf node. */
public class LeafNodeTest {

    /** Test tree whose leaf counts the sum of two numbers. */
    class CalculationsTree extends BehaviorTree {

        class SumNode extends LeafNode{

            public SumNode(Context context) {
                super(context);
            }

            @Override
            public void performTask() {
                Integer a = (Integer)context.getVariable("a");
                Integer b = (Integer)context.getVariable("b");
                if (a != null && b != null)
                {
                    context.setVariable("sum", a + b);
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }

        public CalculationsTree()
        {
            root = new SumNode(context);

            context.setVariable("a", 15);
            context.setVariable("b", 7);
        }

        public Integer getSum()
        {
            return (Integer)context.getVariable("sum");
        }

        @Override
        public Node.NodeState tick()  {
            try {
                return root.tick();
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
        CalculationsTree ct = new CalculationsTree();

        Assert.assertEquals(Node.NodeState.RUNNING, ct.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, ct.tick());
        Assert.assertEquals(ct.getSum(), (Integer) 22);
    }
}
