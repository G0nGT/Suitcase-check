package suitcase;
/** Parses commands and reports invalid input without stopping the program. */
public class Command
{
    private Trip currentTrip;
    private PackingManager packingManager;
    private PackingRules packingRules;

    public Command()
    {
        packingManager = new PackingManager();
        packingRules = new PackingRules();
    }

    public void processCommand(String command)
    {
        if (command == null || command.trim().length() == 0)
        {
            printHelp();
            return;
        }
        String input = command.trim();
        String[] parts = input.split("\\s+", 2);
        String action = parts[0].toLowerCase(java.util.Locale.ROOT);
        String argument = parts.length == 2 ? parts[1].trim().replaceAll("\\s+", " ") : "";
        try
        {
            if (action.equals("trip"))
            {
                int lastSpace = argument.lastIndexOf(' ');
                if (lastSpace < 0)
                {
                    System.out.println("Use: trip destination days");
                    return;
                }
                String city = argument.substring(0, lastSpace).trim();
                int days;
                try
                {
                    days = Integer.parseInt(argument.substring(lastSpace + 1));
                }
                catch (NumberFormatException exception)
                {
                    System.out.println("Days must be 1 to 30.");
                    return;
                }
                Trip nextTrip = new Trip(city, days);
                currentTrip = nextTrip;
                System.out.println("Trip to " + city + ", " + days + " days.");
            }
            else if (action.equals("pack"))
            {
                packingManager.packItem(argument);
                System.out.println("Packed: " + new Item(argument).getName());
            }
            else if (action.equals("unpack"))
            {
                boolean removed = packingManager.unpackItem(argument);
                if (removed)
                {
                    System.out.println("Unpacked: " + new Item(argument).getName());
                }
                else
                {
                    System.out.println("Item not in suitcase: " + argument);
                }
            }
            else if (action.equals("list") && argument.length() == 0)
            {
                System.out.println(packingManager.list());
            }
            else if (action.equals("check") && argument.length() == 0)
            {
                LinkedChain<Alert> alerts = packingRules.check(currentTrip, packingManager);
                if (alerts.isEmpty())
                {
                    System.out.println("All set.");
                }
                else
                {
                    Alert[] messages = alerts.toArray(new Alert[alerts.size()]);
                    for (int i = 0; i < messages.length; i++)
                    {
                        System.out.println(messages[i].getMessage());
                    }
                }
            }
            else
            {
                printHelp();
            }
        }
        catch (IllegalArgumentException exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private void printHelp()
    {
        System.out.println("Unknown command");
        System.out.println("Commands: trip destination days, pack item, unpack item, list, check, quit");
    }
}
