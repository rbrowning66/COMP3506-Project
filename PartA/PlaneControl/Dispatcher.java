package PlaneControl;

public class Dispatcher extends DispatcherBase {
    LandingAllocationsTree landingAllocationsTree;

    public Dispatcher() {
        this.landingAllocationsTree = new LandingAllocationsTree();
    }

    @Override
    public int size() {
        return landingAllocationsTree.countNodes(landingAllocationsTree.rootNode);
    }

    @Override
    public void addPlane(String planeNumber, String time) {
        Plane newPlane = new Plane(planeNumber, time);
        landingAllocationsTree.addNode(landingAllocationsTree.rootNode, newPlane);
    }

    @Override
    public String allocateLandingSlot(String currentTime) {
        String currentTimeFormatted = currentTime.replaceAll(":", "");
        int getCurrentTime = Integer.parseInt(currentTimeFormatted);
        Plane nextPlane = landingAllocationsTree.rootNode.plane;

        if (nextPlane != null) {
            if ((landingAllocationsTree.getDepartureTimeUsable(nextPlane) - getCurrentTime) <= 5) {

            }
        }
    }

    @Override
    public String emergencyLanding(String planeNumber) {
        return null;
    }

    @Override
    public boolean isPresent(String planeNumber) {
        return false;
    }

    /* Implement all the necessary methods of Dispatcher here */
    // Binary search tree possibly

}

class LandingAllocationsLinkedList {
    Node rootNode;
    static class Node {
        Plane plane;
        Node left;
        Node right;

        Node(Plane plane) {
            this.plane = plane;
            left = null;
            right = null;
        }
    }

    public void addNode(Node curNode, Plane newPlane) {
        int arrivalTime = getDepartureTimeUsable(newPlane);
        int curNodeArrivalTime = getDepartureTimeUsable(curNode.plane);

        if (arrivalTime < curNodeArrivalTime) {
            if (curNode.left != null) {
                addNode(curNode.left, newPlane);
            } else {
                curNode.left = new Node(newPlane);
            }
        } else if (arrivalTime > curNodeArrivalTime) {
            if (curNode.right != null) {
                addNode(curNode.right, newPlane);
            } else {
                curNode.right = new Node(newPlane);
            }
        } else {
            if (curNode.plane.compareTo(newPlane) > 0) {
                if (curNode.right != null) {
                    addNode(curNode.right, newPlane);
                } else {
                    curNode.right = new Node(newPlane);
                }
            } else if (curNode.plane.compareTo(newPlane) < 0) {
                if (curNode.left != null) {
                    addNode(curNode.left, newPlane);
                } else {
                    curNode.left = new Node(newPlane);
                }
            }
        }
    }

    public int countNodes(Node rootNode) {
        if (rootNode == null) {
            return 0;
        }

        return (countNodes(rootNode.left) + countNodes(rootNode.right) + 1);
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