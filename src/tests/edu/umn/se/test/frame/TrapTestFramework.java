// TrapTestFramework.java
package edu.umn.se.test.frame;

import java.util.HashMap;
import java.util.Map;

import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.TravelFormProcessor;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class TrapTestFramework
{
    TravelFormProcessor trapProcessor;
    Map<Integer, LoadedSampleForm> savedForms;

    String testUser1 = "linc001";

    public TrapTestFramework()
    {
        trapProcessor = new TravelFormProcessor();

        savedForms = new HashMap<Integer, LoadedSampleForm>();
    }

    public void clearSavedForms() throws TRAPException
    {
        trapProcessor.clearSavedForms();
    }

    public Map<String, String> getCompletedForm(Integer id) throws TRAPException
    {
        return trapProcessor.getCompletedForm(id);
    }

    public Map<String, String> getSavedFormData(Integer id) throws TRAPException
    {
        return trapProcessor.getSavedFormData(id);
    }

    public Map<Integer, TravelFormMetadata> getSavedForms() throws TRAPException
    {
        return trapProcessor.getSavedForms();
    }

    public String getUser()
    {
        return trapProcessor.getUser();
    }

    public Integer saveFormData(Map<String, String> formData, String desc) throws TRAPException
    {
        return trapProcessor.saveFormData(formData, desc);
    }

    public Integer saveFormData(Map<String, String> formData, Integer id) throws TRAPException
    {
        return trapProcessor.saveFormData(formData, id);
    }

    public void setUser(String user) throws TRAPException
    {
        trapProcessor.setUser(user);
    }

    public void submitFormData(Integer id) throws TRAPException
    {
        trapProcessor.submitFormData(id);
    }

    // Helper methods for tests

    public String getValidTestUser()
    {
        return testUser1;
    }

    public void setValidUser() throws TRAPException
    {
        trapProcessor.setUser(getValidTestUser());
    }

    public Integer addRandomForm() throws TRAPException
    {
        return this.addRandomForm("test");
    }

    public Integer addRandomForm(Integer id) throws TRAPException
    {
        Map<String, String> testData = TestDataGenerator.getSampleForm(SampleDataEnum.RANDOM);
        trapProcessor.saveFormData(testData, id);
        savedForms.put(id, (LoadedSampleForm) testData);

        return id;
    }

    public Integer addRandomForm(String desc) throws TRAPException
    {
        Map<String, String> testData = TestDataGenerator.getSampleForm(SampleDataEnum.RANDOM);
        int id = trapProcessor.saveFormData(testData, desc);
        savedForms.put(id, (LoadedSampleForm) testData);

        return id;
    }

    /**
     * Get the expected output for a previously added form given its id. This method will not work
     * if the user has been switched since the form was added. If you need to support that then
     * load/add the form yourself.
     * 
     * @param id - The id of the previously saved form.
     * @return - The expected output of the previously saved form.
     * @throws TRAPException - If there is a problem retrieving the saved form or the expected
     *             output.
     */
    public Map<String, String> getExpectedOutput(Integer id) throws TRAPException
    {
        LoadedSampleForm data = savedForms.get(id);

        return TestDataGenerator.getExpectedOutput(data);
    }
}
