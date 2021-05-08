package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;

/** Test the repeat node*/
public class RepeatNodeTest {

    /** Test the execution of repeat node's tick */
    @Test
    public void testRepeatNodeTick()
    {
        EndlessBT bt = new EndlessBT();

        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
    }

    class EndlessBT extends BehaviorTree
    {
        public EndlessBT()
        {
            super();

            root = new RepeatNode(context);

            try {
                root.addChild(new EmptyLeafNode(context));
            }
            catch(ChildException ex)
            {}
        }

        class EmptyLeafNode extends LeafNode
        {

            public EmptyLeafNode(Context context) {
                super(context);
            }
        }
    }
}
