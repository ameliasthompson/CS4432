import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Project2 {
    public static void main(String[] args) {

        Pattern equalSelect = Pattern.compile("SELECT \\* FROM Project2Dataset WHERE RandomV = \\d+\\s*", Pattern.CASE_INSENSITIVE);
        Pattern inequalSelect = Pattern.compile("SELECT \\* FROM Project2Dataset WHERE RandomV != \\d+\\s*", Pattern.CASE_INSENSITIVE);
        Pattern rangeSelect = Pattern.compile("SELECT \\* FROM Project2Dataset WHERE RandomV > \\d+ AND RandomV < \\d+\\s*", Pattern.CASE_INSENSITIVE);
        Pattern buildIndexes = Pattern.compile("CREATE INDEX ON Project2Dataset \\(RandomV\\)\\s*", Pattern.CASE_INSENSITIVE);
        Pattern exit = Pattern.compile("exit\\s*", Pattern.CASE_INSENSITIVE);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String input = in.nextLine();

            // First we figure out what type of command it is.
            if (buildIndexes.matcher(input).matches()) {
                IndexManager.buildIndices();

            } else if (equalSelect.matcher(input).matches()) {
                // Equal select:
                Query.selectEquals(parseValue(input, 1));

            } else if (inequalSelect.matcher(input).matches()) {
                // Inequal select:
                Query.selectInequal(parseValue(input, 1));

            } else if (rangeSelect.matcher(input).matches()) {
                // Range select:
                Query.selectRange(parseValue(input, 1), parseValue(input, 2));

            } else if (exit.matcher(input).matches()) {
                break;
            
            } else {
                System.out.println("Invalid command!");
                System.out.println("");
            }
        }

        in.close();
    }

    /**
     * Given a string formatted like the commands specified by the assignment,
     * find and return the value with the specified position in the command (first
     * value, second value, etc). If an error occurs, -1 is returned.
     * @param input input string
     * @param n which value
     * @return the requested value or -1
     */
    private static int parseValue(String input, int n) {
        Pattern valueSelect = Pattern.compile(" [0-9]+"); // Select the value number
        Matcher valueMatcher = valueSelect.matcher(input);

        // Skip values until right before the one we want
        for(int i = 0; i < n - 1; i ++) { valueMatcher.find(); }

        if (valueMatcher.find()) {
            int v;
            try {
                v = Integer.parseInt(valueMatcher.group().substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Unable to parse command value!");
                e.printStackTrace();
                return -1;
            }

            return v;

        } else {
            System.out.println("Unable to parse command value!");
            return -1;
        }
    }
}