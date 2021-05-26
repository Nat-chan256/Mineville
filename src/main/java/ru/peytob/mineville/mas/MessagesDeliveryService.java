package ru.peytob.mineville.mas;

import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.mas.signal.CooperateSignal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Messages delivery service sends agents' signals to other agents.
 * Implements singleton pattern.
 * */
public class MessagesDeliveryService {

    /** The community of agents who will receive messages. */
    private AgentsCommunity agentsCommunity;

    /** The instance of the class. */
    private static MessagesDeliveryService instance;

    /** Executor service for sending signals mechanism. */
    private ExecutorService executorService;

    /** Is the instance sending signals. */
    private boolean isLaunched;

    /** The length of period between signals sending. */
    private int periodLength;

    /** The map where keys are agents and values are corresponding intentions. */
    private Map<Agent, Desire> sendersSignals;

    /** Constructor which initialize sendersSignals map. */
    public MessagesDeliveryService()
    {
        sendersSignals = new HashMap<>();

        executorService = Executors.newSingleThreadExecutor();
        periodLength = 1000;
    }

    public void setAgentsCommunity(AgentsCommunity _agentsCommunity)
    {
        agentsCommunity = _agentsCommunity;
    }


    /**
     * Add a sender to senders list.
     * @param _sender an agent whose signal will be sent to other agents
     */
    public void addSender(Agent _sender)
    {
        sendersSignals.put(_sender, _sender.getCurrentIntention());
    }

    /**
     * Check if messages delivery service is sending signals.
     * @return isLaunched flag
     */
    public boolean isLaunched()
    {
        return isLaunched;
    }

    /**
     * Remove a sender from the senders-signals dictionary.
     * @param _sender sender to remove
     */
    public void removeSender(Agent _sender)
    {
        sendersSignals.remove(_sender);
    }

    private Runnable sendSignals()
    {
        return () ->
        {
            while (true)
            {
                List<Agent> receivers = agentsCommunity.getAgents();
                for (Agent sender : sendersSignals.keySet())
                {
                    for (Agent receiver : receivers)
                    {
                        if (receiver == sender)
                        {
                            continue;
                        }
                        try {
                            receiver.receiveSignal(new CooperateSignal(sendersSignals.get(sender), sender));
                        }
                        catch (InterruptedException | ChildException ex)
                        {
                            System.out.println("Receiving signal error: " + ex.getMessage());
                            continue;
                        }
                    }
                    try{
                        Thread.sleep(periodLength);
                    } catch (InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        };
    }

    /** Launches sending messages mechanism. */
    public void start()
    {
        executorService.submit(sendSignals());
        isLaunched = true;
    }
}
