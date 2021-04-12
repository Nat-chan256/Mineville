package ru.peytob.mineville.machine;

import java.util.HashMap;
import java.util.Map;

import ru.peytob.mineville.machine.nodes.Node.NodeState;
import ru.peytob.mineville.machine.nodes.Node.ChildException;
import ru.peytob.mineville.machine.nodes.Node;

/**
 * Behavior Tree class.
 */
public abstract class BehaviorTree implements IBehaviorTree {

    /** Root node. */
    protected Node root;

    /** Context of tree. */
    protected Context context;

    /** Current state of the tree. */
    protected NodeState state;

    /**
     * Constructor that initialize context variable.
     */
    public BehaviorTree() {
        context = new Context();
    }

    /**
     * Sets the states of all the tree nodes as "ready"
     */
    public void setReady() {
        root.setReady();
    }


    /**
     * Updates all tree nodes state. Also makes them perform their tasks.
     * @return tree state after this tick
     */
    public NodeState tick() {
        try {
            return root.tick();
        } catch (ChildException ex) {
            return NodeState.ERROR;
        }
    }


    /** Context class.
     * Context stores variables necessary for nodes to perform their tasks.
     * */
    public class Context {
        /** The map of variables.
         * The key is the variable name, the value is the value of the corresponding variable.
         * */
        private Map<String, Object> variables;

        /**
         * Constructor that initialize variables map.
         */
        public Context() {
            variables = new HashMap<String, Object>();
        }

        /**
         * Sets the value of variable.
         * If variable doesn't exist, it will be created.
         * @param _varName the name of variable to be set
         * @param _value value of variable
         */
        public void setVariable(String _varName, Object _value) {
            variables.put(_varName, _value);
        }

        /**
         * Getter for variable with specified name.
         * @param _varName name of variable we want to get
         * @return value of variable with specified name of null if it doesn't exist
         */
        public Object getVariable(String _varName) {
            for (String var : variables.keySet())
                if (var == _varName)
                    return variables.get(var);
            return null;
        }
    }
}
