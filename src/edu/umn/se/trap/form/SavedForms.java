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

/**
 * @author planeman
 * 
 */
public class SavedForms
{
    /**
     * Holds all of a user's forms
     */
    private final Map<Integer, FormContainer> savedForms;

    /**
     * A counter to generate form id's from. A user is allowed forms up to the max integer and int
     * can hold.
     */
    private int formId;

    public SavedForms()
    {
        savedForms = new HashMap<Integer, FormContainer>();
        formId = 0;
    }

    /**
     * Grabs the form container, which holds information about a user's form (along with the form)
     * 
     * @param id
     *            - Form id
     * @return - The container holding the form and related information
     */
    public FormContainer getFormContainer(int id)
    {
        // TODO: Write
        if (!savedForms.containsKey(id))
        {
            return null;
        }

        return savedForms.get(id);
    }

    /**
     * Grabs all of the form id's a user has, along with some additional information stored in a
     * TravelFormMetadata object.
     * 
     * @return - Map of form id's and TravelFormMetadata
     */
    public Map<Integer, TravelFormMetadata> getSavedForms()
    {
        Map<Integer, TravelFormMetadata> resultAllForms = new HashMap<Integer, TravelFormMetadata>();

        for (Map.Entry<Integer, FormContainer> entry : savedForms.entrySet())
        {
            TravelFormMetadata tempMetadata = new TravelFormMetadata();

            tempMetadata.description = entry.getValue().getDescription();

            tempMetadata.status = entry.getValue().getStatus();

            resultAllForms.put(entry.getKey(), tempMetadata);
        }

        return resultAllForms;
    }

    /**
     * Saves a form
     * 
     * @param formData
     *            - the form map
     * @param id
     *            - the form id
     */
    public void saveForm(Map<String, String> formData, int id)
    {
        // TODO: Write
        if (!savedForms.containsKey(id))
        {
            return;
        }

        FormContainer tempContainer = savedForms.get(id);

        tempContainer.saveForm(formData);

        return;
    }

    /**
     * Save a form with a new form id
     * 
     * @param formData
     *            - The form
     * @param desc
     *            - A description of the form
     * @return - The new form id
     */
    public int saveForm(Map<String, String> formData, String desc)
    {
        // TODO: Write
        int newId = getNewFormId();

        savedForms.put(newId, new FormContainer(formData, desc));

        return newId;
    }

    /**
     * Saves a form with a new status.
     * 
     * @param formData
     *            - The form map (most likely a processed form)
     * @param status
     *            - The new status of the form
     */
    public void saveForm(Map<String, String> formData, Integer id,
            TravelFormProcessorIntf.FORM_STATUS status)
    {
        // TODO: Write

        if (!savedForms.containsKey(id))
        {
            return;
        }

        FormContainer tempContainer = savedForms.get(id);

        tempContainer.saveForm(formData);
        tempContainer.setStauts(status);

        return;
    }

    /**
     * Remove all the forms a user has saved
     */
    public void clearForms()
    {
        // TODO: Write
        savedForms.clear();
    }

    /**
     * Get a new id for a form
     * 
     * @return - The new id
     */
    private Integer getNewFormId()
    {
        // TODO: Write
        int tempId = formId;

        formId++;

        return tempId;
    }
}
