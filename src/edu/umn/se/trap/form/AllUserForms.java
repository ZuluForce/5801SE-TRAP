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
