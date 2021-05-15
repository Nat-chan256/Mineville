package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.mas.Ontology;

/** Test the selector node */
public class SelectorNodeTest
{
    class OpenDoorBT extends BehaviorTree
    {

        public OpenDoorBT(Ontology _ontology) throws ChildException
        {
            super(_ontology);
        }

        public void setDoorClosed()
        {
            ((ContextOntology)this.getOntology()).setVariable("isDoorOpen", false);
        }

        public void setDoorOpen()
        {
            ((ContextOntology)this.getOntology()).setVariable("isDoorOpen", true);
        }

        public void setDoesntHaveKey()
        {
            ((ContextOntology)this.getOntology()).setVariable("hasKey", false);
        }

        public void setHasKey()
        {
            ((ContextOntology)this.getOntology()).setVariable("hasKey", true);
        }

        public void setLegStrength(int legStrength)
        {
            ((ContextOntology)this.getOntology()).setVariable("legStrength", legStrength);
        }


        @Override
        public Node createTreeStructure() throws ChildException {

            Node root = new SelectorNode(this.getOntology());

            root.addChild(new IsDoorOpenNode(this.getOntology()));
            root.addChild(new HasKeyNode(this.getOntology()));
            root.addChild(new CanBreakDownDoor(this.getOntology()));


            ((ContextOntology)this.getOntology()).setVariable("isDoorOpen", false);
            ((ContextOntology)this.getOntology()).setVariable("hasKey", false);
            ((ContextOntology)this.getOntology()).setVariable("legStrength", 20);
            ((ContextOntology)this.getOntology()).setVariable("minLegStrengthToOpenDoor", 60);
            return root;
        }

        @Override
        public Node.NodeState tick()
        {
            try {
                return this.getRoot().tick();
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
            public IsDoorOpenNode(Ontology _ontology)
            {
                super(_ontology);
            }

            @Override
            public void performTask()
            {
                Boolean isDoorOpen = (Boolean)((ContextOntology)this.getOntology()).getVariable("isDoorOpen");

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
            public HasKeyNode(Ontology _ontology)
            {
                super(_ontology);
            }

            @Override
            public void performTask()
            {
                Boolean hasKey = (Boolean)((ContextOntology)this.getOntology()).getVariable("hasKey");
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
            public CanBreakDownDoor(Ontology _ontology)
            {
                super(_ontology);
            }

            @Override
            public void performTask()
            {
                Integer legStrength = (Integer)((ContextOntology)this.getOntology()).getVariable("legStrength");
                Integer minStrength = (Integer)((ContextOntology)this.getOntology()).getVariable("minLegStrengthToOpenDoor");

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
        try {
            OpenDoorBT bt = new OpenDoorBT(new ContextOntology());

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
        catch(ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }
}
