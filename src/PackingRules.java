/** Fixed MVP packing recommendations, without a weather API. */
public class PackingRules
{
    private static final String[] BASIC_ITEMS = {
        "passport", "charger", "toothbrush"
    };
    private static final String[] RAINY_CITIES = {
        "Seattle", "London", "Vancouver", "Portland", "Dublin",
        "Glasgow", "Amsterdam", "Singapore", "Hong Kong"
    };

    public LinkedChain<Item> getRequired(Trip trip)
    {
        if (trip == null)
        {
            throw new IllegalArgumentException("Create a trip before checking.");
        }
        LinkedChain<Item> required = new LinkedChain<Item>();
        for (String name : BASIC_ITEMS)
        {
            required.add(new Item(name));
        }
        for (int i = 0; i < trip.getDays(); i++)
        {
            required.add(new Item("tshirt"));
        }
        String city = trip.getDestination();
        for (String rainyCity : RAINY_CITIES)
        {
            if (city.equalsIgnoreCase(rainyCity))
            {
                required.add(new Item("umbrella"));
                break;
            }
        }
        return required;
    }

    /** Returns one alert per missing copy, leaving packed items unchanged. */
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
