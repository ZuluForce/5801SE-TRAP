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

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.db.UserDBWrapper;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InvalidUsernameException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.AllUserForms;
import edu.umn.se.trap.form.FormDataConverter;
import edu.umn.se.trap.rules.TRAPRuleRegistry;

/**
 * The main class for TRAP that implements the interface for communication with external systems
 * such as the driver.
 * 
 * @author planeman
 * 
 */
public class TravelFormProcessor implements TravelFormProcessorIntf
{
    /** main object for the form storage sub-module */
    private final AllUserForms formStorage;

    /** main object for the TRAP rule/processing sub-module */
    private final TRAPRuleRegistry ruleRegistry;

    /** the current user in the TRAP system */
    private String user;

    /**
     * Construct the TravelFormProcessor object. This creates and initializes object for the main
     * sub-systems, form storage and application rule processing.
     * 
     * @see AllUserForms
     * @see TRAPRuleRegistry
     */
    public TravelFormProcessor()
    {
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
        checkUserSet();
        formStorage.clearSavedForms(user);
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
        checkUserSet();
        return formStorage.getCompletedForm(user, id);
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
        checkUserSet();
        return formStorage.getSavedFormData(user, id);
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
        checkUserSet();
        return formStorage.getSavedForms(user);
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
        checkUserSet();

        return formStorage.saveFormData(user, formData, desc);
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
        checkUserSet();

        return formStorage.saveFormData(user, formData, id);
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
        // TODO: Check that this is a valid user
        if (!UserDBWrapper.isValidUser(user))
        {
            throw new InvalidUsernameException(String.format("username '%s' is invalid", user));
        }

        this.user = user;
        formStorage.addUser(user);
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
        checkUserSet();

        Map<String, String> data = formStorage.getSavedFormData(user, id);

        ReimbursementApp app = FormDataConverter.formToReimbursementApp(data);

        ruleRegistry.processApp(app);

        formStorage.saveCompletedForm(user, app.getOutputFields(), id);

        // TODO: check for any other required steps
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
