/**
 * 
 */
package edu.umn.se.trap.rules;

import java.util.List;

/**
 * @author planeman
 * 
 */
public class TRAPRuleRegistry
{
    private List<InputValidationRule> inputValidators;
    private List<BusinessLogicRule> businessRules;

    private FinalizeRule finalizeRule;

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
}
