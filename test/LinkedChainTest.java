import student.TestCase;
public class LinkedChainTest extends TestCase
{

    private LinkedChain<Item> chain;
    public void setUp() { chain = new LinkedChain<Item>(); }
    public void testAddAndNullRejection()
    {
        assertTrue(chain.add(new Item("shirt")));
        try { chain.add(null); fail("Null accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(1, chain.size());
        assertTrue(chain.contains(new Item("shirt")));
    }
    public void testAddToEndAndNullRejection()
    {
        chain.addToEnd(new Item("shirt")); chain.addToEnd(new Item("pants"));
        assertEquals("shirt", chain.get(0).getName());
        assertEquals("pants", chain.get(1).getName());
        try { chain.addToEnd(null); fail("Null accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(2, chain.size());
    }
    public void testRemoveFirstAndEmpty()
    {
        assertNull(chain.remove());
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        assertEquals("pants", chain.remove().getName());
        assertEquals(1, chain.size());
    }
    public void testRemoveMatchingAndMissing()
    {
        assertFalse(chain.remove(new Item("shirt")));
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        chain.add(new Item("jacket"));
        assertTrue(chain.remove(new Item("pants")));
        assertTrue(chain.remove(new Item("shirt")));
        assertFalse(chain.remove(new Item("shoes")));
        assertFalse(chain.remove(null));
        assertEquals(1, chain.size());
        assertTrue(chain.remove(new Item("jacket")));
        assertTrue(chain.isEmpty());
    }
    public void testDuplicateRemovalAndCount()
    {
        chain.add(new Item("shirt")); chain.add(new Item("shirt"));
        assertEquals(2, chain.count(new Item("shirt")));
        assertTrue(chain.remove(new Item("shirt")));
        assertEquals(1, chain.count(new Item("shirt")));
        assertEquals(0, chain.count(null));
        assertEquals(0, chain.count(new Item("shoes")));
    }
    public void testContainsEmptyAndPresent()
    {
        assertFalse(chain.contains(new Item("shirt")));
        chain.add(new Item("shirt"));
        assertTrue(chain.contains(new Item("shirt")));
        assertFalse(chain.contains(null));
        assertEquals(1, chain.size());
    }
    public void testSizeEmptyAndPopulated()
    {
        assertEquals(0, chain.size());
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        chain.add(new Item("jacket")); assertEquals(3, chain.size());
    }
    public void testIsEmptyAfterRemoval()
    {
        assertTrue(chain.isEmpty()); chain.add(new Item("shirt"));
        assertFalse(chain.isEmpty()); chain.remove();
        assertTrue(chain.isEmpty());
    }
    public void testClearPopulatedAndEmpty()
    {
        chain.add(new Item("shirt")); chain.clear();
        assertEquals(0, chain.size()); assertFalse(chain.contains(new Item("shirt")));
        chain.clear(); assertTrue(chain.isEmpty());
    }
    public void testGetAndBounds()
    {
        chain.add(new Item("shirt"));
        assertEquals("shirt", chain.get(0).getName());
        int[] positions = {-1, 1};
        for (int i = 0; i < positions.length; i++)
        {
            try { chain.get(positions[i]); fail("Invalid index accepted"); }
            catch (IndexOutOfBoundsException expected) { }
        }
        chain.clear();
        try { chain.get(0); fail("Empty chain indexed"); }
        catch (IndexOutOfBoundsException expected) { }
    }
    public void testArraysAndBadArrays()
    {
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        Item[] values = chain.toArray(new Item[2]);
        assertEquals("pants", values[0].getName());
        assertEquals("shirt", values[1].getName());
        try { chain.toArray(null); fail("Null array accepted"); }
        catch (IllegalArgumentException expected) { }
        try { chain.toArray(new Item[1]); fail("Small array accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(2, chain.size());
        chain.clear(); assertEquals(0, chain.toArray(new Item[0]).length);
    }
    public void testStringRepresentation()
    {
        assertEquals("[]", chain.toString());
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        assertEquals("[pants, shirt]", chain.toString());
    }
    public void testReusesSameClassForAlerts()
    {
        LinkedChain<Alert> alerts = new LinkedChain<Alert>();
        Alert alert = new Alert("Missing: shirt");
        alerts.add(alert); alerts.add(new Alert("Missing: shirt"));
        assertEquals(2, alerts.size());
        assertEquals("Missing: shirt", alerts.get(0).getMessage());
        assertTrue(alerts.remove(alert)); assertEquals(1, alerts.size());
        assertEquals(chain.getClass(), alerts.getClass());
    }

}
