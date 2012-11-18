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
package edu.umn.se.trap.data;

/**
 * A data object class representing the necessary information related to grant.
 * 
 * @author planeman
 * 
 */
public class Grant
{
    /** Name of the grant account */
    private String grantAccount;

    /** Percentage of the reimbursement total this grant is responsible for */
    private Integer grantPercentage;

    /** The amount this grant is to be charged for this reimbursement */
    private Double grantCharge;

    /** The final balance of the grant after the app is finished processing */
    private Double finalBalance;

    /**
     * Construct the Grant object. This initializes all attributes.
     */
    public Grant()
    {
        grantAccount = null;
        grantPercentage = 0;
        grantCharge = finalBalance = -1.0;
    }

    /**
     * Get the grant account name
     * 
     * @see #setGrantAccount(String)
     * @return - The grant account name
     */
    public String getGrantAccount()
    {
        return grantAccount;
    }

    /**
     * Set the grant account name
     * 
     * @see #getGrantAccount()
     * @param grantAccount - The grant account name
     */
    public void setGrantAccount(String grantAccount)
    {
        this.grantAccount = grantAccount;
    }

    /**
     * Get the percentage of the reimbursement total that this grant is responsible for.
     * 
     * @see #setGrantPercentage(int)
     * @return - the percentage this grant is responsible for
     */
    public Integer getGrantPercentage()
    {
        return grantPercentage;
    }

    /**
     * Set the percentage of the reimbursement total that this grant is responsible for.
     * 
     * @see #getGrantPercentage()
     * @param grantPercentage - The percentage this grant is responsible for
     */
    public void setGrantPercentage(int grantPercentage)
    {
        this.grantPercentage = grantPercentage;
    }

    /**
     * Get the amount that is set to be charged to this grant. This is determined after all
     * processing is completed and the grant percentage is applied to the total.
     * 
     * @see #setGrantCharge(Double)
     * @return - The amount this grant is responsible for.
     */
    public Double getGrantCharge()
    {
        return grantCharge;
    }

    /**
     * Set the amount this grant is responsible for in this reimbursement app.
     * 
     * @see #getGrantCharge()
     * @param grantCharge - The amount this grant is being charged in this app.
     */
    public void setGrantCharge(Double grantCharge)
    {
        this.grantCharge = grantCharge;
    }

    /**
     * What is the final balance for this grant after the app is completed and funds are earmarked
     * or taken? This will answer that.
     * 
     * @return - The final balance for the grant after the app is completed.
     */
    public Double getFinalBalance()
    {
        return finalBalance;
    }

    /**
     * Set the final balance for the grant. This balance is after the app is completed and funds are
     * earmarked or taken.
     * 
     * @param finalBalance - The final balance for the grant
     */
    public void setFinalBalance(Double finalBalance)
    {
        this.finalBalance = finalBalance;
    }

    /**
     * Create a string representation of this Grant object.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Grant:\n");
        sb.append(String.format("\tAccount: %s\n", grantAccount));
        sb.append(String.format("\tPercent: %f%%\n", grantPercentage));
        sb.append(String.format("\tCharge: $%f", grantCharge));

        return sb.toString();
    }

}
