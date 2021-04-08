import java.util.Scanner;

class Main {
    public static void main(String args[]) {
        
        if (args.length == 1) {
            // We're going to assume that the first argument is always a number
            BufferPool p = new BufferPool(Integer.parseInt(args[0]));

            // Input loop:
            Scanner in = new Scanner(System.in);
            outer: while (true) {
                String s = in.nextLine();
                switch(Parser.getCmd(s)) {
                case GET:
                    p.get(Parser.getID(s));
                    break;

                case SET:
                    p.set(Parser.getID(s), Parser.getContent(s).getBytes());
                    break;

                case PIN:
                    p.pin(Parser.getID(s));
                    break;

                case UNPIN:
                    p.unpin(Parser.getID(s));
                    break;

                case EXIT:
                    System.out.println("Exiting...");
                    break outer; // Not the best practice, but clean
                
                case ERROR:
                    System.out.println("Error reading command.");
                    break;
                }
            }

            p.close(); // Write any dirty frames to disk before exit!
            in.close(); 
        
        } else {
            System.out.println("Wrong number of arguments! Should just be number of frames!");
        }
    }
}