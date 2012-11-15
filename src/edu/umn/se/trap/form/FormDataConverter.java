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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ConferenceInfo;
import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.LodgingExpense;
import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.MealTypeEnum;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.data.UserInfo;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.UserDB;
import edu.umn.se.trap.db.UserDBWrapper;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.InvalidUsernameException;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.rules.DateValidator;

/**
 * @author planeman
 * 
 */
public class FormDataConverter
{
    private static Logger log = LoggerFactory.getLogger(FormDataConverter.class);

    public static ReimbursementApp formToReimbursementApp(Map<String, String> data)
            throws TRAPException
    {
        ReimbursementApp app = new ReimbursementApp();

        String value;

        // The (Input) or (Output) preceeding comments means that the data being collected
        // is either coming from input into the RApp or going to the output map respectively.

        // (Input/Output) Start with datetimes
        value = getFormValue(data, InputFieldKeys.ARRIVAL_DATETIME);
        log.debug("Arrival Datetime: {}", value);
        app.setArrivalDatetime(DateValidator.convertToDatetime(value));
        app.setOutputField(OutputFieldKeys.ARRIVAL_DATETIME, value);

        value = getFormValue(data, InputFieldKeys.DEPARTURE_DATETIME);
        app.setDepartureDatetime(DateValidator.convertToDatetime(value));
        app.setOutputField(OutputFieldKeys.DEPARTURE_DATETIME, value);

        // (Input/Output) Build/Add the UserInfo data object
        addUserInfo(app, data);

        // (Input/Output) Build/Add the ConferenceInfo data object
        addConferenceInfo(app, data);

        // (Input/Output) Num Days
        value = getFormValue(data, InputFieldKeys.NUM_DAYS);
        int numDays = Integer.parseInt(value);
        app.setNumDays(numDays);
        app.setOutputField(OutputFieldKeys.NUM_DAYS, value);

        // (Input/Output) Add all meal expenses
        addMealExpenses(app, data);

        // (Input/Output) Add all lodging expenses
        addLodgingExpenses(app, data);

        // (Input/Output) Add all transportation expenses
        addTransportationExpenses(app, data);

        // (Input/Output) Add all incidental expenses
        addIncidentalExpenses(app, data);

        // (Input/Output) Add all other expenses
        addOtherExpenses(app, data);

        // (Input/Output) Add grants
        addGrants(app, data);

        // Set form submission time to now
        String datetimeNow = DateValidator.datetimeToString(new Date());
        app.setOutputField(OutputFieldKeys.FORM_SUBMISSION_DATETIME, datetimeNow);

        return app;
    }

    private static void addUserInfo(ReimbursementApp app, Map<String, String> data)
            throws TRAPException
    {
        String username, value;

        UserInfo userInfo = new UserInfo();

        // (Input) Username
        username = getFormValue(data, InputFieldKeys.USER_NAME);
        userInfo.setUsername(username);
        app.setOutputField(OutputFieldKeys.USER_NAME, username);

        // (Input) Emergency contact name/phone
        value = getFormValue(data, InputFieldKeys.EMERGENCY_NAME);
        userInfo.setEmergencyContactName(value);
        app.setOutputField(OutputFieldKeys.EMERGENCY_NAME, value);

        value = getFormValue(data, InputFieldKeys.EMERGENCY_PHONE);
        userInfo.setEmergencycontactPhone(value);
        app.setOutputField(OutputFieldKeys.EMERGENCY_PHONE, value);

        List<String> extraUserInfo = null;
        try
        {
            extraUserInfo = UserDBWrapper.getUserInfo(username);
        }
        catch (KeyNotFoundException e)
        {
            throw new InvalidUsernameException("Invalid username");
        }

        // (Input) Email Address
        value = extraUserInfo.get(UserDB.USER_FIELDS.EMAIL.ordinal());
        userInfo.setEmailAddress(value);
        app.setOutputField(OutputFieldKeys.EMAIL, value);

        // Add UserInfo objec to RApp
        app.setUserInfo(userInfo);

        // (Output) Full Name
        value = extraUserInfo.get(UserDB.USER_FIELDS.FULL_NAME.ordinal());
        app.setOutputField(OutputFieldKeys.FULL_NAME, value);

        // (Output) Citizenship
        value = extraUserInfo.get(UserDB.USER_FIELDS.CITIZENSHIP.ordinal());
        app.setOutputField(OutputFieldKeys.CITIZENSHIP, value);

        // (Output) Visa Status
        value = extraUserInfo.get(UserDB.USER_FIELDS.VISA_STATUS.ordinal());
        app.setOutputField(OutputFieldKeys.VISA_STATUS, value);

        // (Output) Paid by University
        value = extraUserInfo.get(UserDB.USER_FIELDS.PAID_BY_UNIVERSITY.ordinal());
        app.setOutputField(OutputFieldKeys.VISA_STATUS, value);
    }

    private static void addConferenceInfo(ReimbursementApp app, Map<String, String> data)
            throws TRAPException
    {
        ConferenceInfo conferenceInfo = new ConferenceInfo();
        String value;

        // Get information on the travel type funding (sponsored/non-sponsored)
        boolean haveTravelType = false;
        try
        {
            value = getFormValue(data, InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
            app.setTravelTypeCSESponsored(value);
            app.setOutputField(OutputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED, value);

            haveTravelType = true;
        }
        catch (MissingFieldException mfe)
        {
            app.setTravelTypeCSESponsored(null);
        }

        try
        {
            value = getFormValue(data, InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED);
            app.setTravelTypeDTCSponsored(value);
            app.setOutputField(OutputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED, value);

            haveTravelType = true;
        }
        catch (MissingFieldException mfe)
        {
            app.setTravelTypeDTCSponsored(null);
        }

        try
        {
            value = getFormValue(data, InputFieldKeys.TRAVEL_TYPE_NONSPONSORED);
            app.setTravelTypeNonSponsored(value);
            app.setOutputField(OutputFieldKeys.TRAVEL_TYPE_NONSPONSORED, value);

            haveTravelType = true;
        }
        catch (MissingFieldException mfe)
        {
            app.setTravelTypeNonSponsored(null);
        }

        // Must have at least one of the travel types
        if (!haveTravelType)
        {
            throw new MissingFieldException(
                    "Must have at least one Travel Type (CSE sponsored/DTC sponsored/Nonsponsored");
        }

        // Conference Title
        value = getFormValue(data, InputFieldKeys.JUSTIFICATION_CONFERENCE_TITLE);
        conferenceInfo.setJustificationConferenceTitle(value);
        app.setOutputField(OutputFieldKeys.JUSTIFICATION_CONFERENCE_TITLE, value);

        // Presented
        value = getFormValue(data, InputFieldKeys.JUSTIFICATION_PRESENTED);
        conferenceInfo.setJustificationPresented(value);
        app.setOutputField(OutputFieldKeys.JUSTIFICATION_PRESENTED, value);

        if (conferenceInfo.isJustificationPresented())
        {
            // Presentation Title
            value = getFormValue(data, InputFieldKeys.JUSTIFICATION_PRESENTATION_TITLE);
            conferenceInfo.setJustificationPresentationTitle(value);
            app.setOutputField(OutputFieldKeys.JUSTIFICATION_PRESENTATION_TITLE, value);

            // Presentation Abstract
            value = getFormValue(data, InputFieldKeys.JUSTIFICATION_PRESENTATION_ABSTRACT);
            conferenceInfo.setJustificationPresentationAbstract(value);
            app.setOutputField(OutputFieldKeys.JUSTIFICATION_PRESENTATION_ABSTRACT, value);

            // Presentation Acknowledgement
            value = getFormValue(data, InputFieldKeys.JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT);
            conferenceInfo.setJustificationPresentationAcknowledge(value);
            app.setOutputField(OutputFieldKeys.JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT, value);
        }

        // Justifications for the use of sponsored/non-sponsored grants
        boolean haveGrantJust = false;
        try
        {
            value = getFormValue(data, InputFieldKeys.JUSTIFICATION_NONSPONSORED);
            conferenceInfo.setJustificationNonSponsored(value);
            app.setOutputField(OutputFieldKeys.JUSTIFICATION_NONSPONSORED, value);

            haveGrantJust = true;
        }
        catch (MissingFieldException mfe)
        {
            // A later rule will have to handle this missing field
            conferenceInfo.setJustificationNonSponsored(null);
        }

        try
        {
            value = getFormValue(data, InputFieldKeys.JUSTIFICATION_SPONSORED);
            conferenceInfo.setJustificationSponsored(value);
            app.setOutputField(OutputFieldKeys.JUSTIFICATION_SPONSORED, value);
        }
        catch (MissingFieldException mfe)
        {
            if (!haveGrantJust)
            {
                throw new MissingFieldException(
                        "Grant justfication field. Must have at least one depending on the grants used.");
            }

            // A later rule will have to handle this missing field
            conferenceInfo.setJustificationSponsored(null);
        }

        // Add conferenceInfo to app
        app.setConferenceInfo(conferenceInfo);
    }

    private static void addMealExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException
    {
        String filledKey, value;

        MealExpense mealExpense;

        // First gather all breakfast expenses
        for (int i = 1; i <= app.getNumDays(); ++i)
        {
            mealExpense = new MealExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.BREAKFAST_CITY_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.BREAKFAST_COUNTRY_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setCountry(value);

                filledKey = String.format(InputFieldKeys.BREAKFAST_STATE_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setState(value);

                mealExpense.setType(MealTypeEnum.BREAKFAST);

                // Set the date for this expense relative to the departure date of the trip
                mealExpense.setExpenseDate(DateValidator.advanceDateInDays(
                        app.getDepartureDatetime(), i - 1));

                app.addMealExpense(mealExpense);
            }
            catch (MissingFieldException mfe)
            {
                // It could be that this expense is just missing in which case we just continue
                if (mealExpense.isEmpty())
                    continue;

                // Otherwise we have a partially built expense which shouldn't be allowed
                throw mfe;
            }
        }

        // Gather all lunch expenses
        for (int i = 1; i <= app.getNumDays(); ++i)
        {
            mealExpense = new MealExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.LUNCH_CITY_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.LUNCH_COUNTRY_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setCountry(value);

                filledKey = String.format(InputFieldKeys.LUNCH_STATE_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setState(value);

                mealExpense.setType(MealTypeEnum.LUNCH);
                mealExpense.setExpenseDate(DateValidator.advanceDateInDays(
                        app.getDepartureDatetime(), i - 1));

                app.addMealExpense(mealExpense);
            }
            catch (MissingFieldException mfe)
            {
                // It could be that this expense is just missing in which case we just continue
                if (mealExpense.isEmpty())
                    continue;

                // Otherwise we have a partially built expense which shouldn't be allowed
                throw mfe;
            }
        }

        // Gather all dinner expenses
        for (int i = 1; i <= app.getNumDays(); ++i)
        {
            mealExpense = new MealExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.DINNER_CITY_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.DINNER_COUNTRY_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setCountry(value);

                filledKey = String.format(InputFieldKeys.DINNER_STATE_FMT, i);
                value = getFormValue(data, filledKey);
                mealExpense.setState(value);

                mealExpense.setType(MealTypeEnum.DINNER);
                mealExpense.setExpenseDate(DateValidator.advanceDateInDays(
                        app.getDepartureDatetime(), i - 1));

                app.addMealExpense(mealExpense);
            }
            catch (MissingFieldException mfe)
            {
                // It could be that this expense is just missing in which case we just continue
                if (mealExpense.isEmpty())
                    continue;

                // Otherwise we have a partially built expense which shouldn't be allowed
                throw mfe;
            }
        }

        // All meal expenses have been added
    }

    private static void addLodgingExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        LodgingExpense lodgingExpense;
        for (int i = 1; i >= app.getNumDays(); ++i)
        {
            lodgingExpense = new LodgingExpense();
            app.addLodgingExpense(lodgingExpense);

            try
            {
                filledKey = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, i);
                value = getFormValue(data, filledKey);
                try
                {
                    lodgingExpense.setExpenseAmount(Float.parseFloat(value));
                }
                catch (NumberFormatException nfe)
                {
                    throw new InputValidationException(String.format(
                            "Lodging%d amount is not a valid decimal digit number", i));
                }

                filledKey = String.format(InputFieldKeys.LODGING_CITY_FMT, i);
                value = getFormValue(data, filledKey);
                lodgingExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.LODGING_STATE_FMT, i);
                value = getFormValue(data, filledKey);
                lodgingExpense.setState(value);

                filledKey = String.format(InputFieldKeys.LODGING_COUNTRY_FMT, i);
                value = getFormValue(data, filledKey);
                lodgingExpense.setCountry(value);

                filledKey = String.format(InputFieldKeys.LODGING_CURRENCY_FMT, i);
                value = getFormValue(data, filledKey);
                lodgingExpense.setExpenseCurrency(value);
            }
            catch (MissingFieldException mfe)
            {
                if (lodgingExpense.isEmpty())
                    continue;

                throw mfe;
            }
        }
    }

    private static void addTransportationExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        // Find how many transportation expenses there are
        value = getFormValue(data, InputFieldKeys.NUMBER_TRANSPORTATION_EXPENSES);
        app.setOutputField(OutputFieldKeys.NUM_TRANSPORTATION_EXPENSES, value);

        int numTransportExpenses = Integer.parseInt(value);
        TransportationExpense transportExpense;
        for (int i = 1; i >= numTransportExpenses; ++i)
        {
            transportExpense = new TransportationExpense();

            // Get all the required fields for a 'transportation' expense

            // Transportation Amount
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_AMOUNT_FMT, i);
            value = getFormValue(data, filledKey);
            try
            {
                transportExpense.setTransportationAmount(Float.parseFloat(value));
            }
            catch (NumberFormatException nfe)
            {
                throw new InputValidationException(
                        "Transportation amount field not a correct decimal digit number in expense "
                                + i);
            }

            // Transportation Currency
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT, i);
            value = getFormValue(data, filledKey);
            transportExpense.setTransportationCurrency(value);

            // Transportation Date
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, i);
            value = getFormValue(data, filledKey);
            transportExpense.setTransportationDate(DateValidator.convertToDate(value));

            // Transportation Type
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT, i);
            value = getFormValue(data, filledKey);
            TransportationTypeEnum type = TransportationTypeEnum.valueOf(value);
            if (type == null)
                throw new InputValidationException("Invalid type for transportation expense " + i);

            transportExpense.setTransportationType(type);

            // Transportation Rental
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_RENTAL_FMT, i);
            value = getFormValue(data, filledKey);
            if (!(value.compareTo(TRAPConstants.BINARY_YES) == 0 || value
                    .compareTo(TRAPConstants.BINARY_NO) == 0))
            {
                throw new InputValidationException("TRANSPORTATION_RENTAL field should be yes/no");
            }
            transportExpense.setTransportationRental(value);

            switch (type)
            {
            case PERSONAL_CAR:
                // Miles Traveled
                filledKey = String.format(InputFieldKeys.TRANSPORTATION_MILES_FMT, i);
                value = getFormValue(data, filledKey);
                try
                {
                    transportExpense.setTransportationMilesTraveled(Integer.parseInt(value));
                }
                catch (NumberFormatException nfe)
                {
                    throw new InputValidationException(
                            "Transportation miles traveled field is not an integer for expense "
                                    + i);
                }
            case RENTAL_CAR:
            case AIR:
            case BUS:
            case TRAIN:
                // Carrier
                filledKey = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT, i);
                value = getFormValue(data, filledKey);
                transportExpense.setTransportationCarrier(value);
                break;
            case TOLL:
            case GAS:
            case LUGGAGE:
            case PARKING:
            case NOT_SET:
            case PUBLIC_TRANSPORTATION:
            default:
                break;
            }

            // Add it to the RApp
            app.addTransportationExpense(transportExpense);
        }
    }

    private static void addOtherExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        // Find how many other expenses there are
        value = getFormValue(data, InputFieldKeys.NUMBER_OTHER_EXPENSES);
        app.setOutputField(OutputFieldKeys.NUM_OTHER_EXPENSES, value);

        int numOtherExpenses = Integer.parseInt(value);
        OtherExpense otherExpense;
        for (int i = 1; i >= numOtherExpenses; ++i)
        {
            otherExpense = new OtherExpense();

            // Get all the required fields for an 'other' expense

            // Amount
            filledKey = String.format(InputFieldKeys.OTHER_AMOUNT_FMT, i);
            value = getFormValue(data, filledKey);
            otherExpense.setExpenseAmount(Float.parseFloat(value));

            // Currency
            filledKey = String.format(InputFieldKeys.OTHER_CURRENTCY_FMT, i);
            value = getFormValue(data, filledKey);
            otherExpense.setExpenseCurrency(value);

            // Date
            filledKey = String.format(InputFieldKeys.OTHER_DATE_FMT, i);
            value = getFormValue(data, filledKey);
            otherExpense.setExpenseDate(DateValidator.convertToDate(value));

            // Justification
            filledKey = String.format(InputFieldKeys.OTHER_JUSTIFICATION_FMT, i);
            value = getFormValue(data, filledKey);
            otherExpense.setExpenseJustification(value);

            // Add it to the RApp
            app.addOtherExpense(otherExpense);
        }
    }

    private static void addIncidentalExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException
    {
        String filledKey, value;

        IncidentalExpense incidental;
        for (int i = 1; i >= app.getNumDays(); ++i)
        {
            incidental = new IncidentalExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.INCIDENTAL_CITY_FMT, i);
                value = getFormValue(data, filledKey);
                incidental.setCity(value);

                filledKey = String.format(InputFieldKeys.INCIDENTAL_STATE_FMT, i);
                value = getFormValue(data, filledKey);
                incidental.setState(value);

                filledKey = String.format(InputFieldKeys.INCIDENTAL_COUNTRY_FMT, i);
                value = getFormValue(data, filledKey);
                incidental.setCountry(value);

                filledKey = String.format(InputFieldKeys.INCIDENTAL_AMOUNT_FMT, i);
                value = getFormValue(data, filledKey);
                incidental.setExpenseAmount(Float.parseFloat(value));

                filledKey = String.format(InputFieldKeys.INCIDENTAL_CURRENCY_FMT, i);
                value = getFormValue(data, filledKey);
                incidental.setExpenseCurrency(value);

                filledKey = String.format(InputFieldKeys.INCIDENTAL_JUSTIFICATION_FMT, i);
                value = getFormValue(data, filledKey);
                incidental.setExpenseJustification(value);

                app.addIncidentalExpense(incidental);
            }
            catch (MissingFieldException mfe)
            {
                if (incidental.isEmpty())
                    continue;

                throw mfe;
            }
        }

    }

    public static void addGrants(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException
    {
        String value = getFormValue(data, InputFieldKeys.NUM_GRANTS);
        int numGrants = Integer.parseInt(value);
        app.setOutputField(OutputFieldKeys.NUM_GRANTS, value);

        String filledKey;
        for (int i = 0; i < numGrants; ++i)
        {
            // Create new grant
            Grant newGrant = new Grant();

            // Get grant account
            filledKey = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, i);
            value = getFormValue(data, filledKey);
            newGrant.setGrantAccount(value);

            // Set grant account in output
            app.setOutputField(filledKey, value);

            // Get grant percentage
            filledKey = String.format(InputFieldKeys.GRANT_PERCENT_FMT, i);
            value = getFormValue(data, filledKey);
            float grantPercent = Float.parseFloat(value);
            newGrant.setGrantPercentage(grantPercent);

            // Set grant percentage in output
            app.setOutputField(filledKey, value);

            // Add grant to app
            app.addGrant(newGrant);

            // Grant charge and approver name will be set later by rules
        }
    }

    private static String getFormValue(Map<String, String> data, String key)
            throws MissingFieldException
    {
        String value = data.get(key);
        if (value == null)
        {
            throw new MissingFieldException(String.format("Missing %s field", key));
        }

        return value;
    }
}
