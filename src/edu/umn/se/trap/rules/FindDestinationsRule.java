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
// FindDestinationsRule.java
package edu.umn.se.trap.rules;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.PerDiemExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.form.OutputFieldKeys;

/**
 * @author planeman
 * 
 */
public class FindDestinationsRule extends BusinessLogicRule
{
    private static Logger log = LoggerFactory.getLogger(FindDestinationsRule.class);

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Integer numDestinations = 0;

        List<String> savedDestinations = new ArrayList<String>();

        for (TripDay day : app.getAllTripDays())
        {
            for (PerDiemExpense expense : day.getAllExpenses())
            {
                if (expense == null)
                {
                    continue;
                }

                if (isDestinationSeen(savedDestinations, expense))
                {
                    log.debug("Found duplicate destination on day {}", day.getDayNumber());
                    continue;
                }

                ++numDestinations;

                // Destination city
                String field = String.format(OutputFieldKeys.DESTINATION_CITY_FMT,
                        day.getDayNumber());
                app.setOutputField(field, expense.getCity());

                // Destination state
                field = String.format(OutputFieldKeys.DESTINATION_STATE_FMT, day.getDayNumber());
                app.setOutputField(field, expense.getState());

                // Destination country
                field = String.format(OutputFieldKeys.DESTINATION_COUNTRY_FMT, day.getDayNumber());
                app.setOutputField(field, expense.getCountry());
            }
        }

        log.info("Found {} destinations", numDestinations.toString());
        app.setOutputField(OutputFieldKeys.NUM_DESTINATIONS, numDestinations.toString());
    }

    /**
     * Checks if a given lodging expense's destination has already been seen given the destinations
     * list. If the destination hasn't been seen it will be added to the list and false returned. If
     * the destination is already present, nothing will be added and true will be returned.
     * 
     * @param destinations - The list of already seen destinations. The size of this list should
     *            always be a multiple of 3. In groups of three you will have the
     *            (city,state,country) for every destination that has been seen.
     * 
     * @param expense - The expense whose location you want to potentially add to the total
     *            destination list.
     * @return - true if the the destination for the given expense has already been seen or false if
     *         it hasn't but has been added to the list.
     */
    private boolean isDestinationSeen(List<String> destinations, PerDiemExpense expense)
    {
        String city, state, country;
        city = expense.getCity();
        state = expense.getState();
        country = expense.getCountry();

        for (int i = 0; i < destinations.size(); i += 3)
        {
            try
            {
                // Check the city, state, country
                if (destinations.get(i).equalsIgnoreCase(city)
                        && destinations.get(i + 1).equalsIgnoreCase(state)
                        && destinations.get(i + 2).equalsIgnoreCase(country))
                {
                    return true;
                }

                // Continue searching
                continue;
            }
            catch (IndexOutOfBoundsException indexError)
            {
                log.error("Stored destinations array is missing information.");
                throw new TRAPRuntimeException("Stored destinations array is missing information");
            }
        }

        // We did not find a matching destination, add it
        destinations.add(city);
        destinations.add(state);
        destinations.add(country);

        return false;
    }
}
