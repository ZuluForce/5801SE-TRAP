// InvalidUsernameException.java
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class InvalidUsernameException extends TRAPException
{

    /**
     * @param msg
     * @param t
     */
    public InvalidUsernameException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public InvalidUsernameException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param t
     */
    public InvalidUsernameException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
