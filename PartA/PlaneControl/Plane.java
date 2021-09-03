package PlaneControl;

public class Plane extends PlaneBase {

    public Plane(String planeNumber, String time) {
        super(planeNumber, time);
    }

    @Override
    public int compareTo(PlaneBase o) {
        // Lexicographic ordering if departure time is the same here
        return 0;
    }

    /* Implement all the necessary methods of Plane here */
}
