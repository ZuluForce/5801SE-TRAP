/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
package edu.umn.se.trap.exception;

/**
 * An exception representing an error during input validation.
 * 
 * @author planeman
 * 
 */
public class InputValidationException extends TRAPException
{
    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = -1191812454892764622L;

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public InputValidationException(final String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public InputValidationException(final String msg, final Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct an InputValidationException wrapping the given exception t.
     * 
     * @param t Exception being wrapped
     */
    public InputValidationException(final Throwable t)
    {
        super(t);
    }
}
