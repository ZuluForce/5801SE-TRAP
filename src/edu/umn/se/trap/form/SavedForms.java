/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.TravelFormProcessorIntf;
import edu.umn.se.trap.exception.FormStorageException;

/**
 * This class holds all the forms of one user. There are multiple instances of this class at one
 * time (one for each user that has saved a form).
 * 
 * @author nagell2008
 * 
 */
public class SavedForms
{
    /**
     * Holds all of a user's forms in a map. See class FormContainer for more information on the
     * data that is saved along with a form.
     */
    private final Map<Integer, FormContainer> savedForms;

    /**
     * A counter to generate form id's from. A user is allowed forms up to the max integer an int
     * can hold.
     */
    private int formId;

    /**
     * Constructor for SavedForms.
     */
    public SavedForms()
    {
        // Initialized to be empty.
        savedForms = new HashMap<Integer, FormContainer>();

        // The form id generator is unique to each SavedForms for each user.s
        formId = 0;
    }

    /**
     * Grabs the form container, which holds information about a user's form (along with the form).
     * 
     * @param id - Form id
     * @return - The container holding the form and related information
     * @throws FormStorageException - A form does not exist for the inputed id
     */
    public FormContainer getFormContainer(int id) throws FormStorageException
    {

        // Check to see if a form does not exist in the map.
        if (!savedForms.containsKey(id))
        {
            throw new FormStorageException("User has not saved form " + id);
        }

        // Return the container for a form.
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

        // A hash map to hold form id's and TravelFormMetadata's. This will be returned.
        Map<Integer, TravelFormMetadata> resultAllForms = new HashMap<Integer, TravelFormMetadata>();

        /*
         * Loop through all of a user's form containers to create a new TravelFormMetadata object
         * and populate it. Then add it to the result map with the form id and TravelFormMetadata.
         */
        for (Map.Entry<Integer, FormContainer> entry : savedForms.entrySet())
        {
            // A new TravelFormMetadata object to hold a description and status
            TravelFormMetadata tempMetadata = new TravelFormMetadata();

            // The description of a form.
            tempMetadata.description = entry.getValue().getDescription();

            // The status of the form.
            tempMetadata.status = entry.getValue().getStatus();

            // Put the form id and tempMetadata into the result hash map.
            resultAllForms.put(entry.getKey(), tempMetadata);
        }

        // Return the resulting map. Potentially could be empty.
        return resultAllForms;
    }

    /**
     * Saves a form with a provided form id.
     * 
     * @param formData - the form map
     * @param id - the form id
     * @throws FormStorageException - A form does not exist for the inputted id
     */
    public void saveForm(Map<String, String> formData, int id) throws FormStorageException
    {

        // Check to see if a form does not exist in the map.
        if (!savedForms.containsKey(id))
        {
            throw new FormStorageException("User has not saved form " + id);
        }

        // A temporary form container to hold a container related to the form id.
        FormContainer tempContainer = savedForms.get(id);

        // Save a the form in the container.
        tempContainer.saveForm(formData);

        return;
    }

    /**
     * Save a form with a new form id.
     * 
     * @param formData - The form
     * @param desc - A description of the form
     * @return - The new form id
     */
    public int saveForm(Map<String, String> formData, String desc)
    {

        // Get a new form id.
        int newId = getNewFormId();

        // Save the form into the map with a new form container.
        savedForms.put(newId, new FormContainer(formData, desc));

        // Return the id for later reference.
        return newId;
    }

    /**
     * Saves a form with a new status.
     * 
     * @param formData - The form map (most likely a processed form)
     * @param status - The new status of the form
     * @throws FormStorageException - A for does not exist for the inputted id
     */
    public void saveForm(Map<String, String> formData, Integer id,
            TravelFormProcessorIntf.FORM_STATUS status) throws FormStorageException
    {
        // Check to see if a form does not exist in the map.
        if (!savedForms.containsKey(id))
        {
            throw new FormStorageException("User has not saved form " + id);
        }

        // Temporary container to hold the form related to the id.
        FormContainer tempContainer = savedForms.get(id);

        // Save the form.
        tempContainer.saveForm(formData);

        // Set the status of the form.
        tempContainer.setStauts(status);

        return;
    }

    /**
     * Remove all the forms a user has saved
     */
    public void clearForms()
    {
        // Clear all the forms a user has saved.
        savedForms.clear();
    }

    /**
     * Get a new id for a form. Attribute formId holds the form id that will be returned. formId is
     * then increased for the next id to be returned.
     * 
     * @return - The new id
     */
    private Integer getNewFormId()
    {
        // Holds the id that will be returned.
        int tempId = formId;

        // Generate the next id.
        formId++;

        // Return the id for a new form.
        return tempId;
    }
}
