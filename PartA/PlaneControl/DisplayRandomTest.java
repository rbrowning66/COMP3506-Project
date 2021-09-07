package PlaneControl;

import org.junit.jupiter.api.Test;

public class DisplayRandomTest {
    @Test
    void fivePlanesAllDifferentUnsorted() {
        String[] csvLines = {"XYJ3442,8:44", "SJK2217,18:46", "ADZ8264,2:03", "FGI4927,15:35",
                "RPT2964,18:12"};
        DisplayRandom display = new DisplayRandom(csvLines);
        Plane[] sortedPlanes = display.sort();
        System.out.println(sortedPlanes[0]);
        System.out.println(sortedPlanes[1]);
        System.out.println(sortedPlanes[2]);
        System.out.println(sortedPlanes[3]);
        System.out.println(sortedPlanes[4]);
    }

    @Test
    void fivePlanesDuplicateDepartureTimesUnsorted() {
        String[] csvLines = {"XYJ3442,8:44", "SJK2217,18:46", "ADZ8264,18:46", "FGI4927,15:35",
                "RPT2964,18:12"};
        DisplayRandom display = new DisplayRandom(csvLines);
        Plane[] sortedPlanes = display.sort();
        System.out.println(sortedPlanes[0]);
        System.out.println(sortedPlanes[1]);
        System.out.println(sortedPlanes[2]);
        System.out.println(sortedPlanes[3]);
        System.out.println(sortedPlanes[4]);
    }

    @Test
    void eightPlanesAllDifferentPartiallySorted() {
        String[] scheduleLines = {"ADZ8264,2:03", "XYJ3442,8:44", "FGI4927,15:35", "RPT2964,18:12"
                , "SJK2217,18:46"};
        String[] extraLines = {"FOW9278,5:29", "RBQ2847,22:50", "GNW2650,10:22"};
        DisplayPartiallySorted display = new DisplayPartiallySorted(scheduleLines, extraLines);
        Plane[] sortedPlanes = display.sort();
        System.out.println(sortedPlanes[0]);
        System.out.println(sortedPlanes[1]);
        System.out.println(sortedPlanes[2]);
        System.out.println(sortedPlanes[3]);
        System.out.println(sortedPlanes[4]);
        System.out.println(sortedPlanes[5]);
        System.out.println(sortedPlanes[6]);
        System.out.println(sortedPlanes[7]);
    }

    @Test
    void eightPlanesDuplicatesPartiallySorted() {
        String[] scheduleLines = {"ADZ8264,5:29", "XYJ3442,8:44", "FGI4927,15:35", "RPT2964,18:12"
                , "SJK2217,18:46"};
        String[] extraLines = {"ADQ9278,5:29", "RBQ2847,22:50", "GNW2650,8:44"};
        DisplayPartiallySorted display = new DisplayPartiallySorted(scheduleLines, extraLines);
        Plane[] sortedPlanes = display.sort();
        System.out.println(sortedPlanes[0]);
        System.out.println(sortedPlanes[1]);
        System.out.println(sortedPlanes[2]);
        System.out.println(sortedPlanes[3]);
        System.out.println(sortedPlanes[4]);
        System.out.println(sortedPlanes[5]);
        System.out.println(sortedPlanes[6]);
        System.out.println(sortedPlanes[7]);
    }
}
