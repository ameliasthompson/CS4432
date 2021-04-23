import java.util.ArrayList;
import java.util.Arrays;

// A lot of this class is copied from HashIndex and slightly modified because
// they ended up having similar implementations. In an ideal world I would have
// realized soon enough to do a base class and then extend it, but we're not
// living in that ideal world.

public class ArrayIndex {

    private ArrayEntry[] entries = new ArrayEntry[5000]; // Assignment specifies 5000 size

    /**
     * A class that represents a single array index entry with one associated rid.
     */
    private class ArrayEntry {
        public final int value;
        public final int rids[];

        /**
         * Construct an ArrayEntry given a value and a record id
         * @param v value
         * @param i record id
         */
        public ArrayEntry(int v, int i) {
            int[] tmp = {i};

            value = v;
            rids = tmp;
        }

        /**
         * Construct an ArrayEntry given another ArrayEntry and a record id to
         * append.
         * @param e ArrayEntry
         * @param i record id
         */
        public ArrayEntry(ArrayEntry e, int i) {
            // Append i to e.rids:
            int[] tmp = new int[e.rids.length + 1];
            System.arraycopy(e.rids, 0, tmp, 0, e.rids.length);
            tmp[e.rids.length] = i;

            Arrays.sort(tmp);

            value = e.value;
            rids = tmp;
        }
    }

    /**
     * Add a new entry to the index
     * @param v RandomV
     * @param i record id
     */
    public void addEntry(int v, int i) {
        ArrayEntry entry = entries[v - 1]; // RandomV starts at 1 not 0

        // If there is no array entry yet, we need to make one
        if (entry == null) {
            entries[v - 1] = new ArrayEntry(v, i);

        // Otherwise we need to append to the existing one
        } else {
            entries[v - 1] = new ArrayEntry(entry, i);
        }
    }

    /**
     * Find the records corrisponding to a value (if any)
     * @param v value
     * @return array of rids or null
     */
    public int[] findRecords(int v) {
        if (v < 1 || v > 5000) {
            System.out.println("Error: Attempted to find record with invalid RandomV value.");
            return null;
        }

        ArrayEntry entry = entries[v - 1]; // RandomV starts at 1, but our array starts at 0

        // If the array index entry actually exists, we check it for records
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

        // If there is no entry, return null
        } else {
            return null;
        }
    }
}
