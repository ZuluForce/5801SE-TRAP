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
package edu.umn.se.trap.form;

import java.util.Map;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.rules.DateValidator;

/**
 * @author planeman
 * 
 */
public class FormDataConverter
{
    public static ReimbursementApp formToReimbursementApp(Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        // TODO: Write this method
        ReimbursementApp app = new ReimbursementApp();

        String value;

        // Start with datetimes
        value = data.get(InputFieldKeys.ARRIVAL_DATETIME);
        if (value == null)
        {
            throw new MissingFieldException("Missing arrival datetime field");
        }
        app.setArrivalDatetime(DateValidator.convertToDatetime(value));

        value = data.get(InputFieldKeys.DEPARTURE_DATETIME);
        if (value == null)
        {
            throw new MissingFieldException("Missing departure datetime field");
        }
        app.setDepartureDatetime(DateValidator.convertToDatetime(value));

        return app;
    }
}
