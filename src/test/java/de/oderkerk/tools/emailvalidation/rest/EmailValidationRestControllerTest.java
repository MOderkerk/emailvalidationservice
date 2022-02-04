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

package de.oderkerk.tools.emailvalidation.rest;

import de.oderkerk.tools.emailvalidation.validation.MXLookUpException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmailValidationRestControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateEMailAddressforNull() {
        EmailValidationRestController emailValidationRestController = new EmailValidationRestController();
        EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
        assertThrows(IllegalArgumentException.class, () -> emailValidationRestController.validateEMailAddress(emailValidationRequest));

    }

    @Test
    void validateEMailAddressforBlank() {
        EmailValidationRestController emailValidationRestController = new EmailValidationRestController();
        EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
        emailValidationRequest.setEmailAddress("");
        assertThrows(IllegalArgumentException.class, () -> emailValidationRestController.validateEMailAddress(emailValidationRequest));

    }

    @Test
    void validateEMailAddressOK() throws MXLookUpException, IOException {
        EmailValidationRestController emailValidationRestController = new EmailValidationRestController();
        EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
        emailValidationRequest.setEmailAddress("test@example.org");
        ResponseEntity<EmailValidationResponse> result = emailValidationRestController.validateEMailAddress(emailValidationRequest);
        assertTrue(Objects.requireNonNull(result.getBody()).isEmailIsValid());


    }
}