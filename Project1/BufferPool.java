
public class BufferPool {
    private final Frame[] frames;
    private final int numframes;

    /**
     * Create and initialize a new buffer with n frames.
     * @param n number of frames
     */
    BufferPool(int n) {
        frames = new Frame[n];
        numframes = n;
        for (int i = 0; i < n; i++) {
            frames[i] = new Frame(i+1); // Everything external is 1 indexed so
                                        // frame number might as well be.
        }
    }

    /**
     * Get a record (if it exists) using the 1 indexed system
     * @param id 1 indexed record number
     */
    public void get(int id) {
        id--; // Convert to 0 index
        if (id >= 0) {
            Frame f = getBlock((id / Frame.NUM_RECORDS_FILE) + 1);

            if (f != null) {
                byte[] record = f.getRecord(id % Frame.NUM_RECORDS_FILE);
                System.out.println("Record in frame #" + f.frameNumber);
                System.out.println(new String(record));
            }
        }
    }

    /**
     * Set a record (if it exists) using the 1 indexed system
     * @param id 1 indexted record number
     * @param record the new record
     */
    public void set(int id, byte[] record) {
        id--; // Convert to 0 index
        if (id >= 0) {
            Frame f = getBlock((id / Frame.NUM_RECORDS_FILE) + 1);

            if (f != null) {
                boolean suc = f.writeRecord(id % Frame.NUM_RECORDS_FILE, record);
                if (suc) {
                    System.out.println("Write successful!");
                } else {
                    System.out.println("Write failed!");
                }

                System.out.println("Record in frame #" + f.frameNumber);
            }
        }
    }

    /**
     * Pin a block
     * @param bid block id
     */
    public void pin(int bid) {
        Frame f = getBlock(bid);
        
        if (f != null) {
            if (f.pinned) {
                System.out.println("Block #" + bid + " already pinned!");
            }

            f.pinned = true;
            System.out.println("Block #" + bid + " pinned in frame #" + f.frameNumber);
        }
    }

    /**
     * Unpin a block
     * @param bid block id
     */
    public void unpin(int bid) {
        // We can't use getblock function because that'll load it if it's not there
        Frame f = null;
        for (Frame i : frames) {
            if (i.getBlockID() == bid) f = i;
        }
        
        if (f != null) {
            if (!f.pinned) {
                System.out.println("Block #" + bid + " already unpinned!");
            }

            f.pinned = false;
            System.out.println("Block #" + bid + " unpinned in frame #" + f.frameNumber);
        
        } else {
            System.out.println("Can not unpin Block #" + bid
                + " because it's not in memory!");
        }
    }

    /**
     * Write all dirty frames to disk.
     */
    public void close() {
        for (Frame i : frames) {
            i.close();
        }
    }

    /**
     * Find an empty frame if there is one.
     * @return the empty frame or null
     */
    private Frame getEmpty() {
        for (Frame i : frames) {
            if (i.getBlockID() == -1) return i;
        }

        return null;
    }

    private int nextReplace = 0;
    /**
     * Attempt to replace a frame in a round robin style, or fail to do so.
     * @return the newly empty frame or null
     */
    private Frame replaceFrame() {
        int initialReplace = nextReplace;
        
        // Loop until we either find a frame we can push out or come full circle
        do {
            if (frames[nextReplace].pinned == false) {
                Frame tmp = frames[nextReplace];
                tmp.close();

                nextReplace++;
                if (nextReplace >= numframes) { nextReplace = 0; }

                return tmp;
            
            } else {
                nextReplace++;
                if (nextReplace >= numframes) { nextReplace = 0; }
            }
        } while (initialReplace != nextReplace);

        // If we're here, there wasn't a frame that could be removed.
        return null;
    }

    /**
     * Get a block from the buffer using the zero index format.
     * @param id zero indexed block id
     * @return the containing frame or null
     */
    private Frame getBlock(int id) {
        // First check to see if the block is in the buffer:
        for (Frame i : frames) {
            if (i.getBlockID() == id) return i;
        }

        // If it's not, we need an empty frame, so we're going to search for
        // an existing one.
        Frame empty = getEmpty();
        if (empty != null) {
            // If there is one, great! We're going to load the block now
            empty.open(id);
            return empty;
        }

        // Otherwise we're going to attempt to create an empty frame
        empty = replaceFrame();
        if (empty != null) {
           empty.open(id);
           return empty;
            
        } else {
            // If it fails we error
            System.out.println("The corresponding block #"
                + Integer.toString(id)
                + " cannot be accessed from disk because the memory buffers are full");
            
            return null;
        }
    }
}