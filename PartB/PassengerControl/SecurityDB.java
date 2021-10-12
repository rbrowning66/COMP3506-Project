package PassengerControl;

import java.util.stream.IntStream;

public class SecurityDB extends SecurityDBBase {
    /* Implement all the necessary methods here */
    Passenger[] hashTablePassengers;
    private int table_size;
    public int numPassengers;

    /**
     * Creates an empty hashtable and a variable to count non-empty elements.
     *
     * @param numPlanes             number of planes per day
     * @param numPassengersPerPlane number of passengers per plane
     */
    public SecurityDB(int numPlanes, int numPassengersPerPlane) {
        super(numPlanes, numPassengersPerPlane);
        int totalPassengers = numPlanes * numPassengersPerPlane;
        this.table_size = getNextPrimeNumber(totalPassengers);
        this.hashTablePassengers = new Passenger[this.table_size];
        this.numPassengers = 0;
    }

    private int getNextPrimeNumber(int number) {
        number++;

        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                number++;
                i = 2;
            }
        }
        return number;
    }

    @Override
    public int calculateHashCode(String key) {
        //Using sum of character to ascii values using equation given in spec
        char[] charArray = key.toCharArray();
        int[] asciiCharArray = new int[charArray.length];
        int counter = 0;

        // Loop through char array and use given hash function to calculate hashcode
        for (char c : charArray) {
            asciiCharArray[counter] = 1 + IntStream.of(asciiCharArray).sum() + (int) c;
            counter++;
        }

        int hash = IntStream.of(asciiCharArray).sum();

        // Apply compression function h(y) = y mod N, y = hash, N = size of hash table (prime
        // number)
        hash = hash % table_size;
        return hash;
    }

    @Override
    public int size() {
        return table_size;
    }

    @Override
    public String get(String passportId) {
        int hashCode = calculateHashCode(passportId);

        while (hashTablePassengers[hashCode] != null) {
            if (hashTablePassengers[hashCode].passportID.equals(passportId)) {
                return hashTablePassengers[hashCode].passengerName;
            }
            hashCode = (1 + hashCode) % table_size;
        }
        return null;
    }

    @Override
    public boolean remove(String passportId) {
        if (!contains(passportId)) {
            return false;
        }

        int hashCode = calculateHashCode(passportId);

        while(!hashTablePassengers[hashCode].passportID.equals(passportId)) {
            hashCode = (1 + hashCode) % table_size;
        }

        hashTablePassengers[hashCode] = null;

        // Rehash keys
        table_size--;
        Passenger[] temp = new Passenger[table_size];
        int count = 0;

        while (hashTablePassengers[count] != null) {
            temp[calculateHashCode(hashTablePassengers[count].passportID)] =
                    new Passenger(hashTablePassengers[count].passportID,
                            hashTablePassengers[count].passengerName);
            count++;
        }

        hashTablePassengers = temp;
        return true;
    }

    @Override
    public boolean addPassenger(String name, String passportId) {
        int hashCode = calculateHashCode(passportId);

        while (hashTablePassengers[hashCode] != null) {
            if (hashTablePassengers[hashCode].passportID.equals(passportId)) {
                return false;
            }
            hashCode = (1 + hashCode) % table_size;
        }

        // Test what exception is thrown when not enough space (change table_size to max_size)
        // and resize hash table
        try {
            hashTablePassengers[hashCode] = new Passenger(passportId, name);
            numPassengers++;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            table_size = SecurityDB.MAX_CAPACITY;
            Passenger[] temp = new Passenger[table_size];
            System.arraycopy(hashTablePassengers, 0, temp, 0, numPassengers);
            hashTablePassengers = temp;
            hashTablePassengers[hashCode] = new Passenger(passportId, name);
            numPassengers++;
            return true;
        }
    }

    @Override
    public int count() {
        return numPassengers;
    }

    @Override
    public int getIndex(String passportId) {
        return calculateHashCode(passportId);
    }

    /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        REMOVE THE MAIN FUNCTION BEFORE SUBMITTING TO THE AUTOGRADER
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        The following main function is provided for simple debugging only

        Note: to enable assertions, you need to add the "-ea" flag to the
        VM options of PassengerControl.SecurityDB's run configuration
     */
    public static void main(String[] args) {
        SecurityDB db = new SecurityDB(3, 2);

        // add
        db.addPassenger("Rob Bekker", "Asb23f");
        db.addPassenger("Kira Adams", "MKSD23");
        db.addPassenger("Kira Adams", "MKSD24");
        assert db.contains("Asb23f");

        // count
        assert db.count() == 3;

        // del
//        db.remove("MKSD23");
//        assert !db.contains("MKSD23");
//        assert db.contains("Asb23f");

        // hashcodes
        assert db.calculateHashCode("Asb23f") == 1717;

        // suspicious
        db = new SecurityDB(3, 2);
        db.addPassenger("Rob Bekker", "Asb23f");
        db.addPassenger("Robert Bekker", "Asb23f");
        // Should print a warning to stderr
    }


}

/* Add any additional helper classes here */
/** Passenger in the hash table security database*/
class Passenger {
    public String passportID;
    public String passengerName;

    public Passenger(String passportID, String passengerName) {
        this.passportID = passportID;
        this.passengerName = passengerName;
    }
}