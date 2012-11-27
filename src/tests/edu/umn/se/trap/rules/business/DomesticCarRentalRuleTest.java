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
// DomesticCarRentalRuleTest.java
package edu.umn.se.trap.rules.business;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class DomesticCarRentalRuleTest extends TestCase
{
    TransportationExpense foreignRental;
    TransportationExpense goodDomestic;
    TransportationExpense badDomestic;
    TransportationExpense otherExpense;

    ReimbursementApp testApp = new ReimbursementApp();

    DomesticCarRental rule = new DomesticCarRental();

    @Override
    public void setUp()
    {
        foreignRental = new TransportationExpense();
        foreignRental.setExpenseCurrency("EUR");
        foreignRental.setTransportationRental(TRAPConstants.STR_YES);
        foreignRental.setTransportationType(TransportationTypeEnum.CAR);

        goodDomestic = new TransportationExpense();
        goodDomestic.setExpenseCurrency(TRAPConstants.USD);
        goodDomestic.setTransportationCarrier("National Travel");
        goodDomestic.setTransportationRental(TRAPConstants.STR_YES);
        goodDomestic.setTransportationType(TransportationTypeEnum.CAR);

        badDomestic = new TransportationExpense();
        badDomestic.setExpenseCurrency(TRAPConstants.USD);
        badDomestic.setTransportationCarrier("Bob's car rental");
        badDomestic.setTransportationRental(TRAPConstants.STR_YES);
        badDomestic.setTransportationType(TransportationTypeEnum.CAR);

        otherExpense = new TransportationExpense();
        otherExpense.setTransportationType(TransportationTypeEnum.BAGGAGE);
        otherExpense.setExpenseCurrency(TRAPConstants.USD);

        // testApp.addTransportationExpense(foreignRental);
        // testApp.addTransportationExpense(goodDomestic);
        // testApp.addTransportationExpense(badDomestic);
        // testApp.addTransportationExpense(otherExpense);

        return;
    }

    @Test
    public void testForeignRental()
    {
        testApp.addTransportationExpense(foreignRental);

        try
        {
            rule.checkRule(testApp);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testGoodDomestic()
    {
        testApp.addTransportationExpense(goodDomestic);
        try
        {
            rule.checkRule(testApp);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Exception: " + e.getMessage());
        }
    }

    @Test
    public void testBadDomestic()
    {
        testApp.addTransportationExpense(badDomestic);

        try
        {
            rule.checkRule(testApp);
            Assert.fail("Rule passes with bad domestic car rental");
        }
        catch (TRAPException e)
        {
            ; // Good
        }
    }
}
