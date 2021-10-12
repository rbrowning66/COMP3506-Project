//package PlaneControl;

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
    public int getMaxPlaneDepartureTime(Plane[] data) {
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
        int maxDepartureTime = getMaxPlaneDepartureTime(data);

        int digitNo = 1;
        // Radix sort loop which does a count sort for each digit for most efficient and accurate
        // sorting
        for (int digit = 1; maxDepartureTime / digit > 0; digit *= 10) {
            countingSort(data, digit);
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
    SortPlanes planeSort = new SortPlanes();

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    /* Implement all the necessary methods here */
    private Plane[] sortExtraPlanesIntoCurrentSchedule(int scheduleLength,
                                                       Plane[] unsortedSchedule) {
        int planeCounter, previousPlaneCounter;
        for (planeCounter = 1; planeCounter < scheduleLength; planeCounter++) {
            Plane previousPlane = unsortedSchedule[planeCounter - 1];
            Plane curPlane = unsortedSchedule[planeCounter];
            previousPlaneCounter = planeCounter - 1;


            while (previousPlaneCounter > -1 &&
                    planeSort.getDepartureTimeUsable(unsortedSchedule[previousPlaneCounter])
                            >= planeSort.getDepartureTimeUsable(curPlane)) {
                if (planeSort.getDepartureTimeUsable(previousPlane) == planeSort.getDepartureTimeUsable(curPlane)) {
                    if (previousPlane.compareTo(curPlane) > 0) {
                        unsortedSchedule[previousPlaneCounter + 1] = unsortedSchedule[previousPlaneCounter];
                        previousPlaneCounter = previousPlaneCounter - 1;
                    }
                } else {
                    unsortedSchedule[previousPlaneCounter + 1] = unsortedSchedule[previousPlaneCounter];
                    previousPlaneCounter = previousPlaneCounter - 1;
                }
            }
            unsortedSchedule[previousPlaneCounter + 1] = curPlane;
        }

        return unsortedSchedule;
    }

    @Override
    public Plane[] sort() {
        // Use insertion sort here instead
        Plane[] extraPlanes = getExtraPlanes();
        Plane[] currentSchedule = getSchedule();
        Plane[] newUnsortedSchedule = new Plane[extraPlanes.length + currentSchedule.length];
        System.arraycopy(currentSchedule, 0, newUnsortedSchedule, 0, currentSchedule.length);
        System.arraycopy(extraPlanes, 0, newUnsortedSchedule, currentSchedule.length,
                extraPlanes.length);

        return sortExtraPlanesIntoCurrentSchedule(newUnsortedSchedule.length,
                newUnsortedSchedule);
    }
}
