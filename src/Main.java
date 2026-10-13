import java.util.Scanner;

/** Starts the console application. */
public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Command command = new Command();
        System.out.println("Suitcase Check: trip destination days, pack item, unpack item, list, check, quit");
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
