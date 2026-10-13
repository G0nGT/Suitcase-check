package suitcase;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class LinkedChainTest
{

    private LinkedChain<Item> chain;
    @BeforeEach
    public void setUp() { chain = new LinkedChain<Item>(); }
    @Test
    public void addAndNullRejection()
    {
        assertTrue(chain.add(new Item("shirt")));
        try { chain.add(null); fail("Null accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(1, chain.size());
        assertTrue(chain.contains(new Item("shirt")));
    }
    @Test
    public void addToEndAndNullRejection()
    {
        chain.addToEnd(new Item("shirt")); chain.addToEnd(new Item("pants"));
        assertEquals("shirt", chain.get(0).getName());
        assertEquals("pants", chain.get(1).getName());
        try { chain.addToEnd(null); fail("Null accepted"); }
        catch (IllegalArgumentException expected) { }
        assertEquals(2, chain.size());
    }
    @Test
    public void removeFirstAndEmpty()
    {
        assertNull(chain.remove());
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        assertEquals("pants", chain.remove().getName());
        assertEquals(1, chain.size());
    }
    @Test
    public void removeMatchingAndMissing()
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
    @Test
    public void duplicateRemovalAndCount()
    {
        chain.add(new Item("shirt")); chain.add(new Item("shirt"));
        assertEquals(2, chain.count(new Item("shirt")));
        assertTrue(chain.remove(new Item("shirt")));
        assertEquals(1, chain.count(new Item("shirt")));
        assertEquals(0, chain.count(null));
        assertEquals(0, chain.count(new Item("shoes")));
    }
    @Test
    public void containsEmptyAndPresent()
    {
        assertFalse(chain.contains(new Item("shirt")));
        chain.add(new Item("shirt"));
        assertTrue(chain.contains(new Item("shirt")));
        assertFalse(chain.contains(null));
        assertEquals(1, chain.size());
    }
    @Test
    public void sizeEmptyAndPopulated()
    {
        assertEquals(0, chain.size());
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        chain.add(new Item("jacket")); assertEquals(3, chain.size());
    }
    @Test
    public void isEmptyAfterRemoval()
    {
        assertTrue(chain.isEmpty()); chain.add(new Item("shirt"));
        assertFalse(chain.isEmpty()); chain.remove();
        assertTrue(chain.isEmpty());
    }
    @Test
    public void clearPopulatedAndEmpty()
    {
        chain.add(new Item("shirt")); chain.clear();
        assertEquals(0, chain.size()); assertFalse(chain.contains(new Item("shirt")));
        chain.clear(); assertTrue(chain.isEmpty());
    }
    @Test
    public void getAndBounds()
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
    @Test
    public void arraysAndBadArrays()
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
    @Test
    public void stringRepresentation()
    {
        assertEquals("[]", chain.toString());
        chain.add(new Item("shirt")); chain.add(new Item("pants"));
        assertEquals("[pants, shirt]", chain.toString());
    }
    @Test
    public void reusesSameClassForAlerts()
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
