// TestUserDB.java
/**
 * Copyright (c) 2012, Ian De Silva, Gregory Gay All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer. - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. - Neither the name of the University of Minnesota nor
 * the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.umn.se.test.frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.UserDB;

/**
 * Mimics the X500 database.
 * 
 * @author desilva
 * 
 */
public class TestUserDB extends UserDB
{
    /**
     * This type enumerates the fields of the {@link ArrayList} with the user information.
     */
    public static enum USER_FIELDS
    {
        USER_NAME, /* X500 user name */
        FULL_NAME, /* Full name of user */
        EMAIL, /* E-mail address of user */
        EMPLOYEE_ID, /* ID number */
        CITIZENSHIP, /* Citizenship of the user */
        VISA_STATUS, /* US visa status if not a US citizenship */
        PAID_BY_UNIVERSITY /* Are they paid by the university? */
    };

    Map<String, List<String>> userInfo = new HashMap<String, List<String>>();

    public class UserEntryBuilder
    {
        private String username;
        private String fullname;
        private String email;
        private String id;
        private String citizenship;
        private String visaStatus;
        private String paidByUniversity;

        /**
         * @return the username
         */
        public String getUsername()
        {
            return username;
        }

        /**
         * @param username the username to set
         */
        public void setUsername(String username)
        {
            this.username = username;
        }

        /**
         * @return the fullname
         */
        public String getFullname()
        {
            return fullname;
        }

        /**
         * @param fullname the fullname to set
         */
        public void setFullname(String fullname)
        {
            this.fullname = fullname;
        }

        /**
         * @return the email
         */
        public String getEmail()
        {
            return email;
        }

        /**
         * @param email the email to set
         */
        public void setEmail(String email)
        {
            this.email = email;
        }

        /**
         * @return the id
         */
        public String getId()
        {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id)
        {
            this.id = id;
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
    }

    /**
     * Constructor. Sets up the object.
     */
    public TestUserDB()
    {
        ArrayList<String> user = new ArrayList<String>();
        user.add("linc001");
        user.add("Lincoln, Abraham");
        user.add("linc001@umn.edu");
        user.add("1849304");
        user.add("United States");
        user.add(null);
        user.add("Yes");

        userInfo.put(user.get(USER_FIELDS.USER_NAME.ordinal()), user);

        UserEntryBuilder builder = new UserEntryBuilder();
        builder.setUsername("helge206");
        builder.setFullname("Helgeson, Andrew");
        builder.setEmail("helge206@umn.edu");
        builder.setCitizenship("United States");
        builder.setPaidByUniversity("No");

        addUser(builder);
    }

    public void addUser(UserEntryBuilder builder)
    {
        ArrayList<String> user = new ArrayList<String>();
        user.add(builder.getUsername());
        user.add(builder.getFullname());
        user.add(builder.getEmail());
        user.add(builder.getId());
        user.add(builder.getCitizenship());
        user.add(builder.getPaidByUniversity());

        userInfo.put(builder.getUsername(), user);
    }

    public void removeUser(String username) throws KeyNotFoundException
    {
        List<String> user = userInfo.remove(username);
        if (user == null)
        {
            throw new KeyNotFoundException("Could not find user, " + username + ", in user DB.");
        }
    }

    /**
     * Gets the user's information as a list of strings.
     * 
     * @param userName the X500 user id of the person to be retrieved from the system.
     * @return a list containing the user's infomation. This contains the X500 id, user's real name
     *         (in Last, First (MI.) form), the user's e-mail address, and the user's employee id
     *         number. A null or empty list returned from this method should be treated as an
     *         invalid user name.
     * @throws KeyNotFoundException if the specified user name could not be found in the database.
     */
    public List<String> getUserInfo(String userName) throws KeyNotFoundException
    {
        List<String> userInfo = this.userInfo.get(userName.toLowerCase());
        if (userInfo == null)
        {
            throw new KeyNotFoundException("Could not find user, " + userName + ", in user DB.");
        }
        return userInfo;
    }
}
