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
