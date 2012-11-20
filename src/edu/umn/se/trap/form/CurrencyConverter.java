// CurrencyConverter.java
package edu.umn.se.trap.form;

import java.util.Date;

import edu.umn.se.trap.db.CurrencyDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.rules.DateValidator;

/**
 * @author planeman
 * 
 */
public class CurrencyConverter
{
    /**
     * Convert the given amount of money to USD from the specified currency on the date provided.
     * 
     * @param amount - The amount to convert
     * @param currency - The currency for the amount
     * @param date - The date to use when looking up the conversion rate
     * @return - The amount converted to USD according to the conversion rate for currency on the
     *         given date.
     */
    public static Double convertToUSD(Double amount, String currency, Date date)
    {
        return convertToUSD(amount, currency, DateValidator.dateToString(date));
    }

    /**
     * Convert the given amount of money to USD from the specified currency on the date provided.
     * 
     * @param amount - The amount to convert
     * @param currency - The currency for the amount
     * @param date - The date to use when looking up the conversion rate
     * @return - The amount converted to USD according to the conversion rate for currency on the
     *         given date.
     */
    public static Double convertToUSD(Double amount, String currency, String date)
    {
        try
        {
            Double conversionRate = CurrencyDBWrapper.getConversion(currency, date);
            return amount * conversionRate;
        }
        catch (KeyNotFoundException noKey)
        {
            throw new TRAPRuntimeException(String.format("Failed to convert currency %s on %s",
                    currency, date));
        }
    }
}
