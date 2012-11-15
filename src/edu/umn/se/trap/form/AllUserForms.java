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

import edu.umn.se.trap.TravelFormMetadata;

/**
 * @author planeman
 * 
 */
public class AllUserForms
{
    /**
     * Holds a user's saved forms
     */
    private Map<String, SavedForms> usersForms;

    /**
     * 
     * @param user
     *            - String of the user id
     * @return Returns a user's saved forms in a SavedForms object, otherwise null.
     */
    public SavedForms getUserSavedForms(String user)
    {
        // TODO: Write
        if (usersForms.containsKey(user))
        {
            return usersForms.get(user);
        }

        return null;
    }

    /**
     * Adds a user to the map (usersForms) to save forms
     * 
     * @param user
     *            - String id of the user
     */
    public void addUser(String user)
    {
        // TODO: Write. Also consider renaming this to addUser
        if (usersForms.containsKey(user))
        {
            return;
        }
        usersForms.put(user, new SavedForms());
        return;
    }

    /**
     * Saves a form by id. This will overwrite a form if the id already exists
     * 
     * @param user
     *            - String of the user id
     * @param formData
     *            - Map of the form data
     * @param id
     *            - Desired form id, as an integer
     */
    public void saveFormData(String user, Map<String, String> formData, int id)
    {
        // TODO: Write
        if (!usersForms.containsKey(user))
        {
            return;
        }
        SavedForms tempUserForm = usersForms.get(user);

        if (tempUserForm == null)
        {
            return;
        }

        tempUserForm.saveForm(formData, id);
        return;
    }

    /**
     * Save a form with a description. Returns the form id created when the form is inserted.
     * 
     * @param user
     *            - String of the user id
     * @param formData
     *            - Map of the form data
     * @param desc
     *            - String description of the form
     * @return - The new form id (as an integer)
     */
    public int saveFormData(String user, Map<String, String> formData, String desc)
    {
        // TODO : Write
        if (!usersForms.containsKey(user))
        {
            // For now, -1 shall be an invalid form id
            return -1;
        }
        SavedForms tempUserForm = usersForms.get(user);

        return tempUserForm.saveForm(formData, desc);
    }

    /**
     * Returns a completed form referenced by id.
     * 
     * @param user
     *            - String id of the user
     * @param id
     *            - Desired id of the form to return
     * @return - The form in a map
     */
    public Map<String, String> getCompletedForm(String user, int id)
    {
        // TODO: Write
        if (!usersForms.containsKey(user))
        {
            return null;
        }
        SavedForms tempUserForm = usersForms.get(user);

        if (tempUserForm == null)
        {
            return null;
        }

        FormContainer tempFormContainer = tempUserForm.getFormContainer(id);

        if (tempFormContainer == null)
        {
            return null;
        }

        // Need to add the FormStatusEnum
        if (tempFormContainer.getStatus() == FormStatusEnum.SUBMITTED)
        {
            return tempFormContainer.getForm();
        }

        return null;
    }

    /**
     * Return a saved form
     * 
     * @param user
     *            - String id of the user
     * @param id
     *            - Form id
     * @return - The form in a map
     */
    public Map<String, String> getSavedFormData(String user, int id)
    {
        // TODO: Write

        if (!usersForms.containsKey(user))
        {
            return null;
        }

        SavedForms tempUserForm = usersForms.get(user);

        if (tempUserForm == null)
        {
            return null;
        }

        FormContainer tempFormContainer = tempUserForm.getFormContainer(id);

        if (tempFormContainer == null)
        {
            return null;
        }

        return tempFormContainer.getForm();

    }

    /**
     * Returns a map of all the forms and form metadata a user has
     * 
     * @param user
     *            - String id of the user
     * @return - A map of form ids and TravelFormMetadatas
     */
    public Map<Integer, TravelFormMetadata> getSavedForms(String user)
    {
        // TODO: Write

        if (usersForms.containsKey(user))
        {
            return null;
        }

        SavedForms tempUserForm = usersForms.get(user);

        if (tempUserForm == null)
        {
            return null;
        }

        // TODO: Need to add the TravelFormMetadata class

        return null;
    }

    /**
     * Saves a form once it has been audited
     * 
     * @param user
     *            - String id of the user
     * @param data
     *            - A map of the form
     * @param id
     *            - A form id to save the completed form under
     */
    public void saveCompletedForm(String user, Map<String, String> data, int id)
    {
        // TODO: Write
    }

    /**
     * Deletes all the forms a user has saved.
     * 
     * @param user
     *            - String id of the user
     */
    public void clearSavedForms(String user)
    {
        // TODO: Write
    }
}
