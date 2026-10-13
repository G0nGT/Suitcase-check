import student.TestCase;

/**
 * Tests the commands used to create trips and manage packed items.
 */
public class CommandTest extends TestCase
{

    private Command command;

    /**
     * Creates a new Command object before each test.
     */
    public void setUp() { command = new Command(); }

    /**
     * Runs a command and captures its printed output.
     *
     * @param input the command to process
     * @return the output with normalized line endings
     */
    private String run(String input)
    {
        java.io.PrintStream old = System.out;
        java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
        java.io.PrintStream capture = new java.io.PrintStream(bytes);
        try
        {
            System.setOut(capture);
            command.processCommand(input);
            capture.flush();
            return bytes.toString().replace("\r\n", "\n");
        }
        finally { System.setOut(old); capture.close(); }
    }

    /**
     * Tests creating a trip, packing items, listing items, and unpacking.
     */
    public void testFullHappyPath()
    {
        assertTrue(run("trip Seattle 1").contains("Trip to Seattle, 1 days."));
        run("pack passport"); run("pack charger"); run("pack toothbrush");
        run("pack tshirt"); run("pack umbrella");
        assertEquals("All set.\n", run("check"));
        assertTrue(run("list").contains("charger"));
        assertTrue(run("unpack charger").contains("Unpacked: charger"));
        assertEquals("Missing: charger\n", run("check"));
    }

    /**
     * Tests that unknown and empty commands preserve packed items.
     */
    public void testUnknownAndEmptyCommandsPreserveState()
    {
        run("pack Shirt");
        String before = run("list");
        String[] inputs = {null, "", "   ", "dance", "list extra", "check extra"};
        for (int i = 0; i < inputs.length; i++)
        {
            assertTrue(run(inputs[i]).contains("Unknown command"));
            assertEquals(before, run("list"));
        }
        assertTrue(run("pack pants").contains("Packed: pants"));
    }

    /**
     * Tests that invalid trip commands preserve the current trip.
     */
    public void testBadTripPreservesCurrentTrip()
    {
        run("trip Phoenix 1");
        String before = run("check");
        String[] inputs = {"trip Phoenix abc", "trip Phoenix 0", "trip Phoenix -1",
            "trip Phoenix 31", "trip Phoenix 999999999999999999", "trip", "trip 3"};
        for (int i = 0; i < inputs.length; i++)
        {
            assertFalse(run(inputs[i]).contains("Trip to"));
            assertEquals(before, run("check"));
        }
    }

    /**
     * Tests checking before a trip exists and processing invalid items.
     */
    public void testCheckBeforeTripAndInvalidItems()
    {
        assertTrue(run("check").contains("Create a trip"));
        String[] inputs = {"pack", "pack ;;;", "pack abcdefghijklmnopqrstuvwxyzabcde", "unpack"};
        for (int i = 0; i < inputs.length; i++)
        {
            run(inputs[i]);
            assertEquals("No items packed.\n", run("list"));
        }
        assertTrue(run("unpack shoes").contains("Item not in suitcase"));
        assertTrue(run("trip Phoenix 1").contains("Trip to"));
    }

    /**
     * Tests packing duplicate items and unpacking one copy at a time.
     */
    public void testDuplicatePackAndUnpack()
    {
        run("pack SHIRT"); run("pack shirt");
        run("unpack Shirt");
        assertEquals("shirt\n", run("list"));
        run("unpack shirt");
        assertEquals("No items packed.\n", run("list"));
    }

    /**
     * Tests that new trips retain items and support multiword city names.
     * Also tests command capitalization and extra spaces.
     */
    public void testNewTripRetainsItemsAndSupportsMultiwordCity()
    {
        run("pack charger");
        assertTrue(run("trip New York 3").contains("Trip to New York, 3 days."));
        assertEquals("charger\n", run("list"));
        assertTrue(run("  PACK   rain jacket  ").contains("Packed: rain jacket"));
    }

}
