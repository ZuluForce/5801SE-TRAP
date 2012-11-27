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
import edu.umn.se.trap.exception.InputValidationException;

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
    private final Map<String, Map<Integer, FormContainer>> usersForms;

    /**
     * A counter to generate form id's from. A user is allowed forms up to the max integer a long
     * can hold.
     */
    private int formId;

    /**
     * an object for checking the validity of keys in saved form data.
     */
    private final UnknownKeyChecker validKeyChecker;

    /**
     * Constructor for AllUserForms. Initializes the usersForms hash map; starts out empty.
     */
    public AllUserForms()
    {
        usersForms = new HashMap<String, Map<Integer, FormContainer>>();
        validKeyChecker = new UnknownKeyChecker();

        formId = 0;
    }

    /**
     * Gets all of a user's forms. This method assumes addUser has already been called and therefore
     * does not check for the existence of the user.
     * 
     * @param user - String id of the user
     * @return - A map of form containers and the respective id's
     * @throws FormStorageException - Thrown if a user does not have any forms
     */
    private Map<Integer, FormContainer> getUserForms(String user) throws FormStorageException
    {
        Map<Integer, FormContainer> returnMap = usersForms.get(user);

        if (returnMap.size() == 0)
        {
            throw new FormStorageException("User " + user + " has no forms available");
        }

        return returnMap;
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
        usersForms.put(user, new HashMap<Integer, FormContainer>());

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

        // Make sure the formData doesn't contain invalid keys
        checkValidKeys(formData);

        // Temporary variable to hold the container of a user's saved forms.
        Map<Integer, FormContainer> tempUserForms = usersForms.get(user);

        // The user has not previously saved this form
        if (!tempUserForms.containsKey(id))
        {
            throw new FormStorageException("Id " + id + " not found for user " + user);
        }

        // Grab the form container
        FormContainer tempForm = tempUserForms.get(id);

        // Save the form
        tempForm.saveForm(formData);

        // Return the id for consistency
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

        // Make sure the formData doesn't contain invalid keys
        checkValidKeys(formData);

        // New id to save the new form with
        int newId = generateNewId();

        // Create a new form container for the new form
        FormContainer newForm = new FormContainer(formData, desc);

        // Get all of a users forms (with form containers)
        Map<Integer, FormContainer> tempUserForms = usersForms.get(user);

        // Put the new form container in the map
        tempUserForms.put(newId, newForm);

        // Return the new form id for later reference.
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
        // Map<Integer, FormContainer> tempUsersForms = usersForms.get(user);
        Map<Integer, FormContainer> tempUsersForms = getUserForms(user);
        // If the user has not saved the form previously
        if (!tempUsersForms.containsKey(id))
        {
            throw new FormStorageException("Cannot find form " + id + " for user " + user);
        }

        // Get the form container holding the form
        FormContainer tempFormContainer = tempUsersForms.get(id);

        // Return the form if it has a SUBMITTED status.
        if (tempFormContainer.getStatus() == TravelFormProcessorIntf.FORM_STATUS.SUBMITTED)
        {
            return tempFormContainer.getForm();
        }

        // Throw an exception as the form has not been submitted.
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
        // Map<Integer, FormContainer> tempUserForms = usersForms.get(user);
        Map<Integer, FormContainer> tempUserForms = getUserForms(user);
        // If the user has not previously saved the form
        if (!tempUserForms.containsKey(id))
        {
            throw new FormStorageException("Cannot find form " + id + " for user " + user);
        }

        // Grab the form container holding the form related to the id
        FormContainer tempFormContainer = tempUserForms.get(id);

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
        // Map<Integer, FormContainer> tempUserForms = usersForms.get(user);
        Map<Integer, FormContainer> tempUserForms = getUserForms(user);

        // Result map to return
        Map<Integer, TravelFormMetadata> resultForms = new HashMap<Integer, TravelFormMetadata>();

        /*
         * Loop through all of a user's form containers to create a new TravelFormMetadata object
         * and populate it. Then add it to the result map with the form id and TravelFormMetadata.
         */
        for (Map.Entry<Integer, FormContainer> entry : tempUserForms.entrySet())
        {
            // A new TravelFormMetadata object to hold a description and status
            TravelFormMetadata tempMetadata = new TravelFormMetadata();

            // The description of a form.
            tempMetadata.description = entry.getValue().getDescription();

            // The status of the form.
            tempMetadata.status = entry.getValue().getStatus();

            // Put the form id and tempMetadata into the result hash map.
            resultForms.put(entry.getKey(), tempMetadata);
        }

        // Return a map of form id's and TravelFormMetadata's
        return resultForms;
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
        Map<Integer, FormContainer> tempUserForms = usersForms.get(user);

        if (!tempUserForms.containsKey(id))
        {
            throw new FormStorageException("Cannot find form " + id + " for user " + user);
        }

        FormContainer tempForm = tempUserForms.get(id);

        tempForm.saveForm(data);
        tempForm.setStauts(TravelFormProcessorIntf.FORM_STATUS.SUBMITTED);

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
        Map<Integer, FormContainer> tempUserForms = usersForms.get(user);

        // Clear all the forms a use has saved
        tempUserForms.clear();

        return;
    }

    /**
     * Calls the form key checker and wraps any potential exception in a FormStorageException. This
     * is a convenience method.
     * 
     * @param formData - form data map to check
     * @throws FormStorageException - When there is an invalid key in the formData
     */
    private void checkValidKeys(Map<String, String> formData) throws FormStorageException
    {
        try
        {
            validKeyChecker.areFormKeysValid(formData);
        }
        catch (InputValidationException ive)
        {
            throw new FormStorageException(ive);
        }
    }

    /**
     * @return - Returns a new id for a form
     */
    private int generateNewId()
    {
        // New id to return
        int returnId = formId;

        // Calculate the next id
        formId++;

        return returnId;
    }

}
