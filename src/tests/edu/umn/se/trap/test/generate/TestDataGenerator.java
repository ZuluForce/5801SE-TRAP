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
// TestDataGenerator.java
package edu.umn.se.trap.test.generate;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author andrewh
 * 
 */
public class TestDataGenerator
{
    private final static Logger log = LoggerFactory.getLogger(TestDataGenerator.class);

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
        String propFile = input.inputFile + ".output";
        log.info("Loading expected output file: " + propFile);

        return getExpectedOutput(propFile);
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
