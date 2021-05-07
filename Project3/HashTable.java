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
     * Build a HashTable of the given relation
     * @return
     */
    public HashTable(String relation) {
        buckets = new Bucket[NUM_BUCKETS];
        for (int i = 0; i < NUM_BUCKETS; i++) {
            buckets[i] = new Bucket();
        }

        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            buckets[hash(DBUtil.getRandomV(i, relation))].addRecord(DBUtil.getRecordText(i, relation));
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
}
