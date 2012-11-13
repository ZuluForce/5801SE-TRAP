/**
 * 
 */
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
