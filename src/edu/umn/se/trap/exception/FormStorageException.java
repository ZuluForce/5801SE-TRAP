// FormStorageException.java
package edu.umn.se.trap.exception;

/**
 * An exception relating to the operation of the form storage sub-system
 * 
 * @author nagell2008
 * 
 */
public class FormStorageException extends TRAPException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = -2751456416032066692L;

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public FormStorageException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public FormStorageException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public FormStorageException(Throwable t)
    {
        super(t);
    }

}
