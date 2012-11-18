// FormProcessorException.java
package edu.umn.se.trap.exception;

/**
 * An exception referencing a problem encountered while processing a form that isn't related to
 * input validation, buisness logic.
 * 
 * @author planeman
 * 
 */
public class FormProcessorException extends TRAPException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = 2556782487298914914L;

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public FormProcessorException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public FormProcessorException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public FormProcessorException(Throwable t)
    {
        super(t);
    }

}
