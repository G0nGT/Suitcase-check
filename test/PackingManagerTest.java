import student.TestCase;

/**
 * Tests packing, unpacking, counting, and listing items.
 */
public class PackingManagerTest extends TestCase
{

    private PackingManager manager;

    /**
     * Creates a new PackingManager object before each test.
     */
    public void setUp() { manager = new PackingManager(); }

    /**
     * Tests packing and counting duplicate items regardless of capitalization.
     */
    public void testPackAndCount()
    {
        manager.packItem("Shirt");
        assertEquals(1, manager.getItemCount());
        assertEquals(1, manager.countItem("shirt"));
        manager.packItem("SHIRT");
        assertEquals(2, manager.countItem("shirt"));
        assertEquals(2, manager.getItemCount());
    }

    /**
     * Tests that invalid item names are rejected without changing contents.
     */
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

    /**
     * Tests that unpacking removes only one copy regardless of capitalization.
     */
    public void testUnpackOneCopy()
    {
        manager.packItem("shirt"); manager.packItem("shirt");
        manager.packItem("pants");
        assertTrue(manager.unpackItem("SHIRT"));
        assertEquals(1, manager.countItem("shirt"));
        assertEquals(1, manager.countItem("pants"));
        assertEquals(2, manager.getItemCount());
    }

    /**
     * Tests unpacking missing items and rejecting a null item name.
     */
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

    /**
     * Tests counting an absent item and rejecting an empty item name.
     */
    public void testCountAbsentAndInvalid()
    {
        assertEquals(0, manager.countItem("shirt"));
        assertEquals(0, manager.getItemCount());
        try { manager.countItem(""); fail("Empty name accepted"); }
        catch (IllegalArgumentException expected) { }
    }

    /**
     * Tests that listing preserves item order, duplicates, and contents.
     */
    public void testListPreservesContentsAndDuplicates()
    {
        manager.packItem("shirt"); manager.packItem("pants");
        manager.packItem("shirt");
        assertEquals("shirt\npants\nshirt", manager.list());
        assertEquals(3, manager.getItemCount());
    }

    /**
     * Tests listing and counting items when empty and after unpacking
     * the last item.
     */
    public void testEmptyListAndCount()
    {
        assertEquals("No items packed.", manager.list());
        assertEquals(0, manager.getItemCount());
        manager.packItem("shirt"); manager.unpackItem("shirt");
        assertEquals("No items packed.", manager.list());
        assertEquals(0, manager.getItemCount());
    }

}
