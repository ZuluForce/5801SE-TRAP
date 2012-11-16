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

import edu.umn.se.trap.TravelFormProcessorIntf;

/**
 * @author planeman
 * 
 */
public class FormContainer
{
    private TravelFormProcessorIntf.FORM_STATUS status;
    private Map<String, String> formData;
    private String formDescription;

    /**
     * Constructor for a form container.
     * 
     * @param _formData
     *            - The form (in a map)
     * @param _desc
     *            - A description of the form
     */
    public FormContainer(Map<String, String> _formData, String _desc)
    {
        status = TravelFormProcessorIntf.FORM_STATUS.DRAFT;
        formData = _formData;
        formDescription = _desc;
    }

    /**
     * Gets a form's description
     * 
     * @return - the description of a form
     */
    public String getDescription()
    {
        return formDescription;
    }

    /**
     * Set a form's description
     * 
     * @param desc
     *            - sets the form's description
     */
    public void setDescription(String desc)
    {
        formDescription = desc;
    }

    /**
     * Gets the form map
     * 
     * @return - the form map
     */
    public Map<String, String> getForm()
    {
        // TODO: Consider renaming to getFormData
        return formData;
    }

    /**
     * Saves a form
     * 
     * @param formData
     *            - the form to save
     */
    public void saveForm(Map<String, String> formData)
    {
        this.formData = formData;
    }

    /**
     * Saves a form with a description
     * 
     * @param formData
     *            - the form map
     * @param desc
     *            - the form description
     */
    public void saveForm(Map<String, String> formData, String desc)
    {
        this.formData = formData;
        formDescription = desc;
    }

    /**
     * Grabs the status of a form
     * 
     * @return - The form status
     */
    public TravelFormProcessorIntf.FORM_STATUS getStatus()
    {
        return status;
    }

    /**
     * Set the status of the form
     * 
     * @param status
     *            - the desired status of a form
     */
    public void setStauts(TravelFormProcessorIntf.FORM_STATUS status)
    {
        this.status = status;
    }
}
