package suitcase;
/** A single packed item. Names are case insensitive. */
public class Item
{
    private String name;

    /** Creates an item with a valid name. */
    public Item(String itemName)
    {
        if (itemName == null || itemName.trim().length() == 0
            || itemName.trim().length() > 30)
        {
            throw new IllegalArgumentException("Item name must be 1 to 30 characters.");
        }
        String cleaned = itemName.trim().toLowerCase(java.util.Locale.ROOT);
        for (int i = 0; i < cleaned.length(); i++)
        {
            char c = cleaned.charAt(i);
            if (!((c >= 'a' && c <= 'z') || c == ' ' || c == '-'))
            {
                throw new IllegalArgumentException("Use letters, spaces, or hyphens for item names.");
            }
        }
        name = cleaned;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other == null || getClass() != other.getClass())
        {
            return false;
        }
        Item item = (Item)other;
        return name.equals(item.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
