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
// TestDataGenerator.java
package edu.umn.se.trap.test.generate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author andrewh
 * 
 */
public class TestDataGenerator
{
    /** Logger for the TestDataGenerator */
    private final static Logger log = LoggerFactory.getLogger(TestDataGenerator.class);

    private static List<String> formNames;
    private static List<String> formUsers;

    /**
     * Enumeration of the various sample forms. These are used to index into lists that contain the
     * file path and user for each form.
     * 
     * @author andrewh
     * 
     */
    public enum SampleDataEnum
    {
        /** "sample1.properties" - Domestic trip */
        DOMESTIC1,
        /** "international1.properties" - International trip to Brazil and PR */
        INTERNATIONAL1,
        /** "short_international1.properties" - Only has 1 foreign expense */
        SHORT_INTL,
    }

    /**
     * Initialize the metadata related to the various sample forms.
     * 
     * First we initialize the file paths that correspond to each of these sample forms.
     * 
     * Next we fill an array with the users for those forms so we know who to set as the current
     * user in TRAP before submitting the form.
     */
    public static void initialize()
    {
        formNames = new ArrayList<String>();
        formNames.add("data/domestic1.properties");
        formNames.add("data/international1.properties");
        formNames.add("data/short_international1.properties");

        formUsers = new ArrayList<String>();
        formUsers.add("linc001");
        formUsers.add("helge206");
        formUsers.add("linc001");
    }

    /**
     * Load the sample form from the specified source.
     * 
     * @param source - The source for the form. This enum corresponds to a file path setup in
     *            {@link #initialize()}
     * @return A sample form loaded from the filesystem.
     */
    public static LoadedSampleForm getSampleForm(SampleDataEnum source)
    {
        String filename = formNames.get(source.ordinal());

        return getSampleForm(filename);
    }

    /**
     * Get the sample form with the specified filename.
     * 
     * @param path - Path on the filesystem for the form to load. This should be a file with
     *            key:value pairs in the format of a standard Java properties file.
     * @return The form loaded from the specified path.
     */
    public static LoadedSampleForm getSampleForm(String path)
    {
        return new LoadedSampleForm(path);
    }

    /**
     * Get the expected output for the specified sameple form input. This takes the base path for
     * the input form and appends .output.
     * 
     * @param source - The source for the sample form whose expected output you want to get.
     * @return The expected output for the given input sample profile.
     */
    public static LoadedSampleForm getExpectedOutput(SampleDataEnum source)
    {
        String filename = formNames.get(source.ordinal());

        return getExpectedOutput(filename + ".output");
    }

    /**
     * Load the expected output map associated with the provided sample form. There should exist a
     * properties file in the same directory as the input file that has ".output" appended.
     * 
     * @param input - The loaded input form for which we should retrieve the expected output.
     * @return - The expected output for the given input form data.
     */
    public static LoadedSampleForm getExpectedOutput(LoadedSampleForm input)
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
    public static LoadedSampleForm getExpectedOutput(String fileName)
    {
        return new LoadedSampleForm(fileName);
    }

    /**
     * Get the user that is associated with a given form. This could also be obtained by simple
     * looking for the username field in the form.
     * 
     * @param whichForm - Which sample form's user do you want to get?
     * @return The user associated with a particular sample form
     */
    public static String getUserForForm(SampleDataEnum whichForm)
    {
        return TestDataGenerator.formUsers.get(whichForm.ordinal());
    }
}
