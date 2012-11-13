/**
 * 
 */
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class BusinessLogicException extends TRAPException
{
    private static final long serialVersionUID = -2550132764104621605L;

    /**
     * @param msg
     * @param t
     */
    public BusinessLogicException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public BusinessLogicException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param t
     */
    public BusinessLogicException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
