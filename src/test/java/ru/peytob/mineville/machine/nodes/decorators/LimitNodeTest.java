package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
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
            root = new LimitNode(context, 3);

            try {
                root.addChild(new KnockDoorNode(context));
            }
            catch(ChildException ex)
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
