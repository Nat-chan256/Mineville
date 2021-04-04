package ru.peytob.mineville.machine.nodes.decorators;


import ru.peytob.mineville.machine.BehaviorTree.Context;

public class InverterNode extends DecoratorNode {
    public InverterNode(Context _context) {
        super(_context);
    }

    @Override
    public NodeState tick() throws ChildException {
        if (children.size() == 0) throw new ChildException("Decorator has no child.");

        NodeState childState = children.elementAt(0).tick();
        if (childState == NodeState.SUCCESS) {
            state = NodeState.FAIL;
            return state;
        } else if (childState == NodeState.FAIL) {
            state = NodeState.SUCCESS;
            return state;
        } else if (childState == NodeState.RUNNING) {
            state = NodeState.RUNNING;
            return state;
        } else if (childState == NodeState.ERROR) {
            state = NodeState.ERROR;
            return state;
        } else {
            state = NodeState.READY;
            return state;
        }
    }
}
