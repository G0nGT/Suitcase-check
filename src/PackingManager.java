/** Manages packed items through the chain's public methods. */
public class PackingManager
{
    private LinkedChain<Item> suitcase;

    public PackingManager()
    {
        suitcase = new LinkedChain<Item>();
    }

    public void packItem(String name)
    {
        suitcase.add(new Item(name));
    }

    public boolean unpackItem(String name)
    {
        return suitcase.remove(new Item(name));
    }

    public int countItem(String name)
    {
        return suitcase.count(new Item(name));
    }

    public int getItemCount()
    {
        return suitcase.size();
    }

    /** Lists each copy separately, without changing the suitcase. */
    public String list()
    {
        if (suitcase.isEmpty())
        {
            return "No items packed.";
        }
        Item[] items = suitcase.toArray(new Item[suitcase.size()]);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < items.length; i++)
        {
            if (i > 0)
            {
                result.append("\n");
            }
            result.append(items[i].getName());
        }
        return result.toString();
    }
}
