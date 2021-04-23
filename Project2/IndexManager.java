public class IndexManager {
    private static HashIndex hIndex = null;
    private static ArrayIndex aIndex = null;

    public static HashIndex getHashIndex() { return hIndex; }
    public static ArrayIndex getArrayIndex() { return aIndex; }

    public static void buildIndices() {
        hIndex = new HashIndex();
        aIndex = new ArrayIndex();

        // Process every record in the database.
        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            int value = DBUtil.getRandomV(i);
            hIndex.addEntry(value, i);
            aIndex.addEntry(value, i);
        }

        DBUtil.getAndResetReadCounter();

        System.out.println("The hash-based and array-based indexes are built successfully. Program is ready and waiting for user command.");
    }
}
