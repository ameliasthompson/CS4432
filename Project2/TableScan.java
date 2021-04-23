import java.util.ArrayList;

public class TableScan {

    /**
     * Use a full table scan to find all records with RandomV v
     * @param v value
     * @return
     */
    public static int[] findRecords(int v) {
        if (v < 1 || v > 5000) {
            System.out.println("Error: Attempted to find record with invalid RandomV value.");
            return null;
        }

        // We scan the entire table for all the records that match
        ArrayList<Integer> records = new ArrayList<Integer>();
        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
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
    }

    /**
     * Use a full table scan to find all records between l and u (exclusive)
     * @param l lower bound (exclusive)
     * @param u upper bound (exclusive)
     * @return
     */
    public static int[] findRecordsRange(int l, int u) {
        if (l >= u) {
            System.out.println("Error: Attempted to find record with invalid RandomV range.");
            return null;
        }

        ArrayList<Integer> records = new ArrayList<Integer>();
        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            int value = DBUtil.getRandomV(i);
            if (value > l && value < u) {
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
    }

    /**
     * Use a full table scan to find records where RandomV does not match the
     * provided value.
     * @param v value
     * @return
     */
    public static int[] findRecordsInequal(int v) {
        if (v < 1 || v > 5000) {
            System.out.println("Error: Attempted to find record with invalid RandomV value.");
            return null;
        }

        // We scan the entire table for all the records that match
        ArrayList<Integer> records = new ArrayList<Integer>();
        for (int i = 1; i <= DBUtil.NUM_RECORDS; i++) {
            int value = DBUtil.getRandomV(i);
            if (value != v) {
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
    }
}
