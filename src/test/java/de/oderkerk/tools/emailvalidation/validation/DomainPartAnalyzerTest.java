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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainPartAnalyzerTest {

    private final String DOMAIN_OK = "test";
    private final String DOMAIN_INVALID_CHARS = "test|'";
    private final String DOMAIN_INVALID_CHARS_END = "test#'";

    DomainPartAnalyzer domainPartAnalyzer;

    @BeforeEach
    void setUp() {
        domainPartAnalyzer = new DomainPartAnalyzer(new EmailValidationResponse());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void analyzeOK() {
        EmailValidationResponse emailValidationResponse = domainPartAnalyzer.analyze(DOMAIN_OK);
        assertTrue(emailValidationResponse.isEmailIsValid());

    }

    @Test
    void analyzeINVALID() {
        EmailValidationResponse emailValidationResponse = domainPartAnalyzer.analyze(DOMAIN_INVALID_CHARS);
        assertFalse(emailValidationResponse.isEmailIsValid());

    }

    @Test
    void analyzeINVALIDCharEnd() {
        EmailValidationResponse emailValidationResponse = domainPartAnalyzer.analyze(DOMAIN_INVALID_CHARS_END);
        assertTrue(emailValidationResponse.isEmailIsValid());

    }

    @Test
    void analyzeLengthToLong() {
        String input = TestDataHelper.padLeftChars("Test", 300);
        EmailValidationResponse emailValidationResponse = domainPartAnalyzer.analyze(input);
        assertFalse(emailValidationResponse.isEmailIsValid());
    }


}