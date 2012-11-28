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
package edu.umn.se.trap.rules.input;

import edu.umn.se.trap.rules.TRAPRule;

/**
 * Abstract class for rules that do some checking on the input. Some input is already checked in the
 * form data conversion such as required form fields, currency conversion, and date/datetime
 * interpretation.
 * 
 * @author andrewh
 * 
 */
public abstract class InputValidationRule implements TRAPRule
{
    // Abstract. Nothing here
}
