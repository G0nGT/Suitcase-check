/**
 * Represents one item packed in a suitcase.
 * Item names must contain between 1 and 30 characters and may
 * contain letters, digits, spaces, apostrophes, and hyphens.
 *
 * @author Shresta Vangala
 * @version 2026.10.13
 */
public class Item
{
    private String name;

    /**
     * Creates an item with the specified name.
     *
     * @param itemName
     *            the name of the item
     * @throws IllegalArgumentException
     *             if the name is null, empty, too long, or contains
     *             invalid characters
     */
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

    /**
     * Returns the name of this item.
     *
     * @return the item name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Determines whether this item equals another object.
     * Item names are compared without regard to capitalization.
     *
     * @param other
     *            the object to compare with this item
     * @return true if both objects are items with matching names
     */
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

    /**
     * Returns a hash code consistent with case-insensitive equality.
     *
     * @return the hash code for this item
     */
    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    /**
     * Returns the name of this item as a string.
     *
     * @return the item name
     */
    @Override
    public String toString()
    {
        return name;
    }
}
