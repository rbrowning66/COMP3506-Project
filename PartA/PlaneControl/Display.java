package PlaneControl;

class DisplayRandom extends DisplayRandomBase {

    public DisplayRandom(String[] csvLines) {
        super(csvLines);
    }

    /* Implement all the necessary methods here */
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


    private int getDepartureTimeUsable(Plane plane) {
        String time = plane.getTime();
        String timeFormatted = time.replaceAll(":", "");
        Character firstNumber = time.charAt(0);

        if (firstNumber.equals('0')) {
            timeFormatted = timeFormatted.substring(1);
        }

        return Integer.parseInt(timeFormatted);
    }

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
        Plane[] unsortedData = getData();
        int max = getMaxValue(unsortedData);

        for (int exponential = 1; max / exponential > 0; exponential *= 10) {
            countingSort(unsortedData, exponential, unsortedData.length);
        }

        return new Plane[0];
    }

}

class DisplayPartiallySorted extends DisplayPartiallySortedBase {

    public DisplayPartiallySorted(String[] scheduleLines, String[] extraLines) {
        super(scheduleLines, extraLines);
    }

    @Override
    Plane[] sort() {
        return new Plane[0];
    }

    /* Implement all the necessary methods here */
}
