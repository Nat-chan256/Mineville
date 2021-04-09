package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;

/** Test Limit Node. */
public class LimitNodeTest {

    /** Test the execution of limit node's tick */
    @Test
    public void testLimitNodeTick()
    {
        KnockThreeTimesBT bt = new KnockThreeTimesBT();

        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());
    }


    class KnockThreeTimesBT extends BehaviorTree
    {
        public KnockThreeTimesBT()
        {
            super();
            LimitNode limitNode = new LimitNode(context, 3);

            try {
                limitNode.addChild(new KnockDoorNode(context));
                root.addChild(limitNode);
            }
            catch(Node.ChildException ex)
            {}
        }

        class KnockDoorNode extends LeafNode
        {
            public KnockDoorNode(Context context) {
                super(context);
            }
        }
    }
}
