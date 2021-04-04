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
    protected TaskController taskController;
    protected Context context;
    protected NodeState state;

    public BehaviorTree() {
        context = new Context();
        taskController = new TaskController();
        root = new RootNode(taskController, context);
    }

    //Метод, обновляющий состояние дерева
    public abstract NodeState tick();

    public class TaskController {
        Node currentTask;
        TCThread currentThread;
        boolean currentTaskFinished;

        public TaskController() {
            currentTaskFinished = true;
        }

        public void interruptCurrentTask() {
            currentThread.disable();
            currentTaskFinished = true;
            currentTask.setState(NodeState.READY);
            if (currentTask.getParent() != null)
                try {
                    currentTask.getParent().resetCurrentSubtask();
                } catch (ChildException ex) {
                    System.out.println(ex);
                }
        }

        public void interruptTask(Node _task) {
            if (currentTask == _task) {
                currentThread.disable();
                currentTaskFinished = true;
                currentTask.setState(NodeState.READY);
                if (currentTask.getParent() != null)
                    try {
                        currentTask.getParent().resetCurrentSubtask();
                    } catch (ChildException ex) {
                        System.out.println(ex);
                    }
            }
        }

        public void performTask(Node _task) {
            if (_task == null) {
                _task.setState(NodeState.ERROR);
                throw new NullPointerException();
            }

            if (!(_task instanceof LeafNode)) //Если текущий узел - не листовой
            {
                performTask(_task.getCurrentSubtask()); //Вызываем эту же функцию для потомка узла
                return;
            }

            //Ждем, пока текущая задача завершится
            for (; !currentTaskFinished; ) ;

            currentTask = _task;

            //Выделяем поток для новой задачи
            currentTaskFinished = false;
            currentThread = new TCThread((LeafNode) _task);
            currentThread.start();
        }

        //Класс для работы с потоками
        class TCThread extends Thread {
            private LeafNode taskToPerform;
            private boolean isActive;

            TCThread(LeafNode _taskToPerform) {
                super();
                taskToPerform = _taskToPerform;
                isActive = true;
            }

            public void run() {
                while (isActive && !currentTaskFinished) {
                    taskToPerform.doAction();
                    currentTaskFinished = true;
                }
            }

            public void disable() {
                isActive = false;
            }
        }

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
