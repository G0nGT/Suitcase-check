/** Stores the destination and duration of a trip. */
public class Trip
{
    private String destination;
    private int days;

    public Trip(String city, int tripDays)
    {
        if (city == null || city.trim().length() == 0)
        {
            throw new IllegalArgumentException("Destination must not be empty.");
        }
        if (tripDays < 1 || tripDays > 30)
        {
            throw new IllegalArgumentException("Days must be 1 to 30.");
        }
        destination = city.trim();
        days = tripDays;
    }

    public String getDestination()
    {
        return destination;
    }

    public int getDays()
    {
        return days;
    }
}
