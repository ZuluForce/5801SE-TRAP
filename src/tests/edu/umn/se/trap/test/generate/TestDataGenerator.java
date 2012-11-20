// TestDataGenerator.java
package edu.umn.se.trap.test.generate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author planeman
 * 
 */
public class TestDataGenerator
{
    public enum SampleDataEnum
    {
        SAMPLE1,
        RANDOM
    }

    public static Map<String, String> getSampleForm(SampleDataEnum source)
    {
        Map<String, String> formData = new HashMap<String, String>();

        switch (source)
        {
        case SAMPLE1:
            formData = new LoadedSampleForm("data/sample1.properties");
            break;
        case RANDOM:
            // TODO: Actually implement random form generation
            formData = new LoadedSampleForm("data/sample1.properties");
            break;
        }

        return formData;
    }

    /**
     * Load the expected output map associated with the provided sample form. There should exist a
     * properties file in the same directory as the input file that has ".output" appended.
     * 
     * @param input - The loaded input form for which we should retreive the expected output.
     * @return - The expected output for the given input form data.
     */
    public static Map<String, String> getExpectedOutput(LoadedSampleForm input)
    {
        return getExpectedOutput(input.inputFile + ".output");
    }

    /**
     * Load the data in fileName as a LoadedSampleForm. In this case it is expected but not required
     * that the data in this file correspond to expected output.
     * 
     * @param fileName - filename of the properties file to load
     * @return - A LoadedSampleForm object containing the loaded key:value pairs contained in the
     *         file
     */
    public static Map<String, String> getExpectedOutput(String fileName)
    {
        return new LoadedSampleForm(fileName);
    }
}
