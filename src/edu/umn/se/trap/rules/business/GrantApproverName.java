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
// GrantApproverName.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.UserGrantDBWrapper;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.OutputFieldKeys;

/**
 * This rule checks that grant output fields have the grant approver name set. If the current user
 * is on of the grant approvers, the name is left blank.
 * 
 * @author nagell2008
 * 
 */
public class GrantApproverName extends BusinessLogicRule
{

    /**
     * This rule checks that grant output fields have the grant approver name set. If the current
     * user is on of the grant approvers, the name is left blank.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        List<Grant> grants = app.getGrantList();
        Grant grant = null;
        String approver;
        String formattedField;

        for (int i = 0; i < grants.size(); i++)
        {
            grant = grants.get(i);

            try
            {
                approver = UserGrantDBWrapper.getGrantAdmin(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new FormProcessorException("No grant found with account name "
                        + grant.getGrantAccount());
            }

            // Only list the approver if it is someone besides the submitter
            if (approver.compareToIgnoreCase(app.getUserInfo().getUsername()) != 0)
            {
                formattedField = String.format(OutputFieldKeys.GRANT_APPROVER_FMT, i + 1);
                app.setOutputField(formattedField, approver);
            }

        }

    }

}
