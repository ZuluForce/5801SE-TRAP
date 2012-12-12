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
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.rules.input.DateValidator;

/**
 * The FormDataConverter converts raw input data maps into a easier to use ReimbursementApp object
 * for further processing within TRAP.
 * 
 * @author andrewh
 * 
 */
public class FormDataConverter
{
    /**
     * A logger instance for the FormDataConverter
     */
    private static Logger log = LoggerFactory.getLogger(FormDataConverter.class);

    /**
     * Takes an map of raw form data and converts it to a ReimbursementApp object that can be used
     * within TRAP for processing. The purpose of this conversion is not to check rules but to some
     * extent it is necessary since we must know whether we can construct a complete object
     * hierarchy.
     * 
     * @param data - The input data map. This is the saved form data that has been requested for
     *            submission.
     * @return - A ReimbursementApp object that is a representation of the raw input data. This
     *         object can be used by the TRAPRuleRegistry for processing.
     * @throws TRAPException - For several reasons including missing fields, invalid usernames, or
     *             invalid number formats.
     */
    public static ReimbursementApp formToReimbursementApp(Map<String, String> data)
            throws TRAPException
    {
        ReimbursementApp app = new ReimbursementApp();

        String value;

        log.info("Started form data conversion: {}", new Date());

        // Start with datetimes
        value = getFormValue(data, InputFieldKeys.ARRIVAL_DATETIME);
        app.setArrivalDatetime(DateValidator.convertToDatetime(value));
        app.setOutputField(OutputFieldKeys.ARRIVAL_DATETIME, value);

        log.info("Trip Arrival Datetime: {}", DateValidator.convertToDatetime(value));

        value = getFormValue(data, InputFieldKeys.DEPARTURE_DATETIME);
        app.setDepartureDatetime(DateValidator.convertToDatetime(value));
        app.setOutputField(OutputFieldKeys.DEPARTURE_DATETIME, value);

        log.info("Trip Departure Datetime: {}", DateValidator.convertToDatetime(value));

        // Build/Add the UserInfo data object
        addUserInfo(app, data);

        // Build/Add the ConferenceInfo data object
        addConferenceInfo(app, data);

        // Num Days
        value = getFormValue(data, InputFieldKeys.NUM_DAYS);
        int numDays = Integer.parseInt(value);
        app.setNumDays(numDays);
        app.setOutputField(OutputFieldKeys.NUM_DAYS, value);

        // Add all meal expenses
        addMealExpenses(app, data);

        // Add all lodging expenses
        addLodgingExpenses(app, data);

        // Add all transportation expenses
        addTransportationExpenses(app, data);

        // Add all incidental expenses
        addIncidentalExpenses(app, data);

        // Add all other expenses
        addOtherExpenses(app, data);

        // Add grants
        addGrants(app, data);

        // Set form submission time to now
        String datetimeNow = DateValidator.datetimeToString(new Date());
        app.setOutputField(OutputFieldKeys.FORM_SUBMISSION_DATETIME, datetimeNow);

        return app;
    }

    /**
     * Add all information related to the user as a UserInfo object.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required field related to conference information is
     *             not present.
     * @throws InputValidationException - When the user isn't found in the database.
     */
    private static void addUserInfo(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String username, value;

        UserInfo userInfo = new UserInfo();

        log.info("Collection user information");

        // Username
        username = getFormValue(data, InputFieldKeys.USER_NAME);
        userInfo.setUsername(username);
        app.setOutputField(OutputFieldKeys.USER_NAME, username);

        // Emergency contact name/phone
        value = getFormValue(data, InputFieldKeys.EMERGENCY_NAME);
        userInfo.setEmergencyContactName(value);
        app.setOutputField(OutputFieldKeys.EMERGENCY_NAME, value);

        value = getFormValue(data, InputFieldKeys.EMERGENCY_PHONE);
        userInfo.setEmergencycontactPhone(value);
        app.setOutputField(OutputFieldKeys.EMERGENCY_PHONE, value);

        // This is information which is obtained from the db using the input username
        List<String> extraUserInfo = null;
        try
        {
            extraUserInfo = UserDBWrapper.getUserInfo(username);
        }
        catch (KeyNotFoundException e)
        {
            throw new InputValidationException("Invalid username");
        }

        // (Input) Email Address
        value = extraUserInfo.get(UserDB.USER_FIELDS.EMAIL.ordinal());
        userInfo.setEmailAddress(value);
        app.setOutputField(OutputFieldKeys.EMAIL, value);

        // Add UserInfo object to RApp
        app.setUserInfo(userInfo);

        // (Output) Full Name
        value = extraUserInfo.get(UserDB.USER_FIELDS.FULL_NAME.ordinal());
        userInfo.setFullName(value);
        app.setOutputField(OutputFieldKeys.FULL_NAME, value);

        // (Output) Citizenship
        value = extraUserInfo.get(UserDB.USER_FIELDS.CITIZENSHIP.ordinal());
        userInfo.setCitizenship(value);
        app.setOutputField(OutputFieldKeys.CITIZENSHIP, value);

        // (Output) Visa Status
        if (userInfo.getCitizenship().compareToIgnoreCase("United States") != 0)
        {
            value = extraUserInfo.get(UserDB.USER_FIELDS.VISA_STATUS.ordinal());
            userInfo.setVisaStatus(value);
            app.setOutputField(OutputFieldKeys.VISA_STATUS, value);
        }

        // (Output) Paid by University
        value = extraUserInfo.get(UserDB.USER_FIELDS.PAID_BY_UNIVERSITY.ordinal());
        userInfo.setPaidByUniversity(value);
        app.setOutputField(OutputFieldKeys.PAID_BY_UNIVERSITY, value);
    }

    /**
     * Add all information related to the conference attended as a ConferenceInfo object.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required field related to conference information is
     *             not present.
     */
    private static void addConferenceInfo(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException
    {
        ConferenceInfo conferenceInfo = new ConferenceInfo();
        String value;

        log.info("Building conference information");

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

    /**
     * Add all meal expenses from the input map as MealExpense objects.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required meal expense related field is missing.
     * @throws InputValidationException - When more than 3 meal expenses are claimed on a given day
     */
    private static void addMealExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        MealExpense mealExpense;

        // First gather all breakfast expenses
        for (int day = 1; day <= app.getNumDays(); ++day)
        {
            mealExpense = new MealExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.BREAKFAST_CITY_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                mealExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.BREAKFAST_STATE_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                mealExpense.setState(value);

                filledKey = String.format(InputFieldKeys.BREAKFAST_COUNTRY_FMT, day);
                value = getFormValue(data, filledKey);
                mealExpense.setCountry(value);

                mealExpense.setType(MealTypeEnum.BREAKFAST);

                // Set the date for this expense relative to the departure date of the trip
                mealExpense.setExpenseDate(DateValidator.advanceDateInDays(
                        app.getDepartureDatetime(), day - 1));

                app.addMealExpense(mealExpense, day);
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
        for (int day = 1; day <= app.getNumDays(); ++day)
        {
            mealExpense = new MealExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.LUNCH_CITY_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                mealExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.LUNCH_STATE_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                mealExpense.setState(value);

                filledKey = String.format(InputFieldKeys.LUNCH_COUNTRY_FMT, day);
                value = getFormValue(data, filledKey);
                mealExpense.setCountry(value);

                mealExpense.setType(MealTypeEnum.LUNCH);
                mealExpense.setExpenseDate(DateValidator.advanceDateInDays(
                        app.getDepartureDatetime(), day - 1));

                app.addMealExpense(mealExpense, day);
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
        for (int day = 1; day <= app.getNumDays(); ++day)
        {
            mealExpense = new MealExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.DINNER_CITY_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                mealExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.DINNER_STATE_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                mealExpense.setState(value);

                filledKey = String.format(InputFieldKeys.DINNER_COUNTRY_FMT, day);
                value = getFormValue(data, filledKey);
                mealExpense.setCountry(value);

                mealExpense.setType(MealTypeEnum.DINNER);
                mealExpense.setExpenseDate(DateValidator.advanceDateInDays(
                        app.getDepartureDatetime(), day - 1));

                app.addMealExpense(mealExpense, day);
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
        log.info("Added {} meal expenses to app", app.getMealExpenseList().size());
    }

    /**
     * Add all lodging expenses from the input map as LodgingExpense objects.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required lodging expense related field is missing.
     * @throws InputValidationException - When a numerical input value is not formatted correctly.
     */
    private static void addLodgingExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        LodgingExpense lodgingExpense;
        for (int day = 1; day <= app.getNumDays(); ++day)
        {
            lodgingExpense = new LodgingExpense();

            try
            {
                filledKey = String.format(InputFieldKeys.LODGING_CITY_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                lodgingExpense.setCity(value);

                filledKey = String.format(InputFieldKeys.LODGING_STATE_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                lodgingExpense.setState(value);

                filledKey = String.format(InputFieldKeys.LODGING_COUNTRY_FMT, day);
                value = getFormValue(data, filledKey);
                lodgingExpense.setCountry(value);

                filledKey = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, day);
                value = getFormValue(data, filledKey);
                try
                {
                    lodgingExpense.setExpenseAmount(Double.parseDouble(value));
                }
                catch (NumberFormatException nfe)
                {
                    throw new InputValidationException(String.format(
                            "Lodging%d amount is not a real number", day));
                }

                filledKey = String.format(InputFieldKeys.LODGING_CURRENCY_FMT, day);
                value = getFormValue(data, filledKey);
                lodgingExpense.setExpenseCurrency(value);

                Date expenseDate = app.getDepartureDatetime();
                lodgingExpense
                        .setExpenseDate(DateValidator.advanceDateInDays(expenseDate, day - 1));

                // Convert to USD
                CurrencyConverter.convertExpenseCurrency(lodgingExpense);

                app.addLodgingExpense(lodgingExpense, day);
            }
            catch (MissingFieldException mfe)
            {
                if (lodgingExpense.isEmpty())
                    continue;

                throw mfe;
            }
        }

        log.info("Added {} lodging expenses to app", app.getLodgingExpenseList().size());
    }

    /**
     * Add all transportation expenses from the input map as TransportationExpense objects.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required transportation expense related field is
     *             missing.
     * @throws InputValidationException - When a numerical input value is not formatted correctly.
     */
    private static void addTransportationExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        // Find how many transportation expenses there are
        value = getFormValue(data, InputFieldKeys.NUMBER_TRANSPORTATION_EXPENSES);
        app.setOutputField(OutputFieldKeys.NUM_TRANSPORTATION_EXPENSES, value);

        int numTransportExpenses = Integer.parseInt(value);
        TransportationExpense transportExpense;
        for (int i = 1; i <= numTransportExpenses; ++i)
        {
            transportExpense = new TransportationExpense();

            // Get all the required fields for a 'transportation' expense

            // Transportation Amount
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_AMOUNT_FMT, i);
            value = getNonRequiredFormValue(data, filledKey);
            try
            {
                if (value != null && !value.equals(""))
                {
                    transportExpense.setExpenseAmount(Double.parseDouble(value));
                }
                else
                {
                    transportExpense.setExpenseAmount(0.0);
                }
            }
            catch (NumberFormatException nfe)
            {
                throw new InputValidationException(
                        "Transportation amount field not a real number in expense " + i);
            }

            // Transportation Currency
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT, i);
            value = getNonRequiredFormValue(data, filledKey);
            transportExpense.setExpenseCurrency(value);

            // Transportation Date
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, i);
            value = getFormValue(data, filledKey);
            transportExpense.setTransportationDate(DateValidator.convertToDate(value));

            // Transportation Type
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT, i);
            value = getFormValue(data, filledKey);
            TransportationTypeEnum type = TransportationTypeEnum.valueOf(value.toUpperCase());
            if (type == null)
                throw new InputValidationException("Invalid type for transportation expense " + i);

            transportExpense.setTransportationType(type);

            // Transportation Rental
            filledKey = String.format(InputFieldKeys.TRANSPORTATION_RENTAL_FMT, i);
            try
            {
                value = getFormValue(data, filledKey);
            }
            catch (MissingFieldException mfe)
            {
                value = "no";
            }
            checkYesNo(value, filledKey);
            transportExpense.setTransportationRental(value);

            switch (type)
            {
            case CAR:
                // Don't count mileage for rental car
                if (transportExpense.getTransportationRental().compareToIgnoreCase(
                        TRAPConstants.STR_YES) != 0)
                {

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

                    break;
                }
                // Continue since the rental car needs a carrier
            case AIR:
            case RAIL:
                // Carrier
                filledKey = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT, i);
                value = getFormValue(data, filledKey);
                transportExpense.setTransportationCarrier(value);
                break;
            case TOLL:
            case GAS:
            case BAGGAGE:
            case PARKING:
            case NOT_SET:
            case PUBLIC_TRANSPORTATION:
            default:
                break;
            }

            // Convert to USD
            CurrencyConverter.convertExpenseCurrency(transportExpense);

            // Add it to the RApp
            app.addTransportationExpense(transportExpense);
        }

        log.info("Added {} transportation expenses", app.getTransportationExpenseList().size());
    }

    /**
     * Add all other expenses from the input map as OtherExpense objects.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required other expense related field is missing.
     * @throws InputValidationException - When a numerical input value is not formatted correctly.
     */
    private static void addOtherExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        // Find how many other expenses there are
        value = getFormValue(data, InputFieldKeys.NUMBER_OTHER_EXPENSES);
        app.setOutputField(OutputFieldKeys.NUM_OTHER_EXPENSES, value);

        int numOtherExpenses = Integer.parseInt(value);
        OtherExpense otherExpense;
        for (int i = 1; i <= numOtherExpenses; ++i)
        {
            otherExpense = new OtherExpense();

            // Get all the required fields for an 'other' expense

            // Amount
            filledKey = String.format(InputFieldKeys.OTHER_AMOUNT_FMT, i);
            value = getFormValue(data, filledKey);
            try
            {
                otherExpense.setExpenseAmount(Double.parseDouble(value));
            }
            catch (NumberFormatException nfe)
            {
                throw new InputValidationException(String.format(
                        "Other expense %d amount not formatted correctly", i));
            }

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

            // Convert to USD
            CurrencyConverter.convertExpenseCurrency(otherExpense);

            // Add it to the RApp
            app.addOtherExpense(otherExpense);
        }

        log.info("Added {} other expenses to the app", app.getOtherExpenseList().size());
    }

    /**
     * Add all incidental expenses from the input map as IncidentalExpense objects.
     * 
     * @param app - The ReimbursementApp that is being constructed.
     * @param data - The input data for the form that is being submitted.
     * @throws MissingFieldException - When a required incidental expense related field is missing.
     * @throws InputValidationException - When a numerical input value is not formatted correctly.
     */
    private static void addIncidentalExpenses(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        String filledKey, value;

        IncidentalExpense incidental;
        for (int day = 1; day <= app.getNumDays(); ++day)
        {
            incidental = new IncidentalExpense();

            try
            {
                // City
                filledKey = String.format(InputFieldKeys.INCIDENTAL_CITY_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                incidental.setCity(value);

                // State
                filledKey = String.format(InputFieldKeys.INCIDENTAL_STATE_FMT, day);
                value = getNonRequiredFormValue(data, filledKey);
                incidental.setState(value);

                // Country
                filledKey = String.format(InputFieldKeys.INCIDENTAL_COUNTRY_FMT, day);
                value = getFormValue(data, filledKey);
                incidental.setCountry(value);

                // Expense amount
                filledKey = String.format(InputFieldKeys.INCIDENTAL_AMOUNT_FMT, day);
                value = getFormValue(data, filledKey);
                try
                {
                    incidental.setExpenseAmount(Double.parseDouble(value));
                }
                catch (NumberFormatException nfe)
                {
                    throw new InputValidationException(String.format(
                            "Incidental expense %d amount not formatted correctly", day));
                }

                // Currency
                filledKey = String.format(InputFieldKeys.INCIDENTAL_CURRENCY_FMT, day);
                value = getFormValue(data, filledKey);
                incidental.setExpenseCurrency(value);

                // Justification
                filledKey = String.format(InputFieldKeys.INCIDENTAL_JUSTIFICATION_FMT, day);
                value = getFormValue(data, filledKey);
                incidental.setExpenseJustification(value);

                // Date
                Date expenseDate = app.getDepartureDatetime();
                incidental.setExpenseDate(DateValidator.advanceDateInDays(expenseDate, day - 1));

                // Convert to USD
                CurrencyConverter.convertExpenseCurrency(incidental);

                // Add to App
                app.addIncidentalExpense(incidental, day);
            }
            catch (MissingFieldException mfe)
            {
                // This means that the first field missed and there just is not incidental for this
                // day.
                if (incidental.isEmpty())
                    continue;

                // This means it is a partial expense which is an error
                throw mfe;
            }
        }

        log.info("Added {} incidental expenses to app", app.getIncidentalExpenseList().size());
    }

    /**
     * Add all grants and their information to the app as Grant objects.
     * 
     * @param app - The ReimbursementApp being constructed.
     * @param data - The input data map.
     * @throws MissingFieldException - When a required field related to a grant is not present in
     *             the input data map.
     * @throws InputValidationException - When the number format for a grant's percentage field is
     *             not in a proper format.
     */
    private static void addGrants(ReimbursementApp app, Map<String, String> data)
            throws MissingFieldException, InputValidationException
    {
        // Find number of grants and set output field
        String value = getFormValue(data, InputFieldKeys.NUM_GRANTS);
        int numGrants = Integer.parseInt(value);
        app.setOutputField(OutputFieldKeys.NUM_GRANTS, value);

        String filledKey;
        for (int i = 1; i <= numGrants; ++i)
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
            try
            {
                Integer grantPercent = Integer.parseInt(value);
                newGrant.setGrantPercentage(grantPercent);
            }
            catch (NumberFormatException nfe)
            {
                throw new InputValidationException(String.format(
                        "Percentage for grant %d not an integer", i));
            }

            // Set grant percentage in output
            app.setOutputField(filledKey, value);

            // Add grant to app
            app.addGrant(newGrant);

            // Grant charge and approver name will be set later by rules
        }

        log.info("Added {} grants to the app", app.getGrantList().size());
    }

    /**
     * Check if the given string represents a valid yes/no value. This simply checks that value is
     * either "yes" or "no" but it could be expanded in the future.
     * 
     * @param value - string to check for yes/no
     * @throws InputValidationException - If the value isn't yes/no
     */
    private static void checkYesNo(String value, String keyName) throws InputValidationException
    {
        boolean valid = (value.compareToIgnoreCase(TRAPConstants.STR_YES) == 0);
        valid = valid || (value.compareToIgnoreCase(TRAPConstants.STR_NO) == 0);

        if (!valid)
        {
            throw new InputValidationException(keyName + " field should be yes/no");
        }
    }

    /**
     * Gets a given key from the data map. If the key isn't found or the value is null it generates
     * an exception.
     * 
     * @param data - The data map to extract the key/value from
     * @param key - The key whose value you want to get
     * @return - The value stored in the data map under the given key
     * @throws MissingFieldException - When the key is not present in the data map.
     */
    private static String getFormValue(Map<String, String> data, String key)
            throws MissingFieldException
    {
        String value = data.get(key);
        if (value == null || value.compareTo("") == 0)
        {
            throw new MissingFieldException(String.format("Missing %s field", key));
        }

        return value;
    }

    /**
     * Gets a key from the data map. If the key isn't present or is the empty string then the empty
     * string is returned
     * 
     * @param data - The form data to check in for the key
     * @param key - The key to get out of the data map
     * @return - The value associated with the key in the data map or null if the key is not present
     *         or is the empty string.
     */
    private static String getNonRequiredFormValue(Map<String, String> data, String key)
    {
        String value = data.get(key);
        if (value == null)
            value = "";

        return value;
    }
}
