import student.TestCase;
public class PackingManagerTest extends TestCase
{

    private PackingManager manager;
    public void setUp() { manager = new PackingManager(); }
    public void testPackAndCount()
    {
        manager.packItem("Shirt");
        assertEquals(1, manager.getItemCount());
        assertEquals(1, manager.countItem("shirt"));
        manager.packItem("SHIRT");
        assertEquals(2, manager.countItem("shirt"));
        assertEquals(2, manager.getItemCount());
    }
    public void testInvalidPackPreservesContents()
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
    public void testUnpackOneCopy()
    {
        manager.packItem("shirt"); manager.packItem("shirt");
        manager.packItem("pants");
        assertTrue(manager.unpackItem("SHIRT"));
        assertEquals(1, manager.countItem("shirt"));
        assertEquals(1, manager.countItem("pants"));
        assertEquals(2, manager.getItemCount());
    }
    public void testMissingAndEmptyUnpack()
    {
        assertFalse(manager.unpackItem("shoes"));
        manager.packItem("shirt");
        assertFalse(manager.unpackItem("shoes"));
        assertEquals(1, manager.getItemCount());
        try { manager.unpackItem(null); fail("Null accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(1, manager.getItemCount());
    }
    public void testCountAbsentAndInvalid()
    {
        assertEquals(0, manager.countItem("shirt"));
        assertEquals(0, manager.getItemCount());
        try { manager.countItem(""); fail("Empty name accepted"); }
        catch (IllegalArgumentException expected) { }
    }
    public void testListPreservesContentsAndDuplicates()
    {
        manager.packItem("shirt"); manager.packItem("pants");
        manager.packItem("shirt");
        assertEquals("shirt\npants\nshirt", manager.list());
        assertEquals(3, manager.getItemCount());
    }
    public void testEmptyListAndCount()
    {
        assertEquals("No items packed.", manager.list());
        assertEquals(0, manager.getItemCount());
        manager.packItem("shirt"); manager.unpackItem("shirt");
        assertEquals("No items packed.", manager.list());
        assertEquals(0, manager.getItemCount());
    }

}
