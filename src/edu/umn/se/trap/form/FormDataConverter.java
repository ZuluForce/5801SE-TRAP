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

import java.util.List;
import java.util.Map;

import edu.umn.se.trap.data.ConferenceInfo;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.UserInfo;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.UserDB;
import edu.umn.se.trap.db.UserDBWrapper;
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
    public static ReimbursementApp formToReimbursementApp(Map<String, String> data)
            throws TRAPException
    {
        // TODO: Write this method
        ReimbursementApp app = new ReimbursementApp();

        String value;

        // (Input) Start with datetimes
        value = getFormValue(data, InputFieldKeys.ARRIVAL_DATETIME);
        app.setArrivalDatetime(DateValidator.convertToDatetime(value));

        value = getFormValue(data, InputFieldKeys.DEPARTURE_DATETIME);
        app.setDepartureDatetime(DateValidator.convertToDatetime(value));

        // (Input/Output) Build/Add the UserInfo data object
        addUserInfo(app, data);

        // (Input/Output) Build/Add the ConferenceInfo data object
        addConferenceInfo(app, data);

        // (Input/Output) Add all meal expenses

        // Set form submission time to now
        // app.setOutputField(OutputFieldKeys.FORM_SUBMISSION_DATETIME, value);

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
            ; // Pass. We just need a justification for sponsored or non-sponsored
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
            // Otherwise continue since we have one. If this justification was needed it will have
            // to be
            // checked by a rule later.
        }

        // Add conferenceInfo to app
        app.setConferenceInfo(conferenceInfo);
    }

    public void addMealExpenses(ReimbursementApp app, Map<String, String> data)
    {
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
