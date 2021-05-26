package ru.peytob.mineville.mas;

import java.util.ArrayList;
import java.util.List;

/** Ontology class represents the knowledge base of agents. */
public class Ontology {

    /** The list of observers.
     * The ontology will call observer.update() method for each observer in the list
     * when some changes take place. */
    private List<IObserver> observersList;

    /** Waiting period for the wait state. */
    private int waitingPeriod = 1000;

    public int getWaitingPeriod()
    {
        return waitingPeriod;
    }

    /**
     * Sets the observer. Wherein the previous observer(-s) are remove from the list.
     * @param observer observer to be set
     */
    public void setObserver(IObserver observer)
    {
        if (observersList == null)
        {
            observersList = new ArrayList<>();
        }
        observersList.clear();
        observersList.add(observer);
    }

    public void setObservers(List<IObserver> _observers)
    {
        observersList = _observers;
    }

    /** Notify observers about changes occurred in ontology. */
    public void notifyObservers() throws InterruptedException
    {
        for (IObserver observer : observersList)
        {
            observer.update();
        }
    }
}
