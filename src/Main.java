import java.util.Scanner;

/**
 * Starts the Suitcase Check console application and processes user commands.
 * Displays the available commands and continues accepting input until the
 * user enters "quit" or the input ends.
 */
public class Main
{
    /**
     * Runs the console application.
     *
     * @param args
     *            command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Command command = new Command();
        System.out.println("Suitcase Check:\ntrip destination days, \n"
            + "pack item, \n"
            + "unpack item, \n"
            + "list, \n"
            + "check, \n"
            + "quit ");
        while (scanner.hasNextLine())
        {
            String input = scanner.nextLine();
            if (input.trim().equalsIgnoreCase("quit"))
            {
                break;
            }
            command.processCommand(input);
        }
        scanner.close();
        System.out.println("Goodbye.");
    }
}
