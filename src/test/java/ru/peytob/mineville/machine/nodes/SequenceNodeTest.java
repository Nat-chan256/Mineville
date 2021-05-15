package ru.peytob.mineville.machine.nodes;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.mas.Ontology;

/** Test the sequence node. */
public class SequenceNodeTest
{
    @Test
    public void testSequenceNodeTick()
    {
        try {
            CookSandwichBT bt = new CookSandwichBT(new ContextOntology());

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
        catch(ChildException ex)
        {
            System.out.println("Behavior tree creation error: " + ex.getMessage());
        }
    }


    class CookSandwichBT extends BehaviorTree
    {
        public CookSandwichBT(Ontology _ontology) throws ChildException
        {
           super(_ontology);
        }

        public void setDoesntHaveBread()
        {
           ((ContextOntology)this.getOntology()).setVariable("hasBread", false);
        }

        public void setDoesntHaveSausage()
        {
            ((ContextOntology)this.getOntology()).setVariable("hasSausage", false);
        }

        public void setHasBread()
        {
            ((ContextOntology)this.getOntology()).setVariable("hasBread", true);
        }

        @Override
        public Node createTreeStructure() throws ChildException {
            Node root = new SequenceNode(this.getOntology());

            root.addChild(new CutBreadPieceNode(this.getOntology()));
            root.addChild(new CutSausagePieceNode(this.getOntology()));
            root.addChild(new PutSausageOnBreadNode(this.getOntology()));

            ((ContextOntology)this.getOntology()).setVariable("hasBread", true);
            ((ContextOntology)this.getOntology()).setVariable("hasSausage", true);
            return root;
        }

        class CutBreadPieceNode extends LeafNode
        {
            public CutBreadPieceNode(Ontology _ontology)
            {
                super(_ontology);
            }

            @Override
            public void performTask()
            {
                Boolean hasBread = (Boolean)((ContextOntology)this.getOntology()).getVariable("hasBread");

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
            public CutSausagePieceNode(Ontology _ontology)
            {
                super(_ontology);
            }

            @Override
            public void performTask()
            {
                Boolean hasSausage = (Boolean)((ContextOntology)this.getOntology()).getVariable("hasSausage");

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
            public PutSausageOnBreadNode(Ontology _ontology)
            {
                super(_ontology);
            }

            @Override
            public void performTask() {
                this.setState(NodeState.SUCCESS);
            }
        }
    }
}
