/**
 * 
 */
package edu.umn.se.trap.data;

/**
 * @author planeman
 * 
 */
public class MealExpense
{
    private MealTypeEnum type;
    private String city;
    private String state;
    private String country;

    public MealTypeEnum getType()
    {
        return type;
    }

    public void setType(MealTypeEnum type)
    {
        this.type = type;
    }

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

}
