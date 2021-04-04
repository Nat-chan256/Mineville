package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;

/** Test the sequence node. */
public class SequenceNodeTest
{
    @Test
    public void testSequenceNodeTick()
    {
        CookSandwichBT bt = new CookSandwichBT();

        // When we have all ingredients
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());

        // When we don't have bread
        bt.setReady();
        bt.setDoesntHaveBread();
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.FAIL, bt.tick());

        // When we don't have sausage
        bt.setReady();
        bt.setHasBread();
        bt.setDoesntHaveSausage();
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.FAIL, bt.tick());
    }


    class CookSandwichBT extends BehaviorTree
    {
        public CookSandwichBT()
        {
           super();
           SequenceNode sequence = new SequenceNode(context);

           try {
               sequence.addChild(new CutBreadPieceNode(context));
               sequence.addChild(new CutSausagePieceNode(context));
               sequence.addChild(new PutSausageOnBreadNode(context));
               root.addChild(sequence);
           }
           catch(Node.ChildException ex)
           {}

           context.setVariable("hasBread", true);
           context.setVariable("hasSausage", true);
        }

        public void setDoesntHaveBread()
        {
           context.setVariable("hasBread", false);
        }

        public void setDoesntHaveSausage()
        {
            context.setVariable("hasSausage", false);
        }

        public void setHasBread()
        {
            context.setVariable("hasBread", true);
        }

        class CutBreadPieceNode extends LeafNode
        {
            public CutBreadPieceNode(Context context)
            {
                super(context);
            }

            @Override
            public void performTask()
            {
                Boolean hasBread = (Boolean)context.getVariable("hasBread");

                if (hasBread == null)
                {
                    state = NodeState.ERROR;
                }
                else if (hasBread)
                {
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }

        class CutSausagePieceNode extends LeafNode
        {
            public CutSausagePieceNode(Context context)
            {
                super(context);
            }

            @Override
            public void performTask()
            {
                Boolean hasSausage = (Boolean)context.getVariable("hasSausage");

                if (hasSausage == null)
                {
                    state = NodeState.ERROR;
                }
                else if (hasSausage)
                {
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }

        class PutSausageOnBreadNode extends LeafNode
        {
            public PutSausageOnBreadNode(Context context)
            {
                super(context);
            }
        }
    }
}
