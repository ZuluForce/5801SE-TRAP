/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
// EmailAddressValidatorTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TestUserDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * TODO: Requirement<br/>
 * 
 * @author andrewh
 * 
 */
public class EmailAddressValidatorTest extends TrapTestFramework
{
    LoadedSampleForm formData;
    Integer formId;

    TestUserDB.UserEntryBuilder builder;

    /**
     * Load a sample form.
     * 
     * @throws TRAPException when saving the form fails.
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);

        builder = userDB.fillBuilderWithUserInfo("helge206");
    }

    /**
     * Check that an email in the com domain is accepted. There aren't supposed to be any checks on
     * the domain but this is just a check. This isn't a valid address but the format is correct.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void validEmailComDomain() throws TRAPException
    {
        builder.setEmail("helge206@umn.com");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    /**
     * Check that an email in the edu domain is accepted. Once again, there shouldn't be any
     * validation of the addresses or the domain.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void validEmailEduDomain() throws TRAPException
    {
        builder.setEmail("helge206@umn.edu");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that processing fails when the @ symbol is missing in the email.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void noAtSymbol() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");
        builder.setEmail("helge206umn.edu");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when there is no domain specified at all (tld or host).
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void missingDomain() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("helge206@");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when the tld for an email is missing.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void missingTLD() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("helge206@umn.");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when the name is missing from the email.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void missingEmailName() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("@umn.com");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when there are bad characters in the address.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void invalidEmailName() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("#%helge206@umn.edu");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when an empty email is provided.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void emptyEmail() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("");
        userDB.addUser(builder);

        saveAndSubmitTestForm();
    }
}
