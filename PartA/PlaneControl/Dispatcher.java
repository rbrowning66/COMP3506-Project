package PlaneControl;

public class Dispatcher extends DispatcherBase {
    LandingAllocationsLinkedList landingAllocationsLinkedList;

    public Dispatcher() {
        this.landingAllocationsLinkedList = new LandingAllocationsLinkedList();
    }

    @Override
    public int size() {
        return landingAllocationsLinkedList.countNodes(landingAllocationsLinkedList.head);
    }

    @Override
    public void addPlane(String planeNumber, String time) {
        Plane newPlane = new Plane(planeNumber, time);
        landingAllocationsLinkedList.addNode(newPlane);
    }

    @Override
    public String allocateLandingSlot(String currentTime) {
        String currentTimeFormatted = currentTime.replaceAll(":", "");
        int getCurrentTime = Integer.parseInt(currentTimeFormatted);
        Plane nextPlane = landingAllocationsLinkedList.head.plane;

        if (nextPlane != null) {
            if ((landingAllocationsLinkedList.getDepartureTimeUsable(nextPlane) - getCurrentTime) <= 5) {
                Node temp = landingAllocationsLinkedList.head;
                landingAllocationsLinkedList.head = landingAllocationsLinkedList.head.next;

                return landingAllocationsLinkedList.head.plane.getPlaneNumber();
            }
        }

        return null;
    }

    @Override
    public String emergencyLanding(String planeNumber) {
        landingAllocationsLinkedList.deleteNode(planeNumber);
    }

    @Override
    public boolean isPresent(String planeNumber) {
        return false;
    }

    /* Implement all the necessary methods of Dispatcher here */
    // Binary search tree possibly

}

class Node {
    Plane plane;
    Node next;

    Node(Plane plane) {
        this.plane = plane;
    }
}

class LandingAllocationsLinkedList {
    Node head;

    public int countNodes(Node rootNode) {
        if (rootNode == null) {
            return 0;
        }

        int count = 1;

        while (rootNode != null) {
            rootNode = rootNode.next;
            count++;
        }

        return count;
    }

    public void addNode(Plane plane) {
        Node previousPlane = null;
        Node newPlane = new Node(plane);
        Node curPlane = head;
        int newArrivalTime = getDepartureTimeUsable(plane);
        int curNodeArrivalTime = getDepartureTimeUsable(head.plane);

        // Loops through list putting node in sorted list
        while (curPlane != null && newArrivalTime > curNodeArrivalTime) {
            previousPlane = curPlane;
            curPlane = curPlane.next;
        }

        // Adds new node to beginning if conditions passed
        if (previousPlane == null) {
            head = newPlane;
        } else {
            previousPlane.next = newPlane;
        }

        newPlane.next = curPlane;
    }

    public String deleteNode(String planeNumber) {
        if (head == null) {
            return null;
        }

        Node headTemp = head;

        if (head.plane.getPlaneNumber().equals(planeNumber)) {
            head = headTemp.next;
            return planeNumber;
        }

        // Finding previous node
        for (Plane plane = head.plane; headTemp != null && i < planeNumber)
    }

    public LandingAllocationsLinkedList() {
        LandingAllocationsLinkedList landingAllocationsLinkedList =
                new LandingAllocationsLinkedList();
        landingAllocationsLinkedList.head = null;
    }
}



/* Add any additional helper classes here */
// Converts digital clock formatted time to number to be used in sorting
public int getDepartureTimeUsable(Plane plane) {
    String time = plane.getTime();
    String timeFormatted = time.replaceAll(":", "");
    Character firstNumber = time.charAt(0);

    if (firstNumber.equals('0')) {
        timeFormatted = timeFormatted.substring(1);
    }

    return Integer.parseInt(timeFormatted);
}