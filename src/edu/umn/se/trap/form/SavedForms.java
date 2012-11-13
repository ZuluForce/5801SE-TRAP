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
public class SavedForms
{
    private Map<Integer, FormContainer> savedForms;

    public FormContainer getFormContainer(int id)
    {
        // TODO: Write

        return null;
    }

    public void saveForm(Map<String, String> formData, int id)
    {
        // TODO: Write
    }

    public void saveForm(Map<String, String> formData, String desc)
    {
        // TODO: Write
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
