/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
package edu.umn.se.trap.exception;

/**
 * @author andrewh
 * 
 */
public abstract class TRAPException extends Exception
{
    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = -1124664119379547664L;

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    protected TRAPException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    protected TRAPException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    protected TRAPException(Throwable t)
    {
        super(t);
    }

}
