import java.nio.file.Paths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DBUtil {

    public static final int NUM_RECORDS_IN_BLOCK = 100;
    public static final int NUM_BLOCKS = 99;
    public static final int NUM_RECORDS = NUM_BLOCKS * NUM_RECORDS_IN_BLOCK;
    public static final int RECORD_SIZE = 40;
    public static final int RANDOM_V_OFFSET = 33;
    public static final int RANDOM_V_SIZE = 4;

    /**
     * Get the RandomV value of a database record.
     * @param rid record id
     * @return RandomV value
     */
    public static int getRandomV(int rid) {
        // First figure out which block the record is in:
        rid--; // Record id is one indexed, but it's easier to find with zero index.
        if (rid < 0) { return -1; }
        int bid = (rid / NUM_RECORDS_IN_BLOCK) + 1; // Have to adjust because block is one indexed

        // Read the block file into memory:
        Path p = Paths.get("Project2Dataset/F" + Integer.toString(bid) + ".txt");
        byte[] file;
        try {
            file = Files.readAllBytes(p);
        } catch (IOException e) {
            System.out.println("Error opening block " + bid + " file.");
            e.printStackTrace();
            return -1;
        }

        // Isolate the record RandomV we're interested in:
        byte[] value = new byte[RANDOM_V_SIZE];
        try {
            int srcPos = (rid % NUM_RECORDS_IN_BLOCK) * RECORD_SIZE + RANDOM_V_OFFSET;
            System.arraycopy(file, srcPos, value, 0, RANDOM_V_SIZE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Record " + rid + " could not be copied from block " + bid + " file.");
            e.printStackTrace();
            return -1;
        }

        return Integer.parseInt(new String(value));
    }
}
