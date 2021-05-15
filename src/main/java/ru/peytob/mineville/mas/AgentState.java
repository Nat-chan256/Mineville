package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.IBehaviorTree;
import ru.peytob.mineville.machine.IMachine;
import ru.peytob.mineville.machine.IState;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.Node;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class AgentState implements IState, IObserver {

    /** Behavior tree ticked in act() method. */
    private IBehaviorTree behaviorTree;

    /** A machine the state belongs to. */
    protected AgentStateMachine parent;


    /** The thread in which the behavior tree is ticked. */
    private ExecutorService executorService;

    /** Constructor that sets the behavior tree and a state machine the state belongs to
     * @param _parent a state machine the state belongs to
     */
    public AgentState(AgentStateMachine _parent) throws ChildException {
        parent = _parent;
        behaviorTree = createBehaviorTree();
        executorService = Executors.newSingleThreadExecutor();
    }

    public Ontology getOntology()
    {
        return ((AgentStateMachine)this.getParent()).getOntology();
    }

    public AgentStateMachine getParent()
    {
        return parent;
    }

    /** Creates the state's behavior tree. */
    public abstract IBehaviorTree createBehaviorTree() throws ChildException;

    /** Ticks the behavior tree which is set for the state. */
    @Override
    public void act()
    {
        try {
            executorService.submit(tickBehaviorTreeTask());
        }catch(IllegalThreadStateException ex)
        {
            System.out.println("Acting AgentState error: " + ex.getMessage());
        }
    }

    @Override
    public void destroy() {

    }

    /** Interrupts the thread performing. */
    public void interrupt() throws InterruptedException
    {
        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    public void setParent(AgentStateMachine _machine) {
        parent = _machine;
    }

    private Runnable tickBehaviorTreeTask()
    {
        return () ->
        {
            while (!(behaviorTree.getState() == Node.NodeState.SUCCESS
                    || behaviorTree.getState() == Node.NodeState.FAIL
                    || behaviorTree.getState() == Node.NodeState.ERROR)
            )
            {
                try {
                    behaviorTree.tick();
                    Thread.sleep(1);
                }catch (InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };
    }
}
