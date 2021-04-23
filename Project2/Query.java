import java.util.Date;

public class Query {

    /**
     * Select rows with RandomV equals to the provided value
     * @param v value
     */
    public static void selectEquals(int v) {
        DBUtil.getAndResetReadCounter(); // Make sure read counter is 0
        long start = new Date().getTime(); // Start timer

        HashIndex hIndex = IndexManager.getHashIndex();
        if (hIndex == null) {
            // Table scan
            TableScan.findRecords(v);

            long finish = new Date().getTime();

            System.out.println("Table scan was used.");
            System.out.println("Query took " + (finish - start) + " ms to complete.");
            System.out.println("Query read " + DBUtil.getAndResetReadCounter() + " blocks.");
            System.out.println("");

        } else {
            // Hash index used
            hIndex.findRecords(v);

            long finish = new Date().getTime();

            System.out.println("The hash index was used.");
            System.out.println("Query took " + (finish - start) + " ms to complete.");
            System.out.println("Query read " + DBUtil.getAndResetReadCounter() + " blocks.");
            System.out.println("");
        }
    }
}
