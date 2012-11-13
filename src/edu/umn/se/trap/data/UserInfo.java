/**
 * 
 */
package edu.umn.se.trap.data;

/**
 * @author planeman
 * 
 */
public class UserInfo
{
    private String username;
    private String emailAddress;
    private String emergencyContactName;
    private String emergencycontactPhone;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmergencyContactName()
    {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName)
    {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencycontactPhone()
    {
        return emergencycontactPhone;
    }

    public void setEmergencycontactPhone(String emergencycontactPhone)
    {
        this.emergencycontactPhone = emergencycontactPhone;
    }

}
