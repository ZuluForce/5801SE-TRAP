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

    DomesticCarRentalRule rule = new DomesticCarRentalRule();

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
