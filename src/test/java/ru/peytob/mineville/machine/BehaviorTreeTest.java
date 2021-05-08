package ru.peytob.mineville.machine;

import org.junit.Assert;
import org.junit.Test;

import ru.peytob.mineville.machine.nodes.*;
import ru.peytob.mineville.machine.nodes.decorators.InverterNode;
import ru.peytob.mineville.machine.nodes.decorators.LimitNode;
import ru.peytob.mineville.machine.nodes.decorators.RepeatNode;

/**
 * Test Behavior tree class.
 */
public class BehaviorTreeTest {

    /**
     * Check if method sets state as ready for all node in behavior tree.
     */
    @Test
    public void testSetReady()
    {
        ManyNodesBT bt = new ManyNodesBT();

        bt.tick();

        Assert.assertNotEquals(Node.NodeState.READY, bt.getState());

        bt.setReady();

        Assert.assertTrue(bt.areAllNodesReady());
    }

    @Test
    public void testTick()
    {
        ManyNodesBT bt = new ManyNodesBT();

        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
        Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());
    }

    /**
     * Class that contains a lot of different nodes.
     * Does not carry a special semantic load.
     * */
    class ManyNodesBT extends BehaviorTree
    {

        public ManyNodesBT()
        {
            root = new SelectorNode(this.context);

            // Left branch
            SequenceNode sequenceNode = new SequenceNode(this.context);
            SpecificLeafNode[] leafNodes = new SpecificLeafNode[5];

            // Right branch
            SelectorNode selectorNode = new SelectorNode(this.context);
            InverterNode inverterNode = new InverterNode(this.context);
            LimitNode limitNode = new LimitNode(this.context, 5);
            RepeatNode repeatNode = new RepeatNode(this.context);
            selectorNode.addChild(inverterNode);
            selectorNode.addChild(limitNode);
            selectorNode.addChild(repeatNode);

                try {
                for (int i = 0; i < 5; ++i)
                {
                    leafNodes[i] = new SpecificLeafNode(this.context);

                    switch (i)
                    {
                        case 0:
                        case 1:
                            sequenceNode.addChild(leafNodes[i]);
                            break;
                        case 2:
                            inverterNode.addChild(leafNodes[i]);
                            break;
                        case 3:
                            limitNode.addChild(leafNodes[i]);
                            break;
                        case 4:
                            repeatNode.addChild(leafNodes[i]);
                            break;
                    }
                }
                root.addChild(sequenceNode);
                root.addChild(selectorNode);
            }
            catch(ChildException ex)
            {
                System.out.println(ex.getMessage());
            }
        }

        /**
         * Checks if all nodes' state is READY.
         * Makes a depth-first tour.
         * @return true if all nodes in tree have state READY; false - otherwise
         */
        public boolean areAllNodesReady()
        {
            boolean result = true;

            for (Node child : root.getChildren())
            {
                result &= isReady(child);
            }

            return result;
        }

        /**
         * Checks if node and all its children have READY state
         * @param node node to be checked
         * @return true if node and all its children have READY state; false - otherwise
         */
        private boolean isReady(Node node)
        {
            if (node.getState() != Node.NodeState.READY)
            {
                return false;
            }
            boolean result = true;
            for (Node child : node.getChildren())
            {
                result &= isReady(child);
            }
            return result;
        }

        class SpecificLeafNode extends LeafNode
        {

            /**
             * Constructor that sets the link on the context.
             *
             * @param _context context of the tree the node belong to
             */
            public SpecificLeafNode(Context _context) {
                super(_context);
            }
        }
    }
}
