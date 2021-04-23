import java.util.ArrayList;
import java.util.Arrays;

public class HashIndex {

    private static final int NUM_BUCKETS = 1000;

    private HashEntry[] entries = new HashEntry[NUM_BUCKETS];

    /**
     * A class that represents a single hash index entry with one or more values.
     */
    private class HashEntry {
        public final int key; //< The key for the hash entry.
        public final int[] rids; ///< The array of rids under the hash.

        /**
         * Construct a HashEntry given a key and a value
         * @param k key
         * @param v value
         */
        public HashEntry(int k, int i) {
            int[] tmp = {i};

            key = k;
            rids = tmp;
        }

        /**
         * Construct a HashEntry given another HashEntry and an additional value
         * to append to the values in the HashEntry.
         * @param e HashEntry
         * @param v value
         */
        public HashEntry(HashEntry e, int i) {
            // Append v to e.rids:
            int[] tmp = new int[e.rids.length + 1];
            System.arraycopy(e.rids, 0, tmp, 0, e.rids.length);
            tmp[e.rids.length] = i;

            Arrays.sort(tmp);

            key = e.key;
            rids = tmp;
        }
    }

    /**
     * Add a new entry to the index
     * @param v RandomV
     * @param i record id
     */
    public void addEntry(int v, int i) {
        int key = hash(v);

        // If there is no hash entry in that bucket yet, we need to make one
        if (entries[key] == null) {
            entries[key] = new HashEntry(key, i);

        // Otherwise we need to append to the existing one
        } else {
            entries[key] = new HashEntry(entries[key], i);
        }
    }

    /**
     * Generate a hash for a String value.
     * @param v value
     * @return hashed value
     */
    private static int hash(int v) {
        if (v < 0) {
            System.out.println("Warning: Hashing value less than zero.");
        }

        return v % NUM_BUCKETS;
    }

    /**
     * Find the records corrisponding to a value (if any)
     * @param v value
     * @return array of rids or null
     */
    public int[] findRecords(int v) {
        int h = hash(v);
        if (h < 0 || h >= NUM_BUCKETS) {
            System.out.println("Error: Attempted to find record with invalid hash.");
            return null;
        }

        HashEntry entry = entries[h];

        // If the bucket actually exists, we check it for records
        if (entry != null) {
            // We get all the records that match
            ArrayList<Integer> records = new ArrayList<Integer>();
            for (int i : entry.rids) {
                int value = DBUtil.getRandomV(i);
                if (value == v) {
                    records.add(i);
                    System.out.println(DBUtil.getRecordText(i));
                }
            }

            if (records.size() > 0) {
                // Because we can't use int in array lists, we have to jump through
                // some hoops to return an int[] here instead of just using toArray()
                int[] tmp = new int[records.size()];
                for (int i = 0; i < records.size(); i++) {
                    tmp[i] = records.get(i);
                }

                return tmp;
            } else {
                // Return null if there are no records
                return null;
            }

        // If there is no bucket, return null
        } else {
            return null;
        }
    }
}
