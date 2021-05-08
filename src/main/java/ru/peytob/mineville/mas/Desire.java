package ru.peytob.mineville.mas;

import java.util.function.Predicate;

/** Agent's desire class. */
public class Desire <T> {

    /** Argument that the predicate takes. */
    private T argument;

    /** Predicate that determines whether desire is achieved or not. */
    private Predicate<T> conditionToMeet;

    /** Can desire be an intention or not. */
    private boolean isAvailable;

    /**
     * Constructor that sets predicate and its arguments.
     * When the predicate returns true, the desire is considered achieved
     * and agent doesn't try to reach it anymore.
     * @param _conditionToMeet predicate that determines whether desire is achieved or not
     * @param _predicateArg argument that the predicate takes
     */
    public Desire(Predicate<T> _conditionToMeet, T _predicateArg)
    {
        conditionToMeet = _conditionToMeet;
        argument = _predicateArg;
    }

    /**
     * Constructor that sets predicate and its arguments.
     * When the predicate returns true, the desire is considered achieved
     * and agent doesn't try to reach it anymore.
     * @param conditionToMeet predicate that determines whether desire is achieved or not
     * @param predicateArg argument that the predicate takes
     * @param isAvailable can the desire be intention or not
     */
    public Desire(Predicate<T> conditionToMeet, T predicateArg, boolean isAvailable)
    {
        this(conditionToMeet, predicateArg);
        this.isAvailable = isAvailable;
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
     * Checks if argument is not null.
     * @return true if argument is not null; false - otherwise
     */
    public boolean isNotNull()
    {
        return conditionToMeet.test(this.argument);
    }
}
