package PlaneControl;

import PlaneControl.Plane;
import PlaneControl.*;


class DisplayRandom extends DisplayRandomBase {

    public DisplayRandom(String[] csvLines) {
        super(csvLines);
    }

    /* Implement all the necessary methods here */

    // Implementation of count sort for every digit
    private void countingSort(Plane[] data, int lengthData, int exp) {
        Plane[] countSorted = new Plane[data.length];
        Integer[] count = {0, 0 ,0 ,0, 0, 0, 0, 0, 0, 0};
        int i;

        for (i = 0; i < lengthData; i++) {
            count[(getDepartureTimeUsable(data[i]) / exp) % 10]++;
        }

        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (i = lengthData - 1; i >= 0; i--) {
            countSorted[count[(getDepartureTimeUsable(data[i]) / exp) % 10] - 1] = data[i];
            count[(getDepartureTimeUsable(data[i]) / exp) % 10]--;
        }

        for (i = 0; i < lengthData; i++)
            data[i] = countSorted[i];
    }

    // Converts digital clock formatted time to number to be used in sorting
    private int getDepartureTimeUsable(Plane plane) {
        String time = plane.getTime();
        String timeFormatted = time.replaceAll(":", "");
        Character firstNumber = time.charAt(0);

        if (firstNumber.equals('0')) {
            timeFormatted = timeFormatted.substring(1);
        }

        return Integer.parseInt(timeFormatted);
    }

    // Get's max departure time from data
    private int getMaxValue(Plane[] data) {
        int maxDepartureTime = getDepartureTimeUsable(data[0]);
        for (int i = 1; i < data.length; i++) {
            if (getDepartureTimeUsable(data[i]) > maxDepartureTime) {
                maxDepartureTime = getDepartureTimeUsable(data[i]);
            }
        }
        return maxDepartureTime;
    }


    @Override
    public Plane[] sort() {
        Plane[] data = getData();

        // Sorting departure times using radix sort
        int max = getMaxValue(data);

        // Radix sort loop which does a count sort for each digit for most efficient and accurate
        // sorting
        for (int exponential = 1; max / exponential > 0; exponential *= 10) {
            countingSort(data, exponential, data.length);
        }

        // Sorting plane numbers lexicographically
        for (int i = 1; i < data.length; i++) {
            if (data[i - 1].compareTo(data[i]) < 0) {
                data[i - 1] = data[i];
            }
        }
        setData(data);
        return data;
    }

}

class DisplayPartiallySorted extends DisplayPartiallySortedBase {

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    @Override
    Plane[] sort() {
        // Potentially could use a priority queue
        return new Plane[0];
    }

    /* Implement all the necessary methods here */
}
