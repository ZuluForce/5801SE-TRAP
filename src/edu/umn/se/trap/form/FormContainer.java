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

import java.util.Map;

import edu.umn.se.trap.TravelFormProcessorIntf;

/**
 * This class holds the actual form and related information.
 * 
 * @author nagell2008
 * 
 */
public class FormContainer
{
    /**
     * Holds the current status of a form.
     */
    private TravelFormProcessorIntf.FORM_STATUS status;

    /**
     * Map of the form.
     */
    private Map<String, String> formData;

    /**
     * The description of a form.
     */
    private String formDescription;

    /**
     * Constructor for a form container.
     * 
     * @param _formData - The form (in a map)
     * @param _desc - A description of the form
     */
    public FormContainer(Map<String, String> _formData, String _desc)
    {
        // The status for a form is set to DRAFT by default.
        status = TravelFormProcessorIntf.FORM_STATUS.DRAFT;

        // The form data.
        formData = _formData;

        // A description of the form.
        formDescription = _desc;
    }

    /**
     * Gets a form's description.
     * 
     * @return - the description of a form
     */
    public String getDescription()
    {
        return formDescription;
    }

    /**
     * Set a form's description.
     * 
     * @param desc - sets the form's description
     */
    public void setDescription(String desc)
    {
        formDescription = desc;
    }

    /**
     * Gets the form map.
     * 
     * @return - the form map
     */
    public Map<String, String> getForm()
    {
        return formData;
    }

    /**
     * Saves a form.
     * 
     * @param formData - the form to save
     */
    public void saveForm(Map<String, String> _formData)
    {
        formData = _formData;
    }

    /**
     * Saves a form with a description.
     * 
     * @param formData - the form map
     * @param desc - the form description
     */
    public void saveForm(Map<String, String> formData, String desc)
    {
        this.formData = formData;
        formDescription = desc;
    }

    /**
     * Grabs the status of a form.
     * 
     * @return - The form status
     */
    public TravelFormProcessorIntf.FORM_STATUS getStatus()
    {
        return status;
    }

    /**
     * Set the status of the form.
     * 
     * @param status - the desired status of a form
     */
    public void setStauts(TravelFormProcessorIntf.FORM_STATUS status)
    {
        this.status = status;
    }
}
