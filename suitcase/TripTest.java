package suitcase;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TripTest
{

    @Test
    public void storesTrip()
    {
        Trip trip = new Trip(" Seattle ", 3);
        assertEquals("Seattle", trip.getDestination());
        assertEquals(3, trip.getDays());
    }
    @Test
    public void durationBoundaries()
    {
        assertEquals(1, new Trip("X", 1).getDays());
        assertEquals(30, new Trip("New York", 30).getDays());
        assertEquals("X", new Trip("X", 1).getDestination());
    }
    @Test
    public void rejectsInvalidDays()
    {
        int[] days = {-1, 0, 31, 9999};
        for (int i = 0; i < days.length; i++)
        {
            try { new Trip("Seattle", days[i]); fail("Invalid days accepted"); }
            catch (IllegalArgumentException expected) { }
        }
    }
    @Test
    public void rejectsInvalidDestination()
    {
        String[] cities = {null, "", "   "};
        for (int i = 0; i < cities.length; i++)
        {
            try { new Trip(cities[i], 3); fail("Invalid city accepted"); }
            catch (IllegalArgumentException expected) { }
        }
    }
}
