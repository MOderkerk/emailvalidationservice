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

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class EmailValidationRestControllerAdviceTest {

    @Test
    void testIllegalArgumentException() {
        EmailValidationRestControllerAdvice emailValidationRestControllerAdvice = new EmailValidationRestControllerAdvice();
        ResponseEntity<EmailValidationResponse> result = emailValidationRestControllerAdvice.handleIllegalArgumentException(new IllegalArgumentException("Test"), null);
        if (!Objects.isNull(result.getBody())) {
            if (!result.getBody().isEmailIsValid()) {
                assertEquals("Test", result.getBody().getValidationErrorList().get(0).getErrorText());
                assertEquals(100001, result.getBody().getValidationErrorList().get(0).getErrorNo());
            } else {
                fail();
            }
        }
    }

    @Test
    void testIndexOutoFbOUNDSException() {
        EmailValidationRestControllerAdvice emailValidationRestControllerAdvice = new EmailValidationRestControllerAdvice();
        ResponseEntity<EmailValidationResponse> result = emailValidationRestControllerAdvice.handleIndexOutOfBoundsException(new IndexOutOfBoundsException("Test"), null);
        if (!Objects.isNull(result.getBody())) {
            if (!result.getBody().isEmailIsValid()) {
                assertEquals("Test", result.getBody().getValidationErrorList().get(0).getErrorText());
                assertEquals(999999, result.getBody().getValidationErrorList().get(0).getErrorNo());
            } else {
                fail();
            }
        }
    }

    @Test
    void testIOException() {
        EmailValidationRestControllerAdvice emailValidationRestControllerAdvice = new EmailValidationRestControllerAdvice();
        ResponseEntity<EmailValidationResponse> result = emailValidationRestControllerAdvice.handleBlacklistIOException(new IOException("Test"), null);
        if (!Objects.isNull(result.getBody())) {
            if (!result.getBody().isEmailIsValid()) {
                assertEquals("Test", result.getBody().getValidationErrorList().get(0).getErrorText());
                assertEquals(999999, result.getBody().getValidationErrorList().get(0).getErrorNo());
            } else {
                fail();
            }
        }
    }

}