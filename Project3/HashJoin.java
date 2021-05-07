public class HashJoin {
    /**
     * Perform a hash-based natural join between A and B.
     * @return number of joined records
     */
    public static void join() {
        // We're not using the read counter in this project, but this also
        // resets the cache.
        DBUtil.getAndResetReadCounter();

        HashTable.Bucket[] buckets = new HashTable("A").getBuckets();

        // Iterate over all of relation B
        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            String rec = DBUtil.getRecordText(i, "B");
            int val = DBUtil.getRandomV(i, "B");
            for (String j : buckets[HashTable.hash(val)].getRecords()) {
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
}
