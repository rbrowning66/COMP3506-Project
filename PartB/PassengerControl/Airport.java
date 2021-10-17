package PassengerControl;

import java.util.*;
import java.util.stream.Collectors;

public class Airport extends AirportBase {
    public LinkedList<Terminal> shuttleSystem;
    /**
     * Creates a new PassengerControl.AirportBase instance with the given capacity.
     *
     * @param capacity capacity of the airport shuttles
     *                 (same for all shuttles)
     */
    public Airport(int capacity) {
        super(capacity);
        this.shuttleSystem = new LinkedList<>();
    }

    @Override
    public TerminalBase opposite(ShuttleBase shuttle, TerminalBase terminal) {
        if (shuttle.getDestination().equals(terminal)) {
            return shuttle.getOrigin();
        } else if (shuttle.getOrigin().equals(terminal)) {
            return shuttle.getDestination();
        } else {
            return null;
        }
    }

    @Override
    public TerminalBase insertTerminal(TerminalBase terminal) {
//        shuttleSystem.add(new Terminal(terminal.getId(), terminal.getWaitingTime()));
        this.shuttleSystem.add((Terminal) terminal);
        return terminal;
    }

    @Override
    public ShuttleBase insertShuttle(TerminalBase origin, TerminalBase destination, int time) {
//        this.shuttleSystem.get(this.shuttleSystem.indexOf())
        Shuttle newShuttle = new Shuttle(origin, destination, time);
        this.shuttleSystem.get(this.shuttleSystem.indexOf((Terminal) origin)).addShuttleToTerminal(time, (Terminal) destination);
        this.shuttleSystem.get(this.shuttleSystem.indexOf((Terminal) destination)).addShuttleToTerminal(time, (Terminal) origin);
        return newShuttle;
    }

    @Override
    public boolean removeTerminal(TerminalBase terminal) {
        try {
            this.shuttleSystem.get(this.shuttleSystem.indexOf((Terminal) terminal)).removeShuttleFromTerminal(terminal);
            this.shuttleSystem.remove(this.shuttleSystem.get(this.shuttleSystem.indexOf((Terminal) terminal)));
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean removeShuttle(ShuttleBase shuttle) {
        Terminal shuttleOrigin = (Terminal) shuttle.getOrigin();
        Terminal shuttleDestination = (Terminal) shuttle.getDestination();
        try {
            this.shuttleSystem.get(this.shuttleSystem.indexOf(shuttleOrigin)).removeShuttleFromTerminal(shuttleDestination);
            this.shuttleSystem.get(this.shuttleSystem.indexOf(shuttleDestination)).removeShuttleFromTerminal(shuttleOrigin);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<ShuttleBase> outgoingShuttles(TerminalBase terminal) {
        Terminal terminalToCheck = (Terminal) terminal;
        return terminalToCheck.outgoingShuttles;
    }

    @Override
    public Path findShortestPath(TerminalBase origin, TerminalBase destination) {
        // Using breadth first search to find the shortest path (not fastest)
        LinkedList<TerminalBase> adjacentListQueue = new LinkedList<>();
        LinkedList<TerminalBase> shortestPath = new LinkedList<>();
        HashMap<Terminal, Boolean> terminalsVisited = new HashMap<>();
        HashMap<Terminal, Integer> distances = new HashMap<>();
        HashMap<Terminal, Terminal> previousTerminal = new HashMap<>();

        for (Terminal t : shuttleSystem) {
//            previousTerminal.add(null);
            previousTerminal.put(t, null);
            terminalsVisited.put(t, false);
            distances.put(t, Integer.MAX_VALUE);
        }

        terminalsVisited.put((Terminal) origin, true);
        distances.replace((Terminal) origin, Integer.MAX_VALUE, origin.getWaitingTime());
        adjacentListQueue.add(origin);

        int totalEdgeWeights = 0;
        int totalWaitTime = 0;

        while (adjacentListQueue.size() != 0) {
            Terminal t = (Terminal) adjacentListQueue.remove();

            for (int i = 0; i < this.shuttleSystem.get(this.shuttleSystem.indexOf(t)).outgoingShuttles.size(); i++) {
                Terminal terminalCheck =
                        (Terminal) this.shuttleSystem.get(this.shuttleSystem.indexOf(t)).outgoingShuttles.get(i).getDestination();
                if (!terminalsVisited.get(terminalCheck)) {
                    terminalsVisited.replace(terminalCheck, false, true);
                    distances.replace(terminalCheck, Integer.MAX_VALUE,
                            distances.get(t) + this.shuttleSystem.get(this.shuttleSystem.indexOf(t)).outgoingShuttles.get(i).getTime());
                    previousTerminal.replace(terminalCheck, null, t);
                    adjacentListQueue.add(terminalCheck);

                    if (terminalCheck == destination) {
                        Terminal step = (Terminal) destination;
                        shortestPath.add(previousTerminal.get(step));

                        while (previousTerminal.get(step) != null) {
                            shortestPath.add(step);
                            step = previousTerminal.get(step);
                        }

                        return new Path(shortestPath, distances.get(destination));
                    }
                }
            }

//            for (ShuttleBase shuttle : ((Terminal) origin).outgoingShuttles) {
//                adjacentTerminals.add((Terminal) shuttle.getDestination());
//                terminalsVisited.put((Terminal) shuttle.getDestination(), false);
//            }
//
//            for (Terminal adjacentTerminal : adjacentTerminals) {
//                TerminalBase n = adjacentListQueue.remove();
//                if (!terminalsVisited.get(adjacentTerminal)) {
//                    terminalsVisited.replace(adjacentTerminal, false, true);
//                    adjacentListQueue.add(adjacentTerminal);
//
//                    // Check if terminal is the destination terminal
//                    if (adjacentTerminal == destination) {
//                        return new Path(adjacentListQueue, adjacentListQueue.size());
//                    }
//                }
//            }
        }

        // If no path available from origin to destination
        return null;
    }

    @Override
    public Path findFastestPath(TerminalBase origin, TerminalBase destination) {
        return null;
    }


    static class Terminal extends TerminalBase {
        /* Implement all the necessary methods of the Terminal here */
        public List<ShuttleBase> outgoingShuttles;

        public void addShuttleToTerminal(int time, Terminal terminalDestination) {
//            outgoingShuttles.put(terminalDestination, time);
//            Terminal destinationTerminal = new Terminal(terminalDestination.getId(),
//                    terminalDestination.getWaitingTime());
//            destinationTerminal.addShuttleToTerminal(destinationTerminal.getWaitingTime(),
//                    terminalDestination);
//            Terminal destinationTerminal = new Terminal(terminalDestination.getId(),
//                    terminalDestination.getWaitingTime());
            this.outgoingShuttles.add(new Shuttle(this, terminalDestination, time));
        }

        public void removeShuttleFromTerminal(TerminalBase terminalDestination) {
            Terminal destination = (Terminal) terminalDestination;
            LinkedList<Terminal> adjacentTerminals = new LinkedList<>();
            for (ShuttleBase shuttle : this.outgoingShuttles) {
                adjacentTerminals.add((Terminal) shuttle.getDestination());
            }
//            this.outgoingShuttles.removeIf(shuttle -> shuttle.getDestination() ==
//                    terminalDestination);
            for (Terminal t : adjacentTerminals) {
                t.outgoingShuttles.removeIf(shuttleBase -> shuttleBase.getDestination() == terminalDestination);
            }

            this.outgoingShuttles.clear();
//            adjacentTerminals.removeIf(terminal -> terminal.outgoingShuttles.)
//            destination.outgoingShuttles.removeIf(shuttle -> shuttle.getDestination() == this);
        }

        /**
         * Creates a new TerminalBase instance with the given terminal ID
         * and waiting time.
         *
         * @param id          terminal ID
         * @param waitingTime waiting time for the terminal, in minutes
         */
        public Terminal(String id, int waitingTime) {
            super(id, waitingTime);
            this.outgoingShuttles = new ArrayList<>();
        }

    }

    static class Shuttle extends ShuttleBase {
        /* Implement all the necessary methods of the Shuttle here */

        /**
         * Creates a new ShuttleBase instance, travelling from origin to
         * destination and requiring 'time' minutes to travel.
         *
         * @param origin      origin terminal
         * @param destination destination terminal
         * @param time        time required to travel, in minutes
         */
        public Shuttle(TerminalBase origin, TerminalBase destination, int time) {
            super(origin, destination, time);
        }


    }

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of PassengerControl.Airport's run configuration
     */
    public static void main(String[] args) {
        Airport a = new Airport(3);
        Terminal terminalA = (Terminal) a.insertTerminal(new Terminal("A", 1));
        Terminal terminalB = (Terminal) a.insertTerminal(new Terminal("B", 3));
        Terminal terminalC = (Terminal) a.insertTerminal(new Terminal("C", 4));
        Terminal terminalD = (Terminal) a.insertTerminal(new Terminal("D", 2));

        Shuttle shuttle1 = (Shuttle) a.insertShuttle(terminalA, terminalB, 2);
        Shuttle shuttle2 = (Shuttle) a.insertShuttle(terminalA, terminalC, 5);
        Shuttle shuttle3 = (Shuttle) a.insertShuttle(terminalA, terminalD, 18);
        Shuttle shuttle4 = (Shuttle) a.insertShuttle(terminalB, terminalD, 8);
        Shuttle shuttle5 = (Shuttle) a.insertShuttle(terminalC, terminalD, 15);

        // Opposite
        assert a.opposite(shuttle1, terminalA).getId().equals("B");

        // Outgoing Shuttles
        assert a.outgoingShuttles(terminalA).stream()
                .map(ShuttleBase::getTime)
                .collect(Collectors.toList()).containsAll(List.of(2, 5, 18));

        // Remove Terminal
        a.removeTerminal(terminalC);
        assert a.outgoingShuttles(terminalA).stream()
                .map(ShuttleBase::getTime)
                .collect(Collectors.toList()).containsAll(List.of(2, 18));

        // Shortest path
        Path shortestPath = a.findShortestPath(terminalA, terminalD);
        assert shortestPath.terminals.stream()
                .map(TerminalBase::getId)
                .collect(Collectors.toList()).equals(List.of("A", "D"));
        assert shortestPath.time == 19;

        // Fastest path
        Path fastestPath = a.findFastestPath(terminalA, terminalD);
        assert fastestPath.terminals.stream()
                .map(TerminalBase::getId)
                .collect(Collectors.toList()).equals(List.of("A", "B", "D"));
        assert fastestPath.time == 14;
    }
}
