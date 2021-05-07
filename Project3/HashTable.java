import java.util.ArrayList;

public class HashTable {
    public static final int NUM_BUCKETS = 500;

    private Bucket[] buckets;

    public Bucket[] getBuckets() { return buckets; }

    /**
     * A class that represents a hash bucket.
     */
    public class Bucket {
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
     * Build a HashTable of a given relation on either RandomV or Col2
     * @param relation relation prefix
     * @param randomV true if use randomV false if Col2
     */
    public HashTable(String relation, boolean randomV) {
        buckets = new Bucket[NUM_BUCKETS];
        for (int i = 0; i < NUM_BUCKETS; i++) {
            buckets[i] = new Bucket();
        }

        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            buckets[(randomV) ? hash(DBUtil.getRandomV(i, relation))
                : hash(Record.getCol2(DBUtil.getRecordText(i, relation)))]
                    .addRecord(DBUtil.getRecordText(i, relation));
        }
    }

    /**
     * Hash a value using the modulo operator.
     * @param v value
     * @return hashed value
     */
    public static int hash(int v) {
        return v % NUM_BUCKETS;
    }

    /**
     * Hash a string using the default string hasher and modulo
     * @param v value
     * @return hashed value
     */
    public static int hash(String v) {
        return v.hashCode() % NUM_BUCKETS;
    }
}
