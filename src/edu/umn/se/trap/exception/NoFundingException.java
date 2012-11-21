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
 * TODO: Do we need this in addition to the InsufficientFundsException? Is there a potential
 * difference of when we would use one or the other?
 * 
 * @author planeman
 * 
 */
public class NoFundingException extends TRAPException
{
    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = 3181387055854507844L;

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public NoFundingException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public NoFundingException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public NoFundingException(Throwable t)
    {
        super(t);
    }
}
