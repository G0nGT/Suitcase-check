package suitcase;
/** A message describing one missing copy of an item. */
public class Alert
{
    // ~ Fields ................................................................
    private String message;

    // ~ Constructor ...........................................................
    public Alert(String alertMessage)
    {
        if (alertMessage == null || alertMessage.trim().length() == 0)
        {
            throw new IllegalArgumentException("Alert message must not be empty.");
        }
        message = alertMessage.trim();
    }

    // ~ Methods ...............................................................
    public String getMessage()
    {
        return message;
    }

    @Override
    public String toString()
    {
        return message;
    }
}
