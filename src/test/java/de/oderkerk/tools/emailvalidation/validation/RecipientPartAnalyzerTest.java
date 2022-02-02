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

import static org.junit.jupiter.api.Assertions.*;


class RecipientPartAnalyzerTest {
    private final String RECIPIENT_OK = "test";
    private final String RECIPIENT_INVALID_CHARS = "test|'";

    RecipientPartAnalyzer recipientPartAnalyzer;

    @BeforeEach
    void setUp() {
        recipientPartAnalyzer = new RecipientPartAnalyzer(new EmailValidationResponse());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void analyzeOK() {
        EmailValidationResponse emailValidationResponse = recipientPartAnalyzer.analyze(RECIPIENT_OK);
        assertTrue(emailValidationResponse.isEmailIsValid(), "No error must occure");

    }

    @Test
    void analyzeLengthToLong() {
        String input = padLeftChars("Test", 66);
        EmailValidationResponse emailValidationResponse = recipientPartAnalyzer.analyze(input);
        assertFalse(emailValidationResponse.isEmailIsValid());
        assertEquals(1, emailValidationResponse.getValidationErrorList().size(), "One error should be in the error list");
        assertEquals(20005, emailValidationResponse.getValidationErrorList().get(0).getErrorNo(), "Error number must be 20002");
        assertEquals("Recipient part is too long. Length: 66", emailValidationResponse.getValidationErrorList().get(0).getErrorText(), "Error text must be \"Recipient part is too long. Length: 66");

    }

    String padLeftChars(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('A');
        }
        sb.append(inputString);

        return sb.toString();
    }
}