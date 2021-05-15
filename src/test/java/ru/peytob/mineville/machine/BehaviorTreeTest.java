package ru.peytob.mineville.machine;

import org.junit.Assert;
import org.junit.Test;

import ru.peytob.mineville.machine.nodes.*;
import ru.peytob.mineville.machine.nodes.decorators.InverterNode;
import ru.peytob.mineville.machine.nodes.decorators.LimitNode;
import ru.peytob.mineville.machine.nodes.decorators.RepeatNode;
import ru.peytob.mineville.mas.Ontology;

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
        try {
            ManyNodesBT bt = new ManyNodesBT(new Ontology());

            bt.tick();

            Assert.assertNotEquals(Node.NodeState.READY, bt.getState());

            bt.setReady();

            Assert.assertTrue(bt.areAllNodesReady());
        }
        catch(ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }

    @Test
    public void testTick()
    {
        try {
            ManyNodesBT bt = new ManyNodesBT(new Ontology());

            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.RUNNING, bt.tick());
            Assert.assertEquals(Node.NodeState.SUCCESS, bt.tick());
        }
        catch (ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }

    /**
     * Class that contains a lot of different nodes.
     * Does not carry a special semantic load.
     * */
    class ManyNodesBT extends BehaviorTree
    {

        public ManyNodesBT(Ontology _ontology) throws ChildException
        {
            super(_ontology);
        }

        /**
         * Checks if all nodes' state is READY.
         * Makes a depth-first tour.
         * @return true if all nodes in tree have state READY; false - otherwise
         */
        public boolean areAllNodesReady()
        {
            boolean result = true;

            for (Node child : this.getRoot().getChildren())
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

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new SelectorNode(this.getOntology());

            // Left branch
            SequenceNode sequenceNode = new SequenceNode(this.getOntology());
            SpecificLeafNode[] leafNodes = new SpecificLeafNode[5];

            // Right branch
            SelectorNode selectorNode = new SelectorNode(this.getOntology());
            InverterNode inverterNode = new InverterNode(this.getOntology());
            LimitNode limitNode = new LimitNode(this.getOntology(), 5);
            RepeatNode repeatNode = new RepeatNode(this.getOntology());
            selectorNode.addChild(inverterNode);
            selectorNode.addChild(limitNode);
            selectorNode.addChild(repeatNode);

            try {
                for (int i = 0; i < 5; ++i)
                {
                    leafNodes[i] = new SpecificLeafNode(this.getOntology());

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

            return root;
        }

        class SpecificLeafNode extends LeafNode
        {

            /**
             * Constructor that sets the link on the context.
             *
             * @param _ontology context of the tree the node belong to
             */
            public SpecificLeafNode(Ontology _ontology) {
                super(_ontology);
            }

            @Override
            public void performTask() {

            }
        }
    }
}
