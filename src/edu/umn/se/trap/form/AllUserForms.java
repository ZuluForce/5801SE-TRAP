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

import java.util.HashMap;
import java.util.Map;

import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.TravelFormProcessorIntf;
import edu.umn.se.trap.exception.FormStorageException;

/**
 * This class manages all the users forms of the TRAP system. It holds both forms that are in
 * progress and forms that have been successfully processed. While feasible, there should be only
 * one instance of this class.
 * 
 * @author nagell2008
 * 
 */
public class AllUserForms
{
    /**
     * Holds a user's saved forms. See class SavedForms for more information regarding an individual
     * user's forms.
     */
    private final Map<String, SavedForms> usersForms;

    /**
     * Constructor for AllUserForms. Initializes the usersForms hash map; starts out empty.
     */
    public AllUserForms()
    {
        usersForms = new HashMap<String, SavedForms>();
    }

    /**
     * Returns the container holding all of a specific user's forms.
     * 
     * @param user - String of the user id
     * @return Returns a user's saved forms in a SavedForms object.
     * @throws FormStorageException - Error only when a user is not found in the map.
     */
    private SavedForms getUserSavedForms(String user) throws FormStorageException
    {
        /**
         * Check to see if a user already exists in the map.
         */
        if (usersForms.containsKey(user))
        {
            return usersForms.get(user);
        }

        /**
         * Throw an exception when a user is not in the map.
         */
        throw new FormStorageException("User not found in storage");
    }

    /**
     * Adds a user to the map (usersForms) to in order to save forms. If the user already exists
     * nothing will happen.
     * 
     * @param user - String id of the user
     */
    public void addUser(String user)
    {
        // Check to see if a user already exists in the map.
        if (usersForms.containsKey(user))
        {
            /*
             * This is not really an error if a user already exists in form storage. As the user
             * already exists and has a SavedForms object, it is safe to return from here.
             */
            return;
        }

        // The user does not already exist, put them in the map and create a new container.
        usersForms.put(user, new SavedForms());

        return;
    }

    /**
     * Saves a form by id. This will overwrite a form if the id already exists. For consistency, the
     * form id is returned for later use.
     * 
     * @param user - String of the user id
     * @param formData - Map of the form data
     * @param id - Desired form id, as an integer
     * @return - The new form id (as an integer)
     * @throws FormStorageException - Throws an exception when a user is not found in the map.
     */
    public int saveFormData(String user, Map<String, String> formData, int id)
            throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = getUserSavedForms(user);

        // Save the user's form
        tempUserForm.saveForm(formData, id);

        return id;
    }

    /**
     * Save a form with a description. Returns the form id created when the form is inserted.
     * 
     * @param user - String of the user id
     * @param formData - Map of the form data
     * @param desc - String description of the form
     * @return - The new form id (as an integer)
     * @throws FormStorageException - Throws an error when a user is not found in the map.
     */
    public int saveFormData(String user, Map<String, String> formData, String desc)
            throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = getUserSavedForms(user);

        /**
         * Save the form and the new form id that is returned.
         */
        int newId = tempUserForm.saveForm(formData, desc);

        /**
         * Return the new form id for later reference.
         */
        return newId;
    }

    /**
     * Returns a completed form referenced by id. Only returns the form if it has a status of
     * SUBMITTED, otherwise an exception is thrown.
     * 
     * @param user - String id of the user
     * @param id - Desired id of the form to return
     * @return - The form in a map
     * @throws FormStorageException - Throws an error when a user is not found in the map.
     */
    public Map<String, String> getCompletedForm(String user, int id) throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = usersForms.get(user);

        // Grabs the specific form container which holds information related to a form.
        FormContainer tempFormContainer = tempUserForm.getFormContainer(id);

        // Return the form if it has a SUBMITTED status.
        if (tempFormContainer.getStatus() == TravelFormProcessorIntf.FORM_STATUS.SUBMITTED)
        {
            return tempFormContainer.getForm();
        }

        /**
         * Throw an exception as the form has not been submitted.
         */
        throw new FormStorageException("Form " + id + " appears to exist, but has status "
                + tempFormContainer.getStatus());
    }

    /**
     * Return a saved form
     * 
     * @param user - String id of the user
     * @param id - Form id
     * @return - The form in a map
     * @throws FormStorageException - Throws an error when a user is not found in the map.
     */
    public Map<String, String> getSavedFormData(String user, int id) throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = getUserSavedForms(user);

        // Grabs the specific form container which holds information related to a form.
        FormContainer tempFormContainer = tempUserForm.getFormContainer(id);

        // Return the form
        return tempFormContainer.getForm();

    }

    /**
     * Returns a map of all the forms and form metadata a user has created.
     * 
     * @param user - String id of the user
     * @return - A map of form ids and TravelFormMetadatas
     * @throws FormStorageException - Throws an error when a user is not found in the map.
     */
    public Map<Integer, TravelFormMetadata> getSavedForms(String user) throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = getUserSavedForms(user);

        // Return a map of form id's and TravelFormMetadata's
        return tempUserForm.getSavedForms();
    }

    /**
     * Saves a form once it has been audited. Sets the forms status to SUBMITTED.
     * 
     * @param user - String id of the user
     * @param data - A map of the form
     * @param id - A form id to save the completed form under
     * @throws FormStorageException - Throws an error when a user is not found in the map.
     */
    public void saveCompletedForm(String user, Map<String, String> data, int id)
            throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = getUserSavedForms(user);

        // Save the form with a SUBMITTED status
        tempUserForm.saveForm(data, id, TravelFormProcessorIntf.FORM_STATUS.SUBMITTED);

        return;
    }

    /**
     * Deletes all the forms a user has saved.
     * 
     * @param user - String id of the user
     * @throws FormStorageException - Throws an error when a user is not found in the map.
     */
    public void clearSavedForms(String user) throws FormStorageException
    {
        // Make sure the user is in the system
        addUser(user);

        // Temporary variable to hold the container of a user's saved forms.
        SavedForms tempUserForm = getUserSavedForms(user);

        // Clear the forms from the map.
        tempUserForm.clearForms();

        return;
    }
}
