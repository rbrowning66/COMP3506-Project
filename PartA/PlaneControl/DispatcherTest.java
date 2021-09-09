package PlaneControl;

import org.junit.jupiter.api.Test;

public class DispatcherTest {
    @Test
    void addPlane() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.addPlane("DEF9237", "15:20");
        System.out.println(dispatcher.size());
        System.out.println(dispatcher.isPresent("DEF9237"));
    }

    @Test
    void getSize() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.addPlane("DEF9237", "15:20");
        dispatcher.addPlane("RIG2493", "8:14");
        dispatcher.addPlane("LRO0183", "23:45");
        System.out.println(dispatcher.size());
    }

    @Test
    void allocatePlane() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.addPlane("EAA1110", "15:43");
        dispatcher.addPlane("ANC3480", "12:12");
        dispatcher.addPlane("ABC1230", "05:42");
        dispatcher.addPlane("AAC3480", "18:43");
        System.out.println(dispatcher.landingAllocationsLinkedList.head.plane.getPlaneNumber());
        System.out.println(dispatcher.landingAllocationsLinkedList.head.next.plane.getPlaneNumber());
        System.out.println(dispatcher.landingAllocationsLinkedList.head.next.next.plane.getPlaneNumber());
        System.out.println(dispatcher.landingAllocationsLinkedList.head.next.next.next.plane.getPlaneNumber());
        System.out.println("ALLOCATING PLANE");
        System.out.println(dispatcher.allocateLandingSlot("05:41"));
        System.out.println(dispatcher.landingAllocationsLinkedList.head.plane.getPlaneNumber());
        System.out.println(dispatcher.landingAllocationsLinkedList.head.next.plane.getPlaneNumber());
        System.out.println(dispatcher.landingAllocationsLinkedList.head.next.next.plane.getPlaneNumber());
    }

    @Test
    void emergencyLanding() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.addPlane("DEF9237", "15:20");
        dispatcher.addPlane("RIG2493", "8:14");
        dispatcher.addPlane("LRO0183", "23:45");
        dispatcher.emergencyLanding("RIG2493");
        System.out.println(dispatcher.landingAllocationsLinkedList.head.plane.getPlaneNumber());
        System.out.println(dispatcher.landingAllocationsLinkedList.head.next.plane.getPlaneNumber());
    }
}
