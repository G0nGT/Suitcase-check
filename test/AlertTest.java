import student.TestCase;
public class AlertTest extends TestCase
{

    public void testMessageAndDisplay()
    {
        Alert alert = new Alert("Missing: charger");
        assertEquals("Missing: charger", alert.getMessage());
        assertEquals("Missing: charger", alert.toString());
    }
    public void testMessageBoundary()
    {
        Alert alert = new Alert(" x ");
        assertEquals("x", alert.getMessage());
        assertEquals("x", alert.toString());
    }
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
