// CurrencyAbbreviationTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class CurrencyAbbreviationTest extends TrapTestFramework
{

    String incidental1CurrencyField;

    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);

        incidental1CurrencyField = String.format(InputFieldKeys.INCIDENTAL_CURRENCY_FMT, 1);
    }

    @Test
    public void validCurrency() throws TRAPException
    {
        // The default international form has all valid currencies
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void missingCurrency() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + incidental1CurrencyField);
        testFormData.remove(incidental1CurrencyField);
        saveAndSubmitTestForm();
    }

    @Test
    public void truncatedCurrency() throws TRAPException
    {
        exception.expect(TRAPRuntimeException.class);
        exception.expectMessage("currency");
        testFormData.put(incidental1CurrencyField, "US");
        saveAndSubmitTestForm();
    }

    @Test
    public void extraCharacterSpace() throws TRAPException
    {
        exception.expect(TRAPRuntimeException.class);
        exception.expectMessage("currency");
        testFormData.put(incidental1CurrencyField, "USD ");
        saveAndSubmitTestForm();
    }

    @Test
    public void extraCharacter() throws TRAPException
    {
        exception.expect(TRAPRuntimeException.class);
        exception.expectMessage("currency");

        // United States Development League
        testFormData.put(incidental1CurrencyField, "USDL");
        saveAndSubmitTestForm();
    }
}
