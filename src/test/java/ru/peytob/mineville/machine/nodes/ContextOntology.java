package ru.peytob.mineville.machine.nodes;

import ru.peytob.mineville.mas.Ontology;

import java.util.HashMap;
import java.util.Map;

public class ContextOntology extends Ontology {

    private Map<String, Object> variables;

    public ContextOntology()
    {
        variables = new HashMap<>();
    }

    public Object getVariable(String _variableName)
    {
        return variables.get(_variableName);
    }

    public void setVariable(String _variableName, Object _value)
    {
        variables.put(_variableName, _value);
    }
}
