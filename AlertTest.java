import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class AlertTest
{

    @Test
    public void messageAndDisplay()
    {
        Alert alert = new Alert("Missing: charger");
        assertEquals("Missing: charger", alert.getMessage());
        assertEquals("Missing: charger", alert.toString());
    }
    @Test
    public void messageBoundary()
    {
        Alert alert = new Alert(" x ");
        assertEquals("x", alert.getMessage());
        assertEquals("x", alert.toString());
    }
    @Test
    public void rejectsEmptyMessage()
    {
        String[] messages = {null, "", "   "};
        for (int i = 0; i < messages.length; i++)
        {
            try { new Alert(messages[i]); fail("Empty message accepted"); }
            catch (IllegalArgumentException expected) { }
        }
    }

}
