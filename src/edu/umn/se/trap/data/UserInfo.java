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
 * A data object representing information about the user submitting the form.
 * 
 * @author andrewh
 * 
 */
public class UserInfo
{
    /** username specified on the submitted form */
    private String username;

    /** The emergency contact name for the user */
    private String emergencyContactName;

    /** The emergency contact phone number for the user */
    private String emergencycontactPhone;

    // Extra info obtained from the DB
    /** The email address of the user */
    private String emailAddress;

    /** Full name: {lastname} [name_suffix], {firstname} [middle] */
    private String fullName;

    private String citizenship;

    /** Visa status. Will be null for U.S. citizen */
    private String visaStatus;

    /** Is the user paid by the university. Yes/No */
    private String paidByUniversity;

    /**
     * Get the form's username
     * 
     * @return - The username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Set the username
     * 
     * @param username - Username to set.
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Get the email address for the user.
     * 
     * @return - The email address of the user
     */
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * Set the email address for the user.
     * 
     * @param emailAddress - The email address to set for the user
     */
    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    /**
     * Get the emergency contact name for the user.
     * 
     * @return - The emergency contact name for the user.
     */
    public String getEmergencyContactName()
    {
        return emergencyContactName;
    }

    /**
     * Set the emergency contact name for the user.
     * 
     * @param emergencyContactName - The emergency contact name to set for the user
     */
    public void setEmergencyContactName(String emergencyContactName)
    {
        this.emergencyContactName = emergencyContactName;
    }

    /**
     * Get the emergency contact phone number for the user.
     * 
     * @return - The emergency contact phone number for the user.
     */
    public String getEmergencycontactPhone()
    {
        return emergencycontactPhone;
    }

    /**
     * Set the emergency contact phone number for the user.
     * 
     * @param emergencycontactPhone - The emergency contact phone number to set for the user.
     */
    public void setEmergencycontactPhone(String emergencycontactPhone)
    {
        this.emergencycontactPhone = emergencycontactPhone;
    }

    /**
     * @return the fullName
     */
    public String getFullName()
    {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    /**
     * @return the visaStatus
     */
    public String getVisaStatus()
    {
        return visaStatus;
    }

    /**
     * @param visaStatus the visaStatus to set
     */
    public void setVisaStatus(String visaStatus)
    {
        this.visaStatus = visaStatus;
    }

    /**
     * @return the paidByUniversity
     */
    public String getPaidByUniversity()
    {
        return paidByUniversity;
    }

    /**
     * @param paidByUniversity the paidByUniversity to set
     */
    public void setPaidByUniversity(String paidByUniversity)
    {
        this.paidByUniversity = paidByUniversity;
    }

    /**
     * @return the citizenship
     */
    public String getCitizenship()
    {
        return citizenship;
    }

    /**
     * @param citizenship the citizenship to set
     */
    public void setCitizenship(String citizenship)
    {
        this.citizenship = citizenship;
    }

    /**
     * Create a string representation of this object.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("User Info:\n");
        sb.append(String.format("\tUsername: %s\n", username));
        sb.append(String.format("\tEmail: %s\n", emailAddress));
        sb.append(String.format("\tEmergency Name: %s\n", emergencyContactName));
        sb.append(String.format("\tEmergency Phone: %s\n", emergencycontactPhone));

        return sb.toString();
    }

}
