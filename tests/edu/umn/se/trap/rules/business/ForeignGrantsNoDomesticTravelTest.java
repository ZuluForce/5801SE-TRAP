// ForeignGrantsNoDomesticTravelTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TestGrantDB;
import edu.umn.se.test.frame.TestUserGrantDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class ForeignGrantsNoDomesticTravelTest extends TrapTestFramework
{

    String incidentalJustField;
    String incidentalAmntField;

    public ForeignGrantsNoDomesticTravelTest() throws TRAPException
    {
        super.setup(SampleDataEnum.SHORT_INTL);

        TestUserGrantDB.UserGrantBuilder ugBuilder = new TestUserGrantDB.UserGrantBuilder();
        ugBuilder.setAccount("8675309");
        ugBuilder.setAdmin("Ethan");
        ugBuilder.addAuthorizedPayee("linc001");
        userGrantDB.addUserGrantInfo(ugBuilder);

        TestGrantDB.GrantBuilder grantBuilder = new TestGrantDB.GrantBuilder();
        grantBuilder.setAccount("8675309");
        grantBuilder.setAcctype("sponsored");
        grantBuilder.setBalance(34000.00);
        grantBuilder.setFunder(null);
        grantBuilder.setOrgType("foreign");
        grantDB.addGrant(grantBuilder);

        testFormData.put("GRANT1_ACCOUNT", "8675309");
        testFormData.put("GRANT1_PERCENT", "100");

        testFormData.remove("DAY1_LODGING_CITY");
        testFormData.remove("DAY1_LODGING_COUNTRY");
        testFormData.remove("DAY1_LODGING_AMOUNT");
        testFormData.remove("DAY1_LODGING_CURRENCY");
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void foreignGrantTravelExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121001");
        testFormData.put("TRANSPORTATION1_TYPE", "AIR");
        testFormData.put("TRANSPORTATION1_CARRIER", "American");
        testFormData.put("TRANSPORTATION1_AMOUNT", "725.50");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();
    }

    @Test
    public void foreignGrantIncidentalExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        testFormData.put("DAY1_INCIDENTAL_STATE", "MN");
        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION", "I dislike...unicorns");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        saveAndSubmitTestForm();
    }

    @Test
    public void foreignGrantLodgingExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_LODGING_CITY", "Lawrence");
        testFormData.put("DAY1_LODGING_STATE", "KS");
        testFormData.put("DAY1_LODGING_COUNTRY", "USA");
        testFormData.put("DAY1_LODGING_AMOUNT", "86.31");
        testFormData.put("DAY1_LODGING_CURRENCY", "USD");

        saveAndSubmitTestForm();
    }

    @Test
    public void foreignGrantCarRentalExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "CAR");
        testFormData.put("TRANSPORTATION1_RENTAL", "YES");
        testFormData.put("TRANSPORTATION1_CARRIER", "National Traveler");
        testFormData.put("TRANSPORTATION1_AMOUNT", "125.00");
        testFormData.put("TRANSPORTATION1_CURRENCY", "BRL");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();
    }

    @Test
    public void foreignGrantTransportationMileageExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "GAS");
        testFormData.put("TRANSPORTATION1_AMOUNT", "22.50");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();

    }

    @Test
    public void foreignGrantPersonalCarExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "CAR");
        testFormData.put("TRANSPORTATION1_RENTAL", "NO");
        testFormData.put("TRANSPORTATION1_MILES_TRAVELED", "50");
        testFormData.put("TRANSPORTATION1_AMOUNT", "0.00");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("DAY1_LODGING_CITY", "Lawrence");
        testFormData.put("DAY1_LODGING_STATE", "KS");
        testFormData.put("DAY1_LODGING_COUNTRY", "USA");
        testFormData.put("DAY1_LODGING_AMOUNT", "86.31");
        testFormData.put("DAY1_LODGING_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();

    }
}
