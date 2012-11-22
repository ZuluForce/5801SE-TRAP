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
 * @author nagell2008
 * 
 */
public class GrantApproverName extends BusinessLogicRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
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
