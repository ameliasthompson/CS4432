import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Project3 {
    public static void main(String args[]) {
        Pattern hashJoin = Pattern.compile("SELECT A.Col1, A.Col2, BCol1, BCol2 FROM A, B WHERE A.RandomV = B.RandomV\\s*", Pattern.CASE_INSENSITIVE);
        Pattern loopJoin = Pattern.compile("SELECT count\\(\\*\\) FROM A, B WHERE A.RandomV > B.RandomV\\s*", Pattern.CASE_INSENSITIVE);
        Pattern hashAgg = Pattern.compile("SELECT Col2, ((SUM\\(RandomV\\), AVG\\(RandomV\\))|(AVG\\(RandomV\\), SUM\\(RandomV\\))|(SUM\\(RandomV\\))|(AVG\\(RandomV\\))) FROM [AB] GROUP BY Col2\\s*", Pattern.CASE_INSENSITIVE);
        Pattern exit = Pattern.compile("exit\\s*", Pattern.CASE_INSENSITIVE);

        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.print(">");
            String input = in.nextLine();

            long start = new Date().getTime(); // Start the timer

            // Identify what command it is
            if (hashJoin.matcher(input).matches()) {
                HashJoin.join();

            } else if (loopJoin.matcher(input).matches()) {
                LoopJoin.join();

            } else if (hashAgg.matcher(input).matches()) {
                HashAggregation.aggregate(
                    ((input.indexOf(" A ") != -1) ? "A" : "B"),
                    ((input.indexOf("SUM")) != -1),
                    ((input.indexOf("AVG")) != -1));

            } else if (exit.matcher(input).matches()) {
                break;

            } else {
                System.out.println("Invalid command.");
            }

            System.out.println("Request took " + (new Date().getTime() - start) + "ms to complete");
        }

        in.close();
    }
}