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
        SAMPLE1, RANDOM
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
            break;
        }

        return formData;
    }
}
