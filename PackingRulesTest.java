import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class PackingRulesTest
{

    private PackingRules rules;
    private PackingManager manager;
    @BeforeEach
    public void setUp()
    {
        rules = new PackingRules(); manager = new PackingManager();
    }
    @Test
    public void requiredNonRainyTrip()
    {
        LinkedChain<Item> required = rules.getRequired(new Trip("Phoenix", 3));
        assertEquals(6, required.size());
        assertEquals(3, required.count(new Item("tshirt")));
        assertTrue(required.contains(new Item("passport")));
        assertTrue(required.contains(new Item("charger")));
        assertTrue(required.contains(new Item("toothbrush")));
        assertFalse(required.contains(new Item("umbrella")));
    }
    @Test
    public void rainyAndDurationBoundaries()
    {
        String[] cities = {"seattle", "London", "Vancouver"};
        for (int i = 0; i < cities.length; i++)
        {
            LinkedChain<Item> required = rules.getRequired(new Trip(cities[i], 1));
            assertEquals(5, required.size());
            assertTrue(required.contains(new Item("umbrella")));
        }
        assertEquals(30, rules.getRequired(new Trip("Phoenix", 30)).count(new Item("tshirt")));
    }
    @Test
    public void rejectsNullTrip()
    {
        try { rules.getRequired(null); fail("Null trip accepted"); }
        catch (IllegalArgumentException expected) { }
        try { rules.check(null, manager); fail("Null trip accepted"); }
        catch (IllegalArgumentException expected) { }
    }
    @Test
    public void rejectsNullManager()
    {
        try { rules.check(new Trip("Seattle", 3), null); fail("Null manager accepted"); }
        catch (IllegalArgumentException expected) { }
    }
    private void packBasics()
    {
        manager.packItem("passport"); manager.packItem("charger");
        manager.packItem("toothbrush");
    }
    @Test
    public void allSetPreservesSuitcase()
    {
        packBasics(); manager.packItem("tshirt"); manager.packItem("umbrella");
        String before = manager.list();
        assertTrue(rules.check(new Trip("Seattle", 1), manager).isEmpty());
        assertEquals(before, manager.list());
    }
    @Test
    public void exactlyOneMissing()
    {
        packBasics();
        LinkedChain<Alert> alerts = rules.check(new Trip("Phoenix", 1), manager);
        assertEquals(1, alerts.size());
        assertEquals("Missing: tshirt", alerts.get(0).getMessage());
        assertEquals(3, manager.getItemCount());
    }
    @Test
    public void duplicateAlertsAndRepeatedCheck()
    {
        packBasics(); manager.packItem("tshirt");
        Trip trip = new Trip("Phoenix", 3);
        LinkedChain<Alert> alerts = rules.check(trip, manager);
        assertEquals(2, alerts.size());
        assertEquals("Missing: tshirt", alerts.get(0).getMessage());
        assertEquals("Missing: tshirt", alerts.get(1).getMessage());
        assertEquals(2, rules.check(trip, manager).size());
        assertEquals(4, manager.getItemCount());
    }
    @Test
    public void emptySuitcaseAndExtraItems()
    {
        assertEquals(7, rules.check(new Trip("Seattle", 3), manager).size());
        packBasics(); manager.packItem("tshirt"); manager.packItem("tshirt");
        manager.packItem("jacket");
        assertTrue(rules.check(new Trip("Phoenix", 1), manager).isEmpty());
    }

}
