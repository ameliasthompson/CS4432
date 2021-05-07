import java.util.Hashtable;

public class HashAggregation {

    private static class Group {
        public final String col2;
        private int sum;
        private int num;

        public Group(String c) {
            col2 = c;
            sum = 0;
        }

        public void add(int n) {
            sum += n;
            num++;
        }

        public int getSum() {
            return sum;
        }

        public float getAvg() {
            return (float) sum / (float) num;
        }
    }

    /**
     * Aggregate the selected table and produce the selected aggregation functions.
     * @param relation relation prefix
     * @param sum if sum should be performed
     * @param avg if average should be performed
     */
    public static void aggregate(String relation, boolean sum, boolean avg) {
        // We're not using the read counter in this project, but this also
        // resets the cache.
        DBUtil.getAndResetReadCounter();

        // THIS IS A DEFAULT JAVA HASHTABLE
        Hashtable<String, Group> groups = new Hashtable<String, Group>();

        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            // Iterate over every record.
            String rec = DBUtil.getRecordText(i, relation);
            String col2 = Record.getCol2(rec);

            // Look for the group and create it if it doesn't exist
            if (groups.get(col2) == null) {
                groups.put(col2, new Group(col2));
            }

            // Increment sum in group
            groups.get(col2).add(Record.getRandomV(rec));
        }

        // Print all the groups
        for (Group g : groups.values()) {
            System.out.println(g.col2
                + (sum ? (", SUM:" + g.getSum()) : "")
                + (avg ? (", AVG:" + g.getAvg()) : ""));
        }

    }
}
