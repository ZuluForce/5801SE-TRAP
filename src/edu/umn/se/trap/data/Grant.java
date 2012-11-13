/**
 * 
 */
package edu.umn.se.trap.data;

/**
 * @author planeman
 * 
 */
public class Grant
{
    private String grantAccount;
    private float grantPercentage;
    private float grantCharge;

    public String getGrantAccount()
    {
        return grantAccount;
    }

    public void setGrantAccount(String grantAccount)
    {
        this.grantAccount = grantAccount;
    }

    public float getGrantPercentage()
    {
        return grantPercentage;
    }

    public void setGrantPercentage(float grantPercentage)
    {
        this.grantPercentage = grantPercentage;
    }

    public float getGrantCharge()
    {
        return grantCharge;
    }

    public void addGrantCharge(float grantCharge)
    {
        this.grantCharge += grantCharge;
    }

}
