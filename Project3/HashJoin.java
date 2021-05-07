import java.util.ArrayList;

public class HashJoin {
    private static final int NUM_BUCKETS = 500;

    /**
     * A class that represents a hash bucket.
     */
    private static class Bucket {
        private ArrayList<String> records = new ArrayList<String>();

        public ArrayList<String> getRecords() {
            // This returns a reference when ideally we would return a copy
            // of the private variable so that it can't be modified.
            return records;
        }

        public void addRecord(String rec) {
            records.add(rec);
        }
    }

    /**
     * Perform a hash-based natural join between A and B.
     * @return number of joined records
     */
    public static void join() {
        // We're not using the read counter in this project, but this also
        // resets the cache.
        DBUtil.getAndResetReadCounter();

        Bucket[] buckets = buildHashTable();

        // Iterate over all of relation B
        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            String rec = DBUtil.getRecordText(i, "B");
            int val = DBUtil.getRandomV(i, "B");
            for (String j : buckets[hash(val)].getRecords()) {
                // We don't actually need to compare here because the domain
                // is only 500 in size and there are 500 buckets so we know
                // that every RandomV in a given bucket is the same, but we're
                // going to compare anyways because it more closely adheres to
                // the simulation we're going for.
                if (val == Record.getRandomV(j)) {
                    System.out.println(Record.getCol1(j) + ", "
                        + Record.getCol2(j) + ", "
                        + Record.getCol1(rec) + ", "
                        + Record.getCol2(rec));
                }
            }
        }
    }

    /**
     * Build a hash table of relation A.
     * @return an array of Buckets in the hash table
     */
    private static Bucket[] buildHashTable() {
        Bucket[] buckets = new Bucket[NUM_BUCKETS];
        for (int i = 0; i < NUM_BUCKETS; i++) {
            buckets[i] = new Bucket();
        }

        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            buckets[hash(DBUtil.getRandomV(i, "A"))].addRecord(DBUtil.getRecordText(i, "A"));
        }

        return buckets;
    }

    /**
     * Hash a value using the modulo operator.
     * @param v value
     * @return hashed value
     */
    private static int hash(int v) {
        return v % NUM_BUCKETS;
    }
}
