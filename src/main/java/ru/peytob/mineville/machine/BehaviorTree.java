package ru.peytob.mineville.machine;

import java.util.HashMap;
import java.util.Map;

import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node.NodeState;
import ru.peytob.mineville.machine.nodes.Node.ChildException;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.machine.nodes.RootNode;

public abstract class BehaviorTree implements IBehaviorTree {

    protected RootNode root;
    protected Context context;
    protected NodeState state;

    public BehaviorTree() {
        context = new Context();
        root = new RootNode(context);
    }

    /** Sets the states of all the tree nodes as "ready" */
    public void setReady()
    {
        try {
            root.setReady();
        }
        catch(ChildException ex)
        {}
    }


    //Метод, обновляющий состояние дерева
    public NodeState tick()
    {
        return root.tick();
    }



    //Контекст хранит переменные, которые нужны узлам для выполнения их задач
    public class Context {
        //Переменные хранятся в словаре, где ключ - имя переменной, а значение - соответсвенно, её значение
        private Map<String, Object> variables; // (надеюсь придумать более изящное решение
        // так что это, вероятно, временная мера)

        public Context() {
            variables = new HashMap<String, Object>();
        }

        public void setVariable(String _varName, Object _value) {
            variables.put(_varName, _value);
        }

        public Object getVariable(String _varName) {
            for (String var : variables.keySet())
                if (var == _varName)
                    return variables.get(var);
            return null;
        }
    }
}
