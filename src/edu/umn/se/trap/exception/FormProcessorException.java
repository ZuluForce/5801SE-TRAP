// FormProcessorException.java
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class FormProcessorException extends TRAPException
{

    /**
     * @param msg
     * @param t
     */
    public FormProcessorException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public FormProcessorException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param t
     */
    public FormProcessorException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
