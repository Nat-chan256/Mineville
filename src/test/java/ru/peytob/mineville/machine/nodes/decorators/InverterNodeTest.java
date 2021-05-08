package ru.peytob.mineville.machine.nodes.decorators;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;

/** Test the Inverter Node. */
public class InverterNodeTest {

    /** Test the execution of inverter node's tick*/
    @Test
    public void testInverterNodeTick()
    {
        CheckDoorOpenBT bt = new CheckDoorOpenBT();

        // When the door is closed
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.FAIL, bt.tick());

        // When the door is open
        bt.setReady();
        bt.setDoorOpen();
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());
    }

    class CheckDoorOpenBT extends BehaviorTree
    {
        public CheckDoorOpenBT()
        {
            root = new InverterNode(context);

            try {
                root.addChild(new IsDoorOpenNode(context));
            }
            catch(ChildException ex)
            {

            }

            context.setVariable("isDoorClosed", true);
        }

        public void setDoorOpen()
        {
            context.setVariable("isDoorClosed", false);
        }

        class IsDoorOpenNode extends LeafNode
        {

            public IsDoorOpenNode(Context context) {
                super(context);
            }

            @Override
            public void performTask()
            {
                Boolean isDoorClosed = (Boolean)context.getVariable("isDoorClosed");
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
