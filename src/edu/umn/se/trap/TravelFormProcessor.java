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
package edu.umn.se.trap;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.db.CurrencyDB;
import edu.umn.se.trap.db.CurrencyDBWrapper;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.PerDiemDB;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.db.UserDB;
import edu.umn.se.trap.db.UserDBWrapper;
import edu.umn.se.trap.db.UserGrantDB;
import edu.umn.se.trap.db.UserGrantDBWrapper;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.form.AllUserForms;
import edu.umn.se.trap.form.FormDataConverter;
import edu.umn.se.trap.rules.TRAPRuleRegistry;

/**
 * The main class for TRAP that implements the interface for communication with external systems
 * such as the driver.
 * 
 * @author andrewh
 * 
 */
public class TravelFormProcessor implements TravelFormProcessorIntf
{
    /** Logger for the TravelFormProcessor class */
    private static Logger log = LoggerFactory.getLogger(TravelFormProcessor.class);

    /** main object for the form storage sub-module */
    private final AllUserForms formStorage;

    /** main object for the TRAP rule/processing sub-module */
    private final TRAPRuleRegistry ruleRegistry;

    /** the current user in the TRAP system */
    private String user;

    /**
     * Constructor -- uses parameters to allow for unit testing.
     */

    /**
     * Construct the TravelFormProcessor object. This creates and initializes object for the main
     * sub-systems, form storage and application rule processing.
     * 
     * @param userDB the user database table abstraction.
     * @param perDiemDB the perDiem DB table abstraction.
     * @param grantDB the grant DB table abstraction.
     * @param userGrantDB the user to grant DB table abstraction.
     * @param currencyDB the table abstraction for the currency DB.
     * 
     * @see AllUserForms
     * @see TRAPRuleRegistry
     */
    public TravelFormProcessor(UserDB userDB, PerDiemDB perDiemDB, GrantDB grantDB,
            UserGrantDB userGrantDB, CurrencyDB currencyDB)
    {
        // Initialize all database wrappers
        UserDBWrapper.setUserDB(userDB);
        PerDiemDBWrapper.setPerDiemDB(perDiemDB);
        GrantDBWrapper.setGrantDB(grantDB);
        UserGrantDBWrapper.setUserGrantDB(userGrantDB);
        CurrencyDBWrapper.setCurrencyDB(currencyDB);

        formStorage = new AllUserForms();
        ruleRegistry = new TRAPRuleRegistry();
        user = null;
    }

    /**
     * Clear all saved forms for the current user.
     * 
     * @see AllUserForms
     * @throws TRAPException - when the current user is not set
     */
    @Override
    public void clearSavedForms() throws TRAPException
    {
        try
        {
            checkUserSet();

            log.info("Clearing saved forms for {}", user);
            formStorage.clearSavedForms(user);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Get the data for a completed form for the current user.
     * 
     * @param id - The id of the completed form to retrieve
     * @throws TRAPException - The current user is not set or no completed form with the given id
     *             could be found.
     */
    @Override
    public Map<String, String> getCompletedForm(Integer id) throws TRAPException
    {
        try
        {
            checkUserSet();
            log.info("Fetching completed formId {} for {}", id, user);
            return formStorage.getCompletedForm(user, id);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Get the current user's saved form data that is stored under the given id.
     * 
     * @see AllUserForms
     * @param id - The id for the form to retrieve
     * @throws TRAPException - Either the user isn't set or the given form id is invalid for the
     *             current user
     */
    @Override
    public Map<String, String> getSavedFormData(Integer id) throws TRAPException
    {
        try
        {
            checkUserSet();

            log.info("Fetching saved form data {} for {}", id, user);
            return formStorage.getSavedFormData(user, id);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Get a map of all form id's to a metadata object about them. This metadata contains a
     * description of the form and its status (submitted or draft).
     * 
     * @return - The map of form id's to form metadata for the current user
     * @throws TRAPException - If the current user isn't set or the form storage raises an error.
     */
    @Override
    public Map<Integer, TravelFormMetadata> getSavedForms() throws TRAPException
    {
        try
        {
            checkUserSet();

            log.info("Fetching saved form info for {}", user);
            return formStorage.getSavedForms(user);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Get the value of the currently set user.
     * 
     * @return - The current user string. If the user hasn't been set this will be null.
     */
    @Override
    public String getUser()
    {
        return user;
    }

    /**
     * Save the given form data (map) with the provided description. Saving with a description will
     * cause a new formId to be generated for the formData. It will be saved under the current user.
     * 
     * @param formData - The map of form data to save
     * @param desc - The description to save with the form data
     */
    @Override
    public Integer saveFormData(Map<String, String> formData, String desc) throws TRAPException
    {
        try
        {
            checkUserSet();

            log.info("Saving form data for {} with a description", user);
            return formStorage.saveFormData(user, formData, desc);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Save the given form data (map) under this id for the current user. The provided id must
     * already be present in form storage for the current user to save with this method.
     * 
     * @param formData - The map of form data to save
     * @param id - The id to save the form data with
     * @throws TRAPException - If the current user isn't set or the form storage doesn't have a
     *             previously saved form with the given id.
     */
    @Override
    public Integer saveFormData(Map<String, String> formData, Integer id) throws TRAPException
    {
        try
        {
            checkUserSet();

            log.info("Saving form data for {} under form id {}", user, id);
            return formStorage.saveFormData(user, formData, id);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Set the current user for the processor (TRAP)
     * 
     * @param user - The value to set as the current user.
     * @throws TRAPException - If the given user is not valid.
     */
    @Override
    public void setUser(String user) throws TRAPException
    {
        try
        {
            if (!UserDBWrapper.isValidUser(user))
            {
                throw new FormProcessorException(String.format("username '%s' is invalid", user));
            }

            log.info("Setting current user to {}", user);
            this.user = user;
            formStorage.addUser(user);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Submit the current user's saved form data under the given id for processing.
     * 
     * @see AllUserForms
     * @see TRAPRuleRegistry
     * @see FormDataConverter
     * @see ReimbursementApp
     * @param id - the id of the saved form to submit
     * @throws TRAPException - When the form submission/processing fails
     */
    @Override
    public void submitFormData(Integer id) throws TRAPException
    {
        try
        {
            checkUserSet();

            log.info("Starting submission process for form {}", id);

            Map<String, String> data = formStorage.getSavedFormData(user, id);

            ReimbursementApp app = FormDataConverter.formToReimbursementApp(data);

            String userOnForm = app.getUserInfo().getUsername();
            if (userOnForm.compareToIgnoreCase(user) != 0)
            {
                throw new FormProcessorException(String.format(
                        "Username %s on submitted form does not match current user %s", userOnForm,
                        user));
            }

            ruleRegistry.processApp(app);

            formStorage.saveCompletedForm(user, app.getOutputFields(), id);

            log.info("Completed submission and processing of form {}. Ouput saved.", id);
        }
        catch (RuntimeException re)
        {
            throw new TRAPRuntimeException(re);
        }
    }

    /**
     * Simple method to check if the user is set and otherwise throw an exception. This method is
     * for convenience since a lot of other methods need to check this before they proceed.
     * 
     * @throws FormProcessorException - If the user value is null (not set)
     */
    private void checkUserSet() throws FormProcessorException
    {
        if (user == null)
            throw new FormProcessorException("No user set in the TravelFormProcessor");
    }
}
