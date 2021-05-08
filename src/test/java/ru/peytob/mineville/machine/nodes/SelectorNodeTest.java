package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;

/** Test the selector node */
public class SelectorNodeTest
{
    class OpenDoorBT extends BehaviorTree
    {

        public OpenDoorBT()
        {
            super();

            root = new SelectorNode(context);

            try
            {
                root.addChild(new IsDoorOpenNode(context));
                root.addChild(new HasKeyNode(context));
                root.addChild(new CanBreakDownDoor(context));
            }
            catch(ChildException ex)
            {

            }

            context.setVariable("isDoorOpen", false);
            context.setVariable("hasKey", false);
            context.setVariable("legStrength", 20);
            context.setVariable("minLegStrengthToOpenDoor", 60);
        }

        public void setDoorClosed()
        {
            context.setVariable("isDoorOpen", false);
        }

        public void setDoorOpen()
        {
            context.setVariable("isDoorOpen", true);
        }

        public void setDoesntHaveKey()
        {
            context.setVariable("hasKey", false);
        }

        public void setHasKey()
        {
            context.setVariable("hasKey", true);
        }

        public void setLegStrength(int legStrength)
        {
            context.setVariable("legStrength", legStrength);
        }


        @Override
        public Node.NodeState tick()
        {
            try {
                return root.tick();
            }
            catch(ChildException ex)
            {
                state = Node.NodeState.ERROR;
                return state;
            }
        }


        // Leaves classes
        class IsDoorOpenNode extends LeafNode
        {
            public IsDoorOpenNode(Context context)
            {
                super(context);
            }

            @Override
            public void performTask()
            {
                Boolean isDoorOpen = (Boolean)context.getVariable("isDoorOpen");

                if (isDoorOpen == null)
                {
                    state = NodeState.ERROR;
                }
                else if (isDoorOpen)
                {
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }

        class HasKeyNode extends LeafNode
        {
            public HasKeyNode(Context context)
            {
                super(context);
            }

            @Override
            public void performTask()
            {
                Boolean hasKey = (Boolean)context.getVariable("hasKey");
                if (hasKey == null)
                {
                    state = NodeState.ERROR;
                }
                else if (hasKey)
                {
                    state = NodeState.SUCCESS;
                }
                else
                {
                    state = NodeState.FAIL;
                }
            }
        }

        class CanBreakDownDoor extends LeafNode
        {
            public CanBreakDownDoor(Context context)
            {
                super(context);
            }

            @Override
            public void performTask()
            {
                Integer legStrength = (Integer)context.getVariable("legStrength");
                Integer minStrength = (Integer)context.getVariable("minLegStrengthToOpenDoor");

                if (legStrength == null || minStrength == null)
                {
                    state = NodeState.ERROR;
                }
                else if (legStrength >= minStrength)
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

    /** Tests the result of selector node's tick execution. */
    @Test
    public void testSelectorNodeTick()
    {
        OpenDoorBT bt = new OpenDoorBT();

        // When the door is open
        bt.setDoorOpen();
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());

        // When the door is closed but we have a key
        bt.setReady();
        bt.setDoorClosed();
        bt.setHasKey();
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());

        // When the door is closed, we don't have a key, but we can bread down the door
        bt.setReady();
        bt.setDoorClosed();
        bt.setDoesntHaveKey();
        bt.setLegStrength(80);
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());


        // When we can't open the door at all
        bt.setReady();
        bt.setLegStrength(10);
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.FAIL, bt.tick());
    }
}
