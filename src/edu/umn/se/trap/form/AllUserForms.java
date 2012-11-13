/**
 * 
 */
package edu.umn.se.trap.form;

import java.util.Map;

import edu.umn.se.trap.TravelFormMetadata;

/**
 * @author planeman
 * 
 */
public class AllUserForms
{
    private Map<String, SavedForms> usersForms;

    public SavedForms getUserSavedForms(String user)
    {
        // TODO: Write

        return null;
    }

    public void insertUser(String user)
    {
        // TODO: Write. Also consider renaming this to addUser
    }

    public void saveFormData(String user, Map<String, String> formData, int id)
    {
        // TODO: Write
    }

    public int saveFormData(String user, Map<String, String> formData, String desc)
    {
        // TODO : Write

        return 0;
    }

    public Map<String, String> getCompletedForm(String user, int id)
    {
        // TODO: Write

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
