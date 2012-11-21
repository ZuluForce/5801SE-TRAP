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
package edu.umn.se.trap.rules;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;

/**
 * The interface that all rules in the TRAP system must implement in-order for the rule registry to
 * communicate with them.
 * 
 * @author andrewh
 * 
 */
public interface TRAPRule
{
    /**
     * Check the logic associated with this rule.
     * 
     * @param app - The ReimbursementApp object that has all information required to process an
     *            application.
     * @throws TRAPException - When a problem occurs during the checking/processing of the rule.
     */
    public void checkRule(ReimbursementApp app) throws TRAPException;
}
