// InsufficientFundsException.java
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class InsufficientFundsException extends TRAPException
{

    /**
     * @param msg
     * @param t
     */
    public InsufficientFundsException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public InsufficientFundsException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param t
     */
    public InsufficientFundsException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
