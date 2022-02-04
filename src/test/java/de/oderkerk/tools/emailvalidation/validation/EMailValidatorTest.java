/*
 * Copyright (c) 2022.  Marco Oderkerk
 * Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.oderkerk.tools.emailvalidation.validation;

import de.oderkerk.tools.emailvalidation.rest.EmailValidationResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class EMailValidatorTest {

    private EMailValidator eMailValidator;
    private final String VALID_EMAIL = "max.mustermann@oderkerk.de";
    private final String INVALID_EMAIL_AT_MISSING = "max.mustermannoderkerk.de";
    private final String INVALID_EMAIL_MULTIPLE_AT = "max.mustermann@@oderkerk.de";
    private final String[] SPLIT_EMAIL_DATA_OK = new String[]{"max.mustermann", "oderkerk.de"};
    private final String[] SPLIT_EMAIL_DATA_NO_DOT_IN_DOMAIN = new String[]{"max.mustermann", "oderkerkde"};
    private final String[] SPLIT_EMAIL_DATA_DOMAIN_ENDS_WITH_DOT = new String[]{"max.mustermann", "oderkerk."};

    @BeforeEach
    void setUp() {
        eMailValidator = new EMailValidator();
    }

    @AfterEach
    void tearDown() {
        eMailValidator = new EMailValidator();
    }

    @Test
    void validateEMailAddress() {
        //Comming soon
        assertTrue(true);
    }

    @Test
    void splitEMailAddress() {
        eMailValidator.splitEMailAddress(VALID_EMAIL);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertTrue(emailValidationResponse.isEmailIsValid(), "Email should be valid ");
    }

    @Test
    void splitEMailAddressHasNoAT() {
        eMailValidator.splitEMailAddress(INVALID_EMAIL_AT_MISSING);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertFalse(emailValidationResponse.isEmailIsValid(), "Email should be invalid ");
        assertEquals(1, emailValidationResponse.getValidationErrorList().size(), "One error should be in the error list");
        assertEquals(20001, emailValidationResponse.getValidationErrorList().get(0).getErrorNo(), "Error number must be 20001");
        assertEquals("Email address has no @ sign.", emailValidationResponse.getValidationErrorList().get(0).getErrorText(), "Error text must be \"Email address has no @ sign.\"");

    }

    @Test
    void splitEMailAddressHasMultipleATs() {
        eMailValidator.splitEMailAddress(INVALID_EMAIL_MULTIPLE_AT);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertFalse(emailValidationResponse.isEmailIsValid(), "Email should be invalid ");
        assertEquals(1, emailValidationResponse.getValidationErrorList().size(), "One error should be in the error list");
        assertEquals(20002, emailValidationResponse.getValidationErrorList().get(0).getErrorNo(), "Error number must be 20002");
        assertEquals("Email address has multiple @ signs.", emailValidationResponse.getValidationErrorList().get(0).getErrorText(), "Error text must be \"Email address has multiple @ signs.\"");

    }

    @Test
    void splitDomainTldIsOK() {
        eMailValidator.splitDomainTld(SPLIT_EMAIL_DATA_OK);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertTrue(emailValidationResponse.isEmailIsValid(), "Email should be valid ");
    }

    @Test
    void splitDomainTldIsDomainDotMissing() {
        eMailValidator.splitDomainTld(SPLIT_EMAIL_DATA_NO_DOT_IN_DOMAIN);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertFalse(emailValidationResponse.isEmailIsValid(), "Email should be invalid ");
        assertEquals(1, emailValidationResponse.getValidationErrorList().size(), "One error should be in the error list");
        assertEquals(20004, emailValidationResponse.getValidationErrorList().get(0).getErrorNo(), "Error number must be 20004");
        assertEquals("Domain of email address has no dot with in it. ", emailValidationResponse.getValidationErrorList().get(0).getErrorText(), "Error text must be \"Domain of email address has no dot with in it.\"");
    }

    @Test
    void splitDomainTldIsDomainEndWithDot() {
        eMailValidator.splitDomainTld(SPLIT_EMAIL_DATA_DOMAIN_ENDS_WITH_DOT);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertFalse(emailValidationResponse.isEmailIsValid(), "Email should be invalid ");
        assertEquals(1, emailValidationResponse.getValidationErrorList().size(), "One error should be in the error list");
        assertEquals(20003, emailValidationResponse.getValidationErrorList().get(0).getErrorNo(), "Error number must be 20003");
        assertEquals("Domain of email address end with a dot. Tld missing", emailValidationResponse.getValidationErrorList().get(0).getErrorText(), "Error text must be \"Domain of email address end with a dot. Tld missing\"");
    }

    @Test
    void testMailOk() throws IOException, MXLookUpException {
        eMailValidator.validateEMailAddress("test@oderkerk.de", false, false);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertTrue(emailValidationResponse.isEmailIsValid(), "Email should be valid ");
    }

    @Test
    void testMailOkWithoutBlacklist() throws IOException, MXLookUpException {
        eMailValidator.validateEMailAddress("test@oderkerk.de", true, false);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertTrue(emailValidationResponse.isEmailIsValid(), "Email should be valid ");
    }

    @Test
    void testMailNOk() throws IOException, MXLookUpException {
        eMailValidator.validateEMailAddress("test@od|erkerk.de", false, false);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertFalse(emailValidationResponse.isEmailIsValid(), "Email should be invalid ");
    }

    @Test
    void testMailBlacklisted() throws IOException, MXLookUpException {
        eMailValidator.validateEMailAddress("test@thisurl.website", false, false);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertFalse(emailValidationResponse.isEmailIsValid(), "Email should be blacklisted ");
    }

    @Test
    void testMailOnetimeMailButCheckDeaktivated() throws IOException, MXLookUpException {
        eMailValidator.validateEMailAddress("test@thisurl.website", true, false);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertTrue(emailValidationResponse.isEmailIsValid(), "Blacklist should not be checked");
    }

    @Test
    void testMailOKWithDNSCheck() throws IOException, MXLookUpException {
        eMailValidator.validateEMailAddress(VALID_EMAIL, false, true);
        EmailValidationResponse emailValidationResponse = eMailValidator.getEmailValidationResponse();
        assertTrue(emailValidationResponse.isEmailIsValid(), "Email should be valid ");
    }
}