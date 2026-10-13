package suitcase;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class PackingManagerTest
{

    private PackingManager manager;
    @BeforeEach
    public void setUp() { manager = new PackingManager(); }
    @Test
    public void packAndCount()
    {
        manager.packItem("Shirt");
        assertEquals(1, manager.getItemCount());
        assertEquals(1, manager.countItem("shirt"));
        manager.packItem("SHIRT");
        assertEquals(2, manager.countItem("shirt"));
        assertEquals(2, manager.getItemCount());
    }
    @Test
    public void invalidPackPreservesContents()
    {
        manager.packItem("shirt");
        String[] names = {null, "", ";;;", "abcdefghijklmnopqrstuvwxyzabcde"};
        for (int i = 0; i < names.length; i++)
        {
            try { manager.packItem(names[i]); fail("Invalid name accepted"); }
            catch (IllegalArgumentException expected) { }
            assertEquals(1, manager.getItemCount());
            assertEquals(1, manager.countItem("shirt"));
        }
    }
    @Test
    public void unpackOneCopy()
    {
        manager.packItem("shirt"); manager.packItem("shirt");
        manager.packItem("pants");
        assertTrue(manager.unpackItem("SHIRT"));
        assertEquals(1, manager.countItem("shirt"));
        assertEquals(1, manager.countItem("pants"));
        assertEquals(2, manager.getItemCount());
    }
    @Test
    public void missingAndEmptyUnpack()
    {
        assertFalse(manager.unpackItem("shoes"));
        manager.packItem("shirt");
        assertFalse(manager.unpackItem("shoes"));
        assertEquals(1, manager.getItemCount());
        try { manager.unpackItem(null); fail("Null accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(1, manager.getItemCount());
    }
    @Test
    public void countAbsentAndInvalid()
    {
        assertEquals(0, manager.countItem("shirt"));
        assertEquals(0, manager.getItemCount());
        try { manager.countItem(""); fail("Empty name accepted"); }
        catch (IllegalArgumentException expected) { }
    }
    @Test
    public void listPreservesContentsAndDuplicates()
    {
        manager.packItem("shirt"); manager.packItem("pants");
        manager.packItem("shirt");
        assertEquals("shirt\npants\nshirt", manager.list());
        assertEquals(3, manager.getItemCount());
    }
    @Test
    public void emptyListAndCount()
    {
        assertEquals("No items packed.", manager.list());
        assertEquals(0, manager.getItemCount());
        manager.packItem("shirt"); manager.unpackItem("shirt");
        assertEquals("No items packed.", manager.list());
        assertEquals(0, manager.getItemCount());
    }

}
