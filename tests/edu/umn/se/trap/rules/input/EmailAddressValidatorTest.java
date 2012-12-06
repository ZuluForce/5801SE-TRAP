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
import edu.umn.se.trap.util.Pair;

/**
 * @author andrewh
 * 
 */
public class EmailAddressValidatorTest extends TrapTestFramework
{
    LoadedSampleForm formData;
    Integer formId;

    TestUserDB.UserEntryBuilder builder;

    @Before
    public void setup() throws TRAPException
    {
        Pair<Integer, LoadedSampleForm> setupData = basicTrapSetup(SampleDataEnum.INTERNATIONAL1);
        formData = setupData.getRight();
        formId = setupData.getLeft();

        // builder = new TestUserDB.UserEntryBuilder();
        builder = userDB.fillBuilderWithUserInfo("helge206");
    }

    @Test
    public void validEmailComDomain() throws TRAPException
    {
        builder.setEmail("helge206@umn.com");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Test
    public void validEmailEduDomain() throws TRAPException
    {
        builder.setEmail("helge206@umn.edu");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void noAtSymbol() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");
        builder.setEmail("helge206umn.edu");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Test
    public void missingDomain() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("helge206@");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Test
    public void missingTLD() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("helge206@umn.");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Test
    public void missingEmailName() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("@umn.com");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Test
    public void invalidEmailName() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("#%helge206@umn.edu");
        userDB.addUser(builder);

        submitFormData(formId);
    }

    @Test
    public void emptyEmail() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Email address is not valid");

        builder.setEmail("");
        userDB.addUser(builder);

        submitFormData(formId);
    }
}
