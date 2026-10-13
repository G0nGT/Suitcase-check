import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MainTest
{

    private String runMain(String input)
    {
        java.io.InputStream oldIn = System.in;
        java.io.PrintStream oldOut = System.out;
        java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();
        java.io.PrintStream capture = new java.io.PrintStream(bytes);
        try
        {
            System.setIn(new java.io.ByteArrayInputStream(input.getBytes(java.nio.charset.StandardCharsets.UTF_8)));
            System.setOut(capture);
            Main.main(new String[0]);
            capture.flush();
            return bytes.toString();
        }
        finally { System.setIn(oldIn); System.setOut(oldOut); capture.close(); }
    }
    @Test
    public void runsAndQuits()
    {
        String output = runMain("pack charger\nlist\nquit\npack pants\n");
        assertTrue(output.contains("Packed: charger"));
        assertTrue(output.contains("Goodbye."));
        assertFalse(output.contains("Packed: pants"));
    }
    @Test
    public void recoversFromBadInput()
    {
        String output = runMain("dance\ntrip Phoenix abc\npack charger\nquit\n");
        assertTrue(output.contains("Unknown command"));
        assertTrue(output.contains("Days must be 1 to 30."));
        assertTrue(output.contains("Packed: charger"));
    }
    @Test
    public void emptyInputEndsCleanly()
    {
        assertTrue(runMain("").contains("Goodbye."));
    }

}
