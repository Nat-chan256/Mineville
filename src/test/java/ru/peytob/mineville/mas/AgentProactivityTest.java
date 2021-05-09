package ru.peytob.mineville.mas;

import org.junit.Assert;
import org.junit.Test;
import ru.peytob.mineville.machine.BehaviorTree;
import ru.peytob.mineville.machine.nodes.ChildException;
import ru.peytob.mineville.machine.nodes.LeafNode;
import ru.peytob.mineville.machine.nodes.Node;
import ru.peytob.mineville.machine.nodes.SequenceNode;
import ru.peytob.mineville.machine.nodes.decorators.UntilSuccessNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Class that tests the following agents' properties:
 * - proactivity (goals achievement)
 * - reactivity (plans changing)
 * - sociality (signal exchange)
 * */
public class AgentsPropertiesTest {

    /** Check if an agent can achieve his goal. */
    @Test
    public void testAgentsProactivity()
    {
        // Simulate people's moving
        PeopleMovingSimulator peopleSimulator = new PeopleMovingSimulator();
        peopleSimulator.startSimulation();

        // Launch the drone to find lost people
        DronesManager.RescueDrone drone = DronesManager.getRescueDroneInstance();
        DronesManager.RescueDronesOntology ontology = (DronesManager.RescueDronesOntology) DronesManager.getOntology();

        while(drone.isActing());
        peopleSimulator.stopSimulation();

        // Error
        double delta = 1.0;
        Assert.assertTrue(areApproximatelyEqual(
                peopleSimulator.getCurrentCoordinates(),
                ontology.getLostPeopleCoords(),
                delta
        ));
    }

    /**
     * Auxiliary method that checks if abs(v1.fst - v2.fst) < delta && abs(v1.snd - v2.snd) < delta (1).
     * @param v1 first pair to be compared
     * @param v2 second pair to be compared
     * @param delta error
     * @return true if condition (1) is met; false - otherwise
     */
    private boolean areApproximatelyEqual(Pair<Double, Double> v1, Pair<Double, Double> v2, double delta)
    {
        return abs(v1.fst - v2.fst) < delta && abs(v1.snd - v2.snd) < delta;
    }

}

/** Manager of drones. */
class DronesManager{

    /** The list of existed rescue drones. */
    private static List<RescueDrone> dronesList;

    /** The ontology of all agents of this class. */
    private static Ontology ontology;

    public static Ontology getOntology() {
        if (ontology == null)
        {
            ontology = new RescueDronesOntology();
        }
        return ontology;
    }

    /**
     * Returns an instance of RescueDrone class with ontology set.
     * @return new instance of rescue drone.
     */
    public static RescueDrone getRescueDroneInstance()
    {
        RescueDrone newDrone = new RescueDrone();

        if (dronesList == null)
        {
            dronesList = new ArrayList<>();
        }
        dronesList.add(newDrone);
        newDrone.setOntology(ontology);
        return newDrone;
    }

    /**
     * Rescue drone whose goal is to find lost people in the open area.
     */
    static class RescueDrone extends Agent
    {
        /** Constructor that sets the goals of the drone. */
        public RescueDrone()
        {
            super();
        }

        /** Tick behavior tree until its state is either SUCCESS or FAIL. */
        @Override
        public void act() {
            while (!(currentBehavior.getState() == Node.NodeState.SUCCESS
                    || currentBehavior.getState() == Node.NodeState.FAIL))
            {
                currentBehavior.tick();
            }
        }

        @Override
        public void setBehaviorTree() {
            currentBehavior = new SeekPeopleBT();
        }

        @Override
        public void setDesires() {
            desiresList.add(new Desire(x -> x != null, RescueDronesOntology.getLostPeopleCoords(), true));
        }

        /**
         * Checks if method act() is performing at the moment.
         * @return true if act() is performing; false - otherwise
         */
        public boolean isActing()
        {
            return thread.isAlive();
        }

    }

    static class RescueDronesOntology extends Ontology
    {
        private static Pair<Double, Double> lostPeopleCoords;

        public static Pair<Double, Double> getLostPeopleCoords() {
            return lostPeopleCoords;
        }

        public static void setLostPeopleCoordinates(Pair<Double, Double> _coords)
        {
            lostPeopleCoords = _coords;
        }
    }

    /** Behavior tree to seek people in the area. */
    static class SeekPeopleBT extends BehaviorTree
    {
        public SeekPeopleBT()
        {
            super();
            //=========First level============
            root = new UntilSuccessNode(context);
            //=========Second level============
            SequenceNode sequenceNode = new SequenceNode(context);
            //=========Third level=============
            ArePeopleNearbyNode arePeopleNearbyNode = new ArePeopleNearbyNode(context);
            MoveNode moveNode = new MoveNode(context);

            try {
                root.addChild(sequenceNode);
                sequenceNode.addChild(moveNode);
                sequenceNode.addChild(arePeopleNearbyNode);
            }catch(ChildException ex)
            {
                System.out.println("Adding child error: " + ex.getMessage());
            }

            context.setVariable("sightRadius", 5.0);
            context.setVariable("droneCoordinates", new Pair<Double, Double>(0.0,0.0));
            context.setVariable("droneDirection", 2);
            context.setVariable("droneMainDirection", 1);
        }

        /**
         * Leaf node that checks if people get in sight of the drone.
         * */
        class ArePeopleNearbyNode extends LeafNode
        {

            /**
             * Constructor that sets the link on the context.
             *
             * @param context context of the tree the node belong to
             */
            public ArePeopleNearbyNode(Context context) {
                super(context);
            }

            /**
             * Check if people come into drone's view.
             */
            @Override
            public void performTask()
            {
                Pair<Double, Double> peoplesCoords = PeopleMovingSimulator.getCurrentCoordinates();
                Double sightRadius = (Double)context.getVariable("sightRadius");
                Pair<Double, Double> droneCoords = (Pair<Double, Double>)context
                        .getVariable("droneCoordinates");

                if (peoplesCoords == null || sightRadius == null || droneCoords == null)
                {
                    state = NodeState.ERROR;
                }
                else if (Math.sqrt( Math.pow(peoplesCoords.fst - droneCoords.fst, 2)
                        + Math.pow(peoplesCoords.snd - droneCoords.snd, 2) ) > sightRadius)
                {
                    state = NodeState.FAIL;
                }
                else
                {
                    state = NodeState.SUCCESS;
                    RescueDronesOntology.setLostPeopleCoordinates(peoplesCoords);
                }
            }
        }

        /** Leaf node that changes current coordinates of the drone. */
        class MoveNode extends LeafNode{

            /**
             * Constructor that sets the link on the context.
             *
             * @param context context of the tree the node belong to
             */
            public MoveNode(Context context) {
                super(context);
            }

            private void moveDown(Integer direction, Pair<Double, Double> coordinates)
            {
                //Directions:
                // 0 - left
                // 1 - up
                // 2 - right
                // 3 - down
                switch(direction)
                {
                    case 0: // Left
                        if (coordinates.fst <= 0 && coordinates.snd <= 0) // Bottom left corner
                        {
                            context.setVariable("droneMainDirection", 1);
                            context.setVariable("droneDirection", 1);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd + 1));
                        }
                        else if (coordinates.fst <= 0) // Left edge
                        {
                            context.setVariable("droneDirection", 3);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd - 1));
                        }
                        else // Just move left
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst - 1, coordinates.snd));
                        }
                        break;
                    case 2: // Right
                        if (coordinates.fst >= Area.getWidth() && coordinates.snd <= 0) // Bottom right corner
                        {
                            context.setVariable("droneMainDirection", 1);
                            context.setVariable("droneDirection", 1);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd + 1));
                        }
                        else if (coordinates.fst >= Area.getWidth()) // Right edge
                        {
                            context.setVariable("droneDirection", 3);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd - 1));
                        }
                        else // Just move right
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst + 1, coordinates.snd));
                        }
                        break;
                    case 3: // Down
                        if (coordinates.fst <= 0) // Move to the right
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst + 1, coordinates.snd));
                            context.setVariable("droneDirection", 2);
                        }
                        else // Move to the left
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst - 1, coordinates.snd));
                            context.setVariable("droneDirection", 0);
                        }
                        break;
                }
            }

            private void moveUp(Integer direction, Pair<Double, Double> coordinates)
            {
                //Directions:
                // 0 - left
                // 1 - up
                // 2 - right
                // 3 - down
                switch(direction)
                {
                    case 0: // Left
                        if (coordinates.fst <= 0 && coordinates.snd >= Area.getHeight()) // Top left corner
                        {
                            context.setVariable("droneMainDirection", 0);
                            context.setVariable("droneDirection", 3);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd - 1));
                        }
                        else if (coordinates.fst <= 0) // Left edge
                        {
                            context.setVariable("droneDirection", 1);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd + 1));
                        }
                        else // Just move left
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst - 1, coordinates.snd));
                        }
                        break;
                    case 1: // Up
                        if (coordinates.fst <= 0) // Move to the right
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst + 1, coordinates.snd));
                            context.setVariable("droneDirection", 2);
                        }
                        else // Move to the left
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst - 1, coordinates.snd));
                            context.setVariable("droneDirection", 0);
                        }
                        break;
                    case 2: // Right
                        if (coordinates.fst >= Area.getWidth() && coordinates.snd >= Area.getHeight()) // Top right corner
                        {
                            context.setVariable("droneMainDirection", 0);
                            context.setVariable("droneDirection", 3);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd - 1));
                        }
                        else if (coordinates.fst >= Area.getWidth()) // Right edge
                        {
                            context.setVariable("droneDirection", 1);
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst, coordinates.snd + 1));
                        }
                        else // Just move right
                        {
                            context.setVariable("droneCoordinates",
                                    new Pair<Double, Double>(coordinates.fst + 1, coordinates.snd));
                        }
                        break;
                }
            }

            /** Moves as a "snake". */
            @Override
            public void performTask()
            {
                super.performTask();
                Integer direction = (Integer)context.getVariable("droneDirection");
                // Main directions:
                // 0 - down
                // 1 - up
                Integer mainDirection = (Integer)context.getVariable("droneMainDirection");
                Pair<Double, Double> droneCoords = (Pair<Double, Double>)context
                        .getVariable("droneCoordinates");

                if (direction == null || droneCoords == null || mainDirection == null)
                {
                    state = NodeState.ERROR;
                    return;
                }

                switch(mainDirection)
                {
                    case 0:
                        moveDown(direction, droneCoords);
                        break;
                    case 1:
                        moveUp(direction, droneCoords);
                        break;
                }
            }
        }
    }
}

/** Class that simulates people's walking by changing their coordinates. */
class PeopleMovingSimulator{
    private static Pair<Double, Double> currentCoordinates;

    private int direction;

    private Thread thread;

    /** Generate random start coordinates. */
    public PeopleMovingSimulator()
    {
        Random rand = new Random();
        currentCoordinates = new Pair<Double, Double>(
                rand.nextDouble() * (Area.getWidth()),
                rand.nextDouble() * (Area.getHeight())
        );

        thread = new Thread(() -> {
            direction = generateDirection();
            switch(direction)
            {
                case 0: // To the left
                    currentCoordinates.fst = currentCoordinates.fst - 0.01;
                    break;
                case 1: // To the up
                    currentCoordinates.snd = currentCoordinates.snd + 0.01;
                    break;
                case 2: // To the right
                    currentCoordinates.fst = currentCoordinates.fst + 0.01;
                    break;
                case 3: // To the down
                    currentCoordinates.snd = currentCoordinates.snd - 0.01;
                    break;
            }
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex)
            {
                System.out.println("Direction generation error: " + ex.getMessage());
            }
        });
    }

    public static Pair<Double, Double> getCurrentCoordinates() {
        return currentCoordinates;
    }

    /** Generate direction depending on current direction and current coordinates values. */
    private int generateDirection() {
        List<Integer> possibleValues = new ArrayList<Integer>();
        possibleValues.add(direction);
        possibleValues.add(direction == 0 ? 3 : direction - 1);
        possibleValues.add(direction == 3 ? 0 : direction + 1);


        // Prevent from going beyond the field
        if (currentCoordinates.fst == 0) {
            possibleValues.remove(new Integer(0));
        }
        if (currentCoordinates.fst == Area.getWidth()) {
            possibleValues.remove(new Integer(2));
        }
        if (currentCoordinates.snd == 0) {
            possibleValues.remove(new Integer(3));
        }
        if (currentCoordinates.fst == Area.getHeight()) {
            possibleValues.remove(new Integer(1));
        }

        Random rand = new Random();
        int index = rand.nextInt(3);
        return possibleValues.get(index);
    }

    public void startSimulation()
    {
        thread.start();
    }

    public void stopSimulation()
    {
        thread.interrupt();
    }
}

/** Area which is explored by drone. */
class Area{

    /** The width of the area. */
    private static Double width = 10.0;

    /** The height of the area. */
    private static Double height = 10.0;

    public static Double getHeight()
    {
        return height;
    }

    public static Double getWidth() {
        return width;
    }
}

/** Class Pair with not final fields. */
class Pair<T1, T2>{
    public T1 fst;
    public T2 snd;

    public Pair(T1 first, T2 second)
    {
        fst = first;
        snd = second;
    }
}