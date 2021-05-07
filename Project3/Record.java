public class Record {
    public static final int COL1_OFFSET = 12;
    public static final int COL2_OFFSET = 21;
    public static final int RANDOM_V_OFFSET = 33;
    public static final int COL1_SIZE = 7;
    public static final int COL2_SIZE = 10;
    public static final int RANDOM_V_SIZE = 4;

    /**
     * Get the string for column 1 of a record.
     * @param rec record string
     * @return column 1 string or null on failure
     */
    public static String getCol1(String rec) {
        try {
            return rec.substring(COL1_OFFSET, COL1_OFFSET + COL1_SIZE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unable to get Col1 from record.");
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Unable to get Col1 from record.");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the string for column 2 of a record.
     * @param rec record string
     * @return column 2 string or null on failure
     */
    public static String getCol2(String rec) {
        try {
            return rec.substring(COL2_OFFSET, COL2_OFFSET + COL2_SIZE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unable to get Col2 from record.");
            e.printStackTrace();
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Unable to get Col2 from record.");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Get the int representation of RandomV from a record.
     * @param rec record string
     * @return RandomV or -1 on failure
     */
    public static int getRandomV(String rec) {
        try {
            return Integer.parseInt(rec.substring(RANDOM_V_OFFSET, RANDOM_V_OFFSET + RANDOM_V_SIZE));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unable to get RandomV from record.");
            e.printStackTrace();
            return -1;
        } catch (NumberFormatException e) {
            System.out.println("Unable to get RandomV from record.");
            e.printStackTrace();
            return -1;
        }
    }
}
