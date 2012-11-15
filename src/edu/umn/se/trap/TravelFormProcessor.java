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
package edu.umn.se.trap;

import java.util.Map;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.form.AllUserForms;
import edu.umn.se.trap.form.FormDataConverter;
import edu.umn.se.trap.rules.FinalizeRule;
import edu.umn.se.trap.rules.TRAPRuleRegistry;

/**
 * @author planeman
 * 
 */
public class TravelFormProcessor implements TravelFormProcessorIntf
{
    private final AllUserForms formStorage;
    private final TRAPRuleRegistry ruleRegistry;
    private String user;

    public TravelFormProcessor()
    {
        formStorage = new AllUserForms();
        ruleRegistry = new TRAPRuleRegistry(new FinalizeRule());
        user = null;
    }

    @Override
    public void clearSavedForms() throws Exception
    {
        checkUserSet();
        formStorage.clearSavedForms(user);
    }

    @Override
    public Map<String, String> getCompletedForm(Integer id) throws Exception
    {
        checkUserSet();
        return formStorage.getCompletedForm(user, id);
    }

    @Override
    public Map<String, String> getSavedFormData(Integer id) throws Exception
    {
        checkUserSet();
        return formStorage.getSavedFormData(user, id);
    }

    @Override
    public Map<Integer, TravelFormMetadata> getSavedForms() throws Exception
    {
        checkUserSet();
        return formStorage.getSavedForms(user);
    }

    @Override
    public String getUser()
    {
        return user;
    }

    @Override
    public Integer saveFormData(Map<String, String> formData, String desc) throws Exception
    {
        checkUserSet();

        return formStorage.saveFormData(user, formData, desc);
    }

    @Override
    public Integer saveFormData(Map<String, String> formData, Integer id) throws Exception
    {
        checkUserSet();

        return formStorage.saveFormData(user, formData, id);
    }

    @Override
    public void setUser(String user) throws Exception
    {
        this.user = user;

    }

    @Override
    public void submitFormData(Integer id) throws Exception
    {
        Map<String, String> data = formStorage.getSavedFormData(user, id);

        ReimbursementApp app = FormDataConverter.formToReimbursementApp(data);

        ruleRegistry.processApp(app);

        // TODO: Manage any post processing steps
    }

    private void checkUserSet() throws FormProcessorException
    {
        if (user == null)
            throw new FormProcessorException("No user set in the TravelFormProcessor");
    }

}
