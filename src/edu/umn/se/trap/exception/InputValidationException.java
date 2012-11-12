/**
 * 
 */
package edu.umn.se.trap.exception;

/**
 * An exception representing an error during input validation.
 * 
 * @author planeman
 * 
 */
public class InputValidationException extends TRAPException
{
    private static final long serialVersionUID = -1191812454892764622L;

    /**
     * Construct an InputValidationException with a given exception msg.
     * 
     * @param msg
     */
    public InputValidationException(final String msg)
    {
        super(msg);
    }

    /**
     * Construct an InputValidationException with the given msg and exception type of t.
     * 
     * @param msg
     *            Message for the constructed exception
     * @param t
     *            Exception being wrapped
     */
    public InputValidationException(final String msg, final Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct an InputValidationException wrapping the given exception t.
     * 
     * @param t
     *            Exception being wrapped
     */
    public InputValidationException(final Throwable t)
    {
        super(t);
    }
}
