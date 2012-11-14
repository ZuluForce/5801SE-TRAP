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
