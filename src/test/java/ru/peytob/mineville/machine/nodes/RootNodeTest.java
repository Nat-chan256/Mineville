package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.Node.NodeState;

/**
 * Class to test root node.
 */
public class RootNodeTest {
    private class EmptyBehaviorTree extends BehaviorTree {
        public EmptyBehaviorTree() {
            super();
        }

        public NodeState tick() {
            return root.tick();
        }
    }

    /**
     * Check the execution of tick() method for root node without children.
     */
    @Test
    public void testRootNodeTick() {
        EmptyBehaviorTree bt = new EmptyBehaviorTree();

        Assert.assertEquals(NodeState.READY, bt.tick());
    }
}
