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
    private final int formId;

    public SavedForms()
    {
        savedForms = new HashMap<Integer, FormContainer>();
        formId = 0;
    }

    public FormContainer getFormContainer(int id)
    {
        // TODO: Write

        return null;
    }

    public Map<Integer, TravelFormMetadata> getSavedForms()
    {
        return null;
    }

    public void saveForm(Map<String, String> formData, int id)
    {
        // TODO: Write
    }

    public int saveForm(Map<String, String> formData, String desc)
    {
        // TODO: Write
        return 0;
    }

    public void saveForm(Map<String, String> formData, TravelFormProcessorIntf.FORM_STATUS status)
    {
        // TODO: Write
    }

    public void clearForms()
    {
        // TODO: Write
    }

    private Integer getNewFormId()
    {
        // TODO: Write

        return 0;
    }
}
