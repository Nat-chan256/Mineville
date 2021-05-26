package ru.peytob.mineville.mas.signal;

import ru.peytob.mineville.mas.Agent;
import ru.peytob.mineville.mas.Desire;

public class CooperateSignal extends Signal {

    /** The goal which the sender offers to share. */
    private Desire intention;

    /** A sender of the signal. */
    private Agent sender;

    public CooperateSignal(Desire _intention, Agent _sender)
    {
        intention = _intention;
        sender = _sender;
    }

    public Desire getIntention()
    {
        return intention;
    }
}
