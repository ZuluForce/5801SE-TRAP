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
