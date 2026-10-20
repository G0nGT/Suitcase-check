import student.TestCase;
public class ItemTest extends TestCase
{

    public void testValidNameAndDisplay()
    {
        Item item = new Item(" Shirt ");
        assertEquals("shirt", item.getName());
        assertEquals("shirt", item.toString());
    }
    public void testInvalidNames()
    {
        String[] names = { null, "", "   ", ";;;", "shirt1",
            "abcdefghijklmnopqrstuvwxyzabcde" };
        for (int i = 0; i < names.length; i++)
        {
            try { new Item(names[i]); fail("Invalid name accepted"); }
            catch (IllegalArgumentException expected) { }
        }
    }
    public void testEqualityAndHashCode()
    {
        Item item = new Item("shirt");
        Item equal = new Item("SHIRT");
        assertTrue(item.equals(item));
        assertTrue(item.equals(equal));
        assertTrue(equal.equals(item));
        assertEquals(item.hashCode(), equal.hashCode());
        assertFalse(item.equals(new Item("pants")));
        assertFalse(item.equals(null));
        assertFalse(item.equals("shirt"));
    }
    public void testBoundaryNames()
    {
        String name = "abcdefghijklmnopqrstuvwxyzabcd";
        assertEquals(name, new Item(name).getName());
        assertEquals("a", new Item("a").toString());
        assertEquals("rain jacket", new Item("rain jacket").getName());
        assertEquals("t-shirt", new Item("t-shirt").getName());
    }

}
