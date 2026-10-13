import student.TestCase;

/**
 * Tests the Alert class.
 *
 * @version 2026.10.13
 */
public class AlertTest extends TestCase
{

    /**
     * Tests creating an alert with a valid message.
     */
    public void testMessageAndDisplay()
    {
        Alert alert = new Alert("Missing: charger");
        assertEquals("Missing: charger", alert.getMessage());
        assertEquals("Missing: charger", alert.toString());
    }

    /**
     * Tests creating an alert with message boundaries.
     */
    public void testMessageBoundary()
    {
        Alert alert = new Alert(" x ");
        assertEquals("x", alert.getMessage());
        assertEquals("x", alert.toString());
    }

    /**
     * Tests creating an alert with an empty message.
     */
    public void testRejectsEmptyMessage()
    {
        String[] messages = {null, "", "   "};
        for (int i = 0; i < messages.length; i++)
        {
            try { new Alert(messages[i]); fail("Empty message accepted"); }
            catch (IllegalArgumentException expected) { }
        }
    }

}
