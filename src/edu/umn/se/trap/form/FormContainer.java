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

    public String getDescription()
    {
        return formDescription;
    }

    public void setDescription(String desc)
    {
        formDescription = desc;
    }

    public Map<String, String> getForm()
    {
        // TODO: Consider renaming to getFormData
        return formData;
    }

    public void saveForm(Map<String, String> formData)
    {
        this.formData = formData;
    }

    public void saveForm(Map<String, String> formData, String desc)
    {
        this.formData = formData;
        formDescription = desc;
    }

    public TravelFormProcessorIntf.FORM_STATUS getStatus()
    {
        return status;
    }

    public void setStauts(TravelFormProcessorIntf.FORM_STATUS status)
    {
        this.status = status;
    }
}
