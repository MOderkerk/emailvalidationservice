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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Error handling for invalid requests and exception handler
 */
@RestControllerAdvice
public class EmailValidationRestControllerAdvice {

    /**
     * Handle Illegal argument execptions thrown if the email in the request is missing
     *
     * @param iex        illegal argument exception
     * @param webRequest webRequest of the api call
     * @return ResponseEntity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<EmailValidationResponse> handleIllegalArgumentException(IllegalArgumentException iex, WebRequest webRequest) {
        ValidationError validationError = new ValidationError(100001, iex.getMessage());
        EmailValidationResponse emailValidationResponse = new EmailValidationResponse();
        emailValidationResponse.setEmailIsValid(false);
        emailValidationResponse.getValidationErrorList().add(validationError);
        return new ResponseEntity<>(emailValidationResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Index out of Bounds exception thrown during validation or if an error occurred. In all cases it is an programming error
     *
     * @param iex        Index out Of Bounds Exception
     * @param webRequest webRequest of the api call
     * @return ResponseEntity
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<EmailValidationResponse> handleIndexOutOfBoundsException(IndexOutOfBoundsException iex, WebRequest webRequest) {
        ValidationError validationError = new ValidationError(999999, iex.getMessage());
        EmailValidationResponse emailValidationResponse = new EmailValidationResponse();
        emailValidationResponse.setEmailIsValid(false);
        emailValidationResponse.getValidationErrorList().add(validationError);
        return new ResponseEntity<>(emailValidationResponse, HttpStatus.BAD_REQUEST);
    }
}
