// InvalidUsernameException.java
package edu.umn.se.trap.exception;

/**
 * An exception meaning that a provided username was not valid according to what is in the UserDB.
 * 
 * @author planeman
 * 
 */
public class InvalidUsernameException extends TRAPException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = -3497608496311700189L;

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public InvalidUsernameException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public InvalidUsernameException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public InvalidUsernameException(Throwable t)
    {
        super(t);
    }

}
