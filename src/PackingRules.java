/**
 * PackingRules class that provides packing recommendations based on a trip's
 * destination and duration, and checks for missing items without changing the
 * packed items.
 *
 * @author Shresta Vangala
 * @version 2026.10.13
 */
public class PackingRules
{
    // ----------------------------------------------------------
    /**
     * Creates a list of items required for the given trip. Every trip requires
     * a passport, charger, and toothbrush, along with one tshirt per day. An
     * umbrella is required for Seattle, London, and Vancouver.
     *
     * @param trip
     *            the trip to create packing recommendations for
     * @return a chain containing all required items
     * @throws IllegalArgumentException
     *             if the trip is null
     */
    public LinkedChain<Item> getRequired(Trip trip)
    {
        if (trip == null)
        {
            throw new IllegalArgumentException("Create a trip before checking.");
        }
        LinkedChain<Item> required = new LinkedChain<Item>();
        required.add(new Item("passport"));
        required.add(new Item("charger"));
        required.add(new Item("toothbrush"));
        for (int i = 0; i < trip.getDays(); i++)
        {
            required.add(new Item("tshirt"));
        }
        String city = trip.getDestination();
        if (city.equalsIgnoreCase("Seattle") || city.equalsIgnoreCase("London")
            || city.equalsIgnoreCase("Vancouver"))
        {
            required.add(new Item("umbrella"));
        }
        return required;
    }

    /**
     * Checks the packed items against the trip's requirements. Creates one
     * alert for each missing copy of a required item without changing the items
     * already packed.
     *
     * @param trip
     *            the trip whose packing requirements are checked
     * @param manager
     *            the manager containing the packed items
     * @return a chain of alerts for all missing items, or an empty chain if
     *             every required item is packed
     * @throws IllegalArgumentException
     *             if the trip or manager is null
     */
    public LinkedChain<Alert> check(Trip trip, PackingManager manager)
    {
        if (manager == null)
        {
            throw new IllegalArgumentException("Packing manager must not be null.");
        }
        LinkedChain<Item> required = getRequired(trip);
        Item[] items = required.toArray(new Item[required.size()]);
        LinkedChain<Item> processed = new LinkedChain<Item>();
        LinkedChain<Alert> alerts = new LinkedChain<Alert>();
        for (int i = 0; i < items.length; i++)
        {
            Item item = items[i];
            if (!processed.contains(item))
            {
                int missing = required.count(item) - manager.countItem(item.getName());
                for (int copy = 0; copy < missing; copy++)
                {
                    alerts.add(new Alert("Missing: " + item.getName()));
                }
                processed.add(item);
            }
        }
        return alerts;
    }
}
