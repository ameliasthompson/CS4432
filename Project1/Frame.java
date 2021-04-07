import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Arrays;

public class Frame {
    public final static int RECORD_SIZE = 40;
    public final static int NUM_RECORDS_FILE = 100;
    public final static int FILE_SIZE = RECORD_SIZE * NUM_RECORDS_FILE;
    
    private byte[] content = new byte[FILE_SIZE];
    private int blockID = -1;
    private boolean dirty = false;
    
    public boolean pinned = false;  // We use both getters and setters here so
                                    // and nothing needs to be done when the
                                    // value is changed, so there is absolutely
                                    // no reason for it to not be public.

    public int getBlockID() { return blockID; }

    /**
     * Get a record from the content of the dataframe.
     * @param j record number
     * @return the jth record in the dataframe
     */
    public byte[] getRecord(int j) {
        if (blockID > 0 && j > 0 && j <= NUM_RECORDS_FILE) {
            return Arrays.copyOfRange(content, (j - 1) * RECORD_SIZE, j * RECORD_SIZE);
        
        } else {
            return null;
        }
    }

    /**
     * Write RECORD_SIZE bytes to the appropriate location in file.
     * @param j record number
     * @param record contents of record
     */
    public void writeRecord(int j, byte[]record) {
        try {
            if (blockID > 0 && j > 0 && j <= NUM_RECORDS_FILE) {
                System.arraycopy(record, 0, content, (j - 1) * RECORD_SIZE, RECORD_SIZE);
                dirty = true;
            }
        
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Record to be written is too small.");
            e.printStackTrace();
        }
    }

    /**
     * Read a new block into memory in this frame.
     * @param id block number
     */
    public void open(int id) {
        blockID = id;

        // If it's a valid blockID, then we need to load the content from disk.
        if (blockID > 0) {
            try {
                // We're jumping through a bunch of hoops here to make sure
                // that java does not automatically size the array. This is
                // to more closely match the fact that blocks are always the
                // same size.
                Path p = Paths.get("Dataset/F" + Integer.toString(blockID) + ".txt");
                byte[] tmp = Files.readAllBytes(p);
                System.arraycopy(tmp, 0, content, 0, FILE_SIZE);

            } catch (IOException e) {
                System.out.println("Error opening block file.");
                e.printStackTrace();

                blockID = -1;

            } catch (IndexOutOfBoundsException e) {
                System.out.println("File was not at least "
                        + Integer.toString(FILE_SIZE)
                        + "bytes long.");
                e.printStackTrace();

                blockID = -1;
            }
        }
    }

    /**
     * Write the frame to disk if it's dirty, and mark the frame as empty.
     */
    public void close() {
        if (blockID > 0) {
            blockID = -1;

            // If the frame is dirty, we need to write it back to disk
            if (dirty) {
                try {
                    Path p = Paths.get("Dataset/F" + Integer.toString(blockID) + ".txt");
                    Files.write(p, content);
                
                } catch (IOException e) {
                    System.out.println("Error writing block to disk.");
                    e.printStackTrace();
                }
            }
        }
    }
}
