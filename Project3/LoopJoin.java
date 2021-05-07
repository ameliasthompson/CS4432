import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoopJoin {

    /**
     * Count all qualifying records for join and print count.
     */
    public static void join() {
        int count = 0;

        for (int ab = 1; ab <= DBUtil.NUM_BLOCKS; ab++) {
            // Loop over blocks in A
            String aBlock = getFile(ab, "A");

            for (int ar = 0; ar < DBUtil.NUM_RECORDS_IN_BLOCK; ar++) {
                // Loop over records in the current block in A
                String aRecord = getRecord(aBlock, ar);

                for (int bb = 1; bb <= DBUtil.NUM_BLOCKS; bb++) {
                    // Loop over blocks in B
                    String bBlock = getFile(bb, "B");

                    for (int br = 0; br < DBUtil.NUM_RECORDS_IN_BLOCK; br++) {
                        // Loop over records in current block in B
                        String bRecord = getRecord(bBlock, br);
                        if (Record.getRandomV(aRecord) > Record.getRandomV(bRecord)) {
                            count++; // We're just counting them for this part of the project
                        }
                    }
                }
            }
        }

        System.out.println("There are " + count + " qualifying records.");
    }

    /**
     * Open a file and return all bytes in it as a string.
     * @param bid block ID
     * @param relation relation prefix
     * @return file string or null on failure
     */
    private static String getFile(int bid, String relation) {
        // Because we need to be more explicit with how we're managing files
        // in this part, we're not going to use DBUtil functions.
        Path p = Paths.get("Project3Dataset-"
                + relation + "/" + relation + Integer.toString(bid) + ".txt");
        byte[] file;

        try {
            file = Files.readAllBytes(p);

        } catch (IOException e) {
            System.out.println("Unable to open " + p.toString());
            e.printStackTrace();
            return null;
        }

        return new String(file);
    }

    /**
     * Extract a record from a block
     * @param block block string
     * @param offset number of records from the beginning
     * @return record string
     */
    private static String getRecord(String block, int offset) {
        try {
            return block.substring(offset * DBUtil.RECORD_SIZE, (offset + 1) * DBUtil.RECORD_SIZE);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.print("Unable to extract record with offset " + offset + " from block");
            e.printStackTrace();
            return null;
        }
    }
}
