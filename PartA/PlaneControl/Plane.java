package PlaneControl;
public class Plane extends PlaneBase {

    public Plane(String planeNumber, String time) {
        super(planeNumber, time);
    }

    /* Implement all the necessary methods of Plane here */
    @Override
    public int compareTo(PlaneBase o) {
        // Lexicographic ordering if departure time is the same here
        String thisPlaneNumber = this.getPlaneNumber();
        String otherPlaneNumber = o.getPlaneNumber();

        // Assume plane numbers will never be the same
        return thisPlaneNumber.substring(0, 2).compareTo(otherPlaneNumber.substring(0, 2));

        // Loops through both plane's plane numbers, it can be assumed that both plane numbers are
        // the same length if this plane number is more than return 1. Vice versa return -1.
//        return thisPlaneNumber.compareTo(otherPlaneNumber);
//        for (int k = 0; k < thisPlaneNumber.length(); k++) {
//            if ((thisPlaneNumber.charAt(k) - otherPlaneNumber.charAt(k)) != 0) {
//                if (thisPlaneNumber.charAt(k) > otherPlaneNumber.charAt(k)) {
//                    return 1;
//                } else {
//                    return -1;
//                }
//            }
//        }
//
//        // Doesn't need to check difference in length as plane number is always the same length
//        return 0;
    }

}
