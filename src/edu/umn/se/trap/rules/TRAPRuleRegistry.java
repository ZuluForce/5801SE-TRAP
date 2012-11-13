/**
 * 
 */
package edu.umn.se.trap.rules;

import java.util.List;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class TRAPRuleRegistry
{
    private List<InputValidationRule> inputValidators;
    private List<BusinessLogicRule> businessRules;

    private FinalizeRule finalizeRule;

    public TRAPRuleRegistry(FinalizeRule finalRule)
    {
        finalizeRule = finalRule;
    }

    public void addInputValidationRule(InputValidationRule rule)
    {
        inputValidators.add(rule);
    }

    public void addBusinessLogicRule(BusinessLogicRule rule)
    {
        businessRules.add(rule);
    }

    public void setFinalizeRule(FinalizeRule rule)
    {
        finalizeRule = rule;
    }

    /**
     * Add all known InputValidationRules and BusinessLogicRules to the registry
     */
    public void loadAllRules()
    {
        // addInputValidationRule(...);
    }

    public void processApp(ReimbursementApp app) throws TRAPException
    {
        for (InputValidationRule rule : inputValidators)
        {
            rule.checkRule(app);
        }

        for (BusinessLogicRule rule : businessRules)
        {
            rule.checkRule(app);
        }

        finalizeRule.checkRule(app);
    }
}
