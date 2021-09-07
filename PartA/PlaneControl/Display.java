package PlaneControl;

import PlaneControl.Plane;
import PlaneControl.*;


class SortPlanes {
    // Lexicographically sort plane numbers if matching departure times
    public Plane[] sortPlaneNumbers(Plane[] data) {
        Plane[] planes = new Plane[1];
        for (int i = 1; i < data.length; i++) {
            if (data[i - 1].getTime().compareTo(data[i].getTime()) == 0) {
                if (data[i - 1].compareTo(data[i]) > 0) {
                    planes[0] = data[i - 1];
                    data[i - 1] = data[i];
                    data[i] = planes[0];
                }
            }
        }
        return data;
    }


    // Implementation of count sort for every digit
    public void countingSort(Plane[] data, int exp) {
        Plane[] countSorted = new Plane[data.length];
        Integer[] count = {0, 0 ,0 ,0, 0, 0, 0, 0, 0, 0};
        int i;

        for (i = 0; i < data.length; i++) {
            count[(getDepartureTimeUsable(data[i]) / exp) % 10]++;
        }

        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (i = data.length - 1; i >= 0; i--) {
            countSorted[count[(getDepartureTimeUsable(data[i]) / exp) % 10] - 1] = data[i];
            count[(getDepartureTimeUsable(data[i]) / exp) % 10]--;
        }

        for (i = 0; i < data.length; i++)
            data[i] = countSorted[i];
    }


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


    // Get's max departure time from data
    public int getMaxValue(Plane[] data) {
        int maxDepartureTime = getDepartureTimeUsable(data[0]);
        for (int i = 1; i < data.length; i++) {
            if (getDepartureTimeUsable(data[i]) > maxDepartureTime) {
                maxDepartureTime = getDepartureTimeUsable(data[i]);
            }
        }
        return maxDepartureTime;
    }

    // Radix sort using other sorts
    public Plane[] radixSort(Plane[] data) {
        // Sorting departure times using radix sort
        int max = getMaxValue(data);

        // Radix sort loop which does a count sort for each digit for most efficient and accurate
        // sorting
        for (int exponential = 1; max / exponential > 0; exponential *= 10) {
            countingSort(data, exponential);
        }

        return sortPlaneNumbers(data);
    }
}

class DisplayRandom extends DisplayRandomBase {

    public DisplayRandom(String[] csvLines) {
        super(csvLines);
    }

    /* Implement all the necessary methods here */
    @Override
    public Plane[] sort() {
        Plane[] data = getData();
        SortPlanes planeSort = new SortPlanes();
        return planeSort.radixSort(data);
    }

}

class DisplayPartiallySorted extends DisplayPartiallySortedBase {

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    /* Implement all the necessary methods here */
    @Override
    public Plane[] sort() {
        // Use insertion sort here instead
        SortPlanes planeSort = new SortPlanes();
        Plane[] extraPlanes = getExtraPlanes();
        Plane[] currentSchedule = getSchedule();
        Plane[] newUnsortedSchedule = new Plane[extraPlanes.length + currentSchedule.length];
        System.arraycopy(currentSchedule, 0, newUnsortedSchedule, 0, currentSchedule.length);
        System.arraycopy(extraPlanes, 0, newUnsortedSchedule, currentSchedule.length,
                extraPlanes.length);
        return planeSort.radixSort(newUnsortedSchedule);
    }
}
