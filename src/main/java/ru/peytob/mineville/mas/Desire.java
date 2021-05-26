package ru.peytob.mineville.mas;

import java.util.Objects;
import java.util.function.Predicate;

/** Agent's desire class. */
public class Desire <T> {

    /** State in which an agent can achieve the desire. */
    private AgentState appropriateState;

    /** Argument that the predicate takes. */
    private T argument;

    /** Predicate that determines whether desire is achieved or not. */
    private Predicate<T> conditionToMeet;

    /** Can desire be an intention or not. */
    private boolean isAvailable;

    /** Can the desire be shared between several agents.
     * Has value "false" by default. */
    private boolean isShareable;

    /**
     * Constructor that sets predicate and its arguments.
     * When the predicate returns true, the desire is considered achieved
     * and agent doesn't try to reach it anymore.
     * @param _conditionToMeet predicate that determines whether desire is achieved or not
     * @param _predicateArg argument that the predicate takes
     */
    public Desire(Predicate<T> _conditionToMeet, T _predicateArg, AgentState _appropriateState)
    {
        conditionToMeet = _conditionToMeet;
        argument = _predicateArg;
        appropriateState = _appropriateState;
    }

    /**
     * Constructor that sets predicate and its arguments.
     * When the predicate returns true, the desire is considered achieved
     * and agent doesn't try to reach it anymore.
     * @param conditionToMeet predicate that determines whether desire is achieved or not
     * @param predicateArg argument that the predicate takes
     * @param isAvailable can the desire be intention or not
     */
    public Desire(Predicate<T> conditionToMeet, T predicateArg, AgentState _appropriateState, boolean isAvailable)
    {
        this(conditionToMeet, predicateArg, _appropriateState);
        this.isAvailable = isAvailable;
    }

    /**
     * Constructor that sets predicate and its arguments.
     * When the predicate returns true, the desire is considered achieved
     * and agent doesn't try to reach it anymore.
     * @param _conditionToMeet predicate that determines whether desire is achieved or not
     * @param _predicateArg argument that the predicate takes
     * @param _appropriateState state which determine agent's behavior so that it achieves the desire
     * @param _isAvailable can the desire be intention or not
     * @param _isShareable can the desire be shared between several agents
     */
    public Desire(Predicate<T> _conditionToMeet,
                  T _predicateArg,
                  AgentState _appropriateState,
                  boolean _isAvailable,
                  boolean _isShareable)
    {
        this(_conditionToMeet, _predicateArg, _appropriateState, _isAvailable);
        isShareable = _isShareable;
    }

    public AgentState getAppropriateState()
    {
        return appropriateState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desire<?> desire = (Desire<?>) o;
        return isAvailable == desire.isAvailable
                && (argument == null && desire.argument == null ||  argument.equals(desire.argument))
                && conditionToMeet.equals(desire.conditionToMeet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(argument, conditionToMeet, isAvailable);
    }

    /**
     * Checks if underlying predicate returns true.
     * @return true if condition to meet returns true; false - otherwise
     */
    public boolean isAchieved()
    {
        return conditionToMeet.test(argument);
    }

    /**
     * Check if the desire can be an intention.
     * @return true if the desire is available; false - otherwise
     */
    public boolean isAvailable()
    {
        return isAvailable;
    }

    /**
     * Check if the desire can be shared between several agents.
     * @return true if the desire is shareable; false - otherwise
     */
    public boolean isShareable()
    {
        return isShareable;
    }

    /**
     * Checks if argument is not null.
     * @return true if argument is not null; false - otherwise
     */
    public boolean isNotNull()
    {
        return conditionToMeet.test(this.argument);
    }
}
