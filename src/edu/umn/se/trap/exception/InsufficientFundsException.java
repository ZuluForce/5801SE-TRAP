// InsufficientFundsException.java
package edu.umn.se.trap.exception;

/**
 * An exception the means that there are insufficient funds for a reimbursement.
 * 
 * @author planeman
 * 
 */
public class InsufficientFundsException extends TRAPException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = 6951396632446605148L;

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public InsufficientFundsException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public InsufficientFundsException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public InsufficientFundsException(Throwable t)
    {
        super(t);
    }

}
