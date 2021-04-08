public class Parser {

    public static enum cmds {
        GET,
        SET,
        PIN,
        UNPIN,
        EXIT,
        ERROR
    }

    /**
     * Isolate the command from the input string and return a enumerator
     * representation of it.
     * @param s input string
     * @return command
     */
    public static cmds getCmd(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length >= 1) {
            switch(tokens[0].toLowerCase()) {
            case "get":
                return cmds.GET;
            case "set":
                return cmds.SET;
            case "pin":
                return cmds.PIN;
            case "unpin":
                return cmds.UNPIN;
            case "exit":
                return cmds.EXIT;
            default:
                return cmds.ERROR;
            }
        }

        return cmds.ERROR;
    }

    /**
     * Isolate the id from the input string and return it. Note that this
     * does not care if it's a record id or block id. Returns if the command
     * doesn't have an id for some reason.
     * @param s input string
     * @return id
     */
    public static int getID(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length >= 2) {
            return Integer.parseInt(tokens[1]);
        }

        return -1;
    }

    /**
     * Isolate the content, if any, from the input string and return it. Returns
     * null if there is no content.
     * @param s
     * @return
     */
    public static String getContent(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length >= 3) {
            // Reconstruct the content from tokens in case it got split up.
            String tmp = tokens[2];
            for (int i = 3; i < tokens.length; i++) {
                tmp = tmp + " " + tokens[i];
            }

            return tmp;
        }

        return null;
    }
}
