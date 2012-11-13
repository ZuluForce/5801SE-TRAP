/**
 * 
 */
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class NoFundingException extends TRAPException
{
    private static final long serialVersionUID = 3181387055854507844L;

    /**
     * @param msg
     */
    public NoFundingException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    public NoFundingException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public NoFundingException(Throwable t)
    {
        super(t);
    }
}
