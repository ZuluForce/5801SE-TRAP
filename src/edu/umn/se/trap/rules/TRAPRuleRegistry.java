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
package edu.umn.se.trap.rules;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.rules.business.AddOtherExpenses;
import edu.umn.se.trap.rules.business.AddTransportationExpenses;
import edu.umn.se.trap.rules.business.AlcoholOnlyAllowedUnderNonSponsored;
import edu.umn.se.trap.rules.business.BaggageExpenses;
import edu.umn.se.trap.rules.business.BusinessLogicRule;
import edu.umn.se.trap.rules.business.DoDMealRestrictions;
import edu.umn.se.trap.rules.business.DomesticCarRental;
import edu.umn.se.trap.rules.business.FamilyMemberExpensesNotAllowed;
import edu.umn.se.trap.rules.business.FindDestinations;
import edu.umn.se.trap.rules.business.ForeignGrantsNoDomesticTravel;
import edu.umn.se.trap.rules.business.GrantApproverName;
import edu.umn.se.trap.rules.business.IncidentalPerDiem;
import edu.umn.se.trap.rules.business.InternetOnlyUnderNonSponsoredGrants;
import edu.umn.se.trap.rules.business.LodgingPerDiem;
import edu.umn.se.trap.rules.business.MealPerDiem;
import edu.umn.se.trap.rules.business.NIHGrantRestrictions;
import edu.umn.se.trap.rules.business.NoExportGrantsOnlyForUSCitizens;
import edu.umn.se.trap.rules.business.OnlyOneCheckedLuggage;
import edu.umn.se.trap.rules.business.PerDayCarExpenses;
import edu.umn.se.trap.rules.business.TransportationMileageExpenses;
import edu.umn.se.trap.rules.business.USCarriersOnly;
import edu.umn.se.trap.rules.input.DateValidator;
import edu.umn.se.trap.rules.input.EmailAddressValidator;
import edu.umn.se.trap.rules.input.GrantPercentSumTo100;
import edu.umn.se.trap.rules.input.InputValidationRule;
import edu.umn.se.trap.rules.input.OneOrMoreGrantsAllValid;
import edu.umn.se.trap.rules.input.OtherExpenseDateValidator;
import edu.umn.se.trap.rules.input.PhoneNumberValidator;
import edu.umn.se.trap.rules.input.TransportationDateValidator;

/**
 * A registry of rules that are used to process a ReimbursementApp. When called to process, the
 * registry will pass the app to the internal rules. The order they are processed in is as follows:
 * 1. InputValidationRules 2. BusinessLogicRules 3. FinalizeRule (only one)
 * 
 * @author andrewh
 * 
 */
public class TRAPRuleRegistry
{
    /** Logger for the TRAPRuleRegistry */
    private static Logger log = LoggerFactory.getLogger(TRAPRuleRegistry.class);

    /** List of input validation rules called by the registry */
    private final List<InputValidationRule> inputValidators;

    /** List of business logic rules called by the registry */
    private final List<BusinessLogicRule> businessRules;

    /** The finalize rule that is called after the input and business rules */
    private FinalizeRule finalizeRule;

    /**
     * Construct the rule registry. This will automatically add all known rules to the registry.
     */
    public TRAPRuleRegistry()
    {
        inputValidators = new ArrayList<InputValidationRule>();
        businessRules = new ArrayList<BusinessLogicRule>();

        // Registers all known rules
        loadAllRules();
    }

    /**
     * Add an input validation rule to the registry.
     * 
     * @param rule - the InputValidationRule to add to the registry.
     */
    public void addInputValidationRule(InputValidationRule rule)
    {
        inputValidators.add(rule);
    }

    /**
     * Add a business logic rule to the registry.
     * 
     * @param rule - the BusinessLogicRule to add to the registry.
     */
    public void addBusinessLogicRule(BusinessLogicRule rule)
    {
        businessRules.add(rule);
    }

    /**
     * Set the FinalizeRule for the registry. This is the rule that gets called after all other
     * rules have been processed. There is only one of these.
     * 
     * @param rule - The FinalizeRule to set for the registry.
     */
    public void setFinalizeRule(FinalizeRule rule)
    {
        finalizeRule = rule;
    }

    /**
     * Add all known InputValidationRules and BusinessLogicRules to the registry
     * 
     * @see TRAPRule
     */
    public void loadAllRules()
    {
        // Set the finalize rule
        setFinalizeRule(new FinalizeRule());

        // Add InputValidationRules
        addInputValidationRule(new DateValidator());

        addInputValidationRule(new TransportationDateValidator());
        addInputValidationRule(new OtherExpenseDateValidator());
        addInputValidationRule(new EmailAddressValidator());
        addInputValidationRule(new GrantPercentSumTo100());
        addInputValidationRule(new OneOrMoreGrantsAllValid());
        addInputValidationRule(new PhoneNumberValidator());

        // Add BusinessLogicRules
        addBusinessLogicRule(new FindDestinations());
        addBusinessLogicRule(new GrantApproverName());
        addBusinessLogicRule(new AddOtherExpenses());
        addBusinessLogicRule(new AlcoholOnlyAllowedUnderNonSponsored());
        addBusinessLogicRule(new InternetOnlyUnderNonSponsoredGrants());
        addBusinessLogicRule(new NoExportGrantsOnlyForUSCitizens());
        addBusinessLogicRule(new ForeignGrantsNoDomesticTravel());
        addBusinessLogicRule(new DomesticCarRental());
        addBusinessLogicRule(new NIHGrantRestrictions());
        addBusinessLogicRule(new DoDMealRestrictions());
        addBusinessLogicRule(new FamilyMemberExpensesNotAllowed());
        addBusinessLogicRule(new DomesticCarRental());

        addBusinessLogicRule(new MealPerDiem());
        addBusinessLogicRule(new LodgingPerDiem());
        addBusinessLogicRule(new IncidentalPerDiem());

        addBusinessLogicRule(new TransportationMileageExpenses());
        addBusinessLogicRule(new PerDayCarExpenses());
        addBusinessLogicRule(new BaggageExpenses());
        addBusinessLogicRule(new AddTransportationExpenses());
        addBusinessLogicRule(new OnlyOneCheckedLuggage());

        addBusinessLogicRule(new USCarriersOnly());

    }

    /**
     * Start processing of a ReimbursementApp. This run through all registered rules. When all is
     * completed, the output map within the app should be completed and ready for saving (baring any
     * exceptions).
     * 
     * @see ReimbursementApp
     * @param app - The app to process
     * @throws TRAPException - When there is an error during processing.
     */
    public void processApp(ReimbursementApp app) throws TRAPException
    {
        // Input rules
        log.info("Checking app against {} input validation rules", inputValidators.size());

        for (InputValidationRule rule : inputValidators)
        {
            rule.checkRule(app);
        }

        // Business rules
        log.info("Checking app against {} business rules", businessRules.size());

        for (BusinessLogicRule rule : businessRules)
        {
            rule.checkRule(app);
        }

        // Finalize rule
        log.info("Doing final processing of the app against the finalize rule");

        finalizeRule.checkRule(app);
    }
}
