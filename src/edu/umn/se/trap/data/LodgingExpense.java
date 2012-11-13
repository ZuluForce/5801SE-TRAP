/**
 * 
 */
package edu.umn.se.trap.data;

/**
 * @author planeman
 * 
 */
public class LodgingExpense
{
    private String city;
    private String state;
    private String country;
    private float expenseAmount;
    private String expenseCurrency;

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public float getExpenseAmount()
    {
        return expenseAmount;
    }

    public void setExpenseAmount(float expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseCurrency()
    {
        return expenseCurrency;
    }

    public void setExpenseCurrency(String expenseCurrency)
    {
        this.expenseCurrency = expenseCurrency;
    }

}
