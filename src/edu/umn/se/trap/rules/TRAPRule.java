/**
 * 
 */
package edu.umn.se.trap.rules;

import edu.umn.se.trap.data.ReimbursementApp;

/**
 * @author planeman
 * 
 */
public interface TRAPRule
{
    public void checkRule(ReimbursementApp app);
}
