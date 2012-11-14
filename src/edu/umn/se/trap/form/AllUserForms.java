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

    public void saveFormData(String user, Map<String, String> formData, int id)
    {
        // TODO: Write
        if (!usersForms.containsKey(user))
        {
            return;
        }
        SavedForms tempUserForm = usersForms.get(user);

        tempUserForm.saveForm(formData, id);
        return;
    }

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

    public Map<String, String> getCompletedForm(String user, int id)
    {
        // TODO: Write
        if (!usersForms.containsKey(user))
        {
            return null;
        }
        SavedForms tempUserForm = usersForms.get(user);
        FormContainer tempFormContainer = tempUserForm.getFormContainer(id);

        // Need to add the FormStatusEnum
        if (tempFormContainer.getStatus() == FormStatusEnum.SUBMITTED)
        {
            return tempFormContainer.getForm();
        }

        return null;
    }

    public Map<String, String> getSavedFormData(String user, int id)
    {
        // TODO: Write

        return null;
    }

    public Map<Integer, TravelFormMetadata> getSavedForms(String user)
    {
        // TODO: Write

        return null;
    }

    public void saveCompletedForm(String user, Map<String, String> data, int id)
    {
        // TODO: Write
    }

    public void clearSavedForms(String user)
    {
        // TODO: Write
    }
}
