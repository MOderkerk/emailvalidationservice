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

import de.oderkerk.tools.emailvalidation.validation.EMailValidator;
import de.oderkerk.tools.emailvalidation.validation.MXLookUpException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j
public class EmailValidationRestController implements Serializable {


    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "No email address given in the request"),
            @ApiResponse(responseCode = "200", description = "EMail Address has been checked and the result of the validation has been send in the response")})
    @Operation(summary = "Validate email address", description = "Function to perform a validation of a email address ")
    @PostMapping(path = "validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailValidationResponse> validateEMailAddress(@RequestBody EmailValidationRequest emailValidationRequest) throws MXLookUpException, IOException {
        log.debug("Starting validation for {}", emailValidationRequest.toString());
        checkRequestData(emailValidationRequest);
        EMailValidator eMailValidator = new EMailValidator();
        EmailValidationResponse emailValidationResponse = eMailValidator.validateEMailAddress(emailValidationRequest.getEmailAddress(), emailValidationRequest.isOneTimeMailAllowed(), emailValidationRequest.isTryDNSCheck());
        log.debug("Result of Validation of email {} : {}", emailValidationRequest.getEmailAddress(), emailValidationResponse.toString());
        return new ResponseEntity<>(emailValidationResponse, HttpStatus.OK);
    }

    /**
     * Internal method for checking the request data
     *
     * @param emailValidationRequest to be checked
     */
    private void checkRequestData(EmailValidationRequest emailValidationRequest) {
        if (Objects.isNull(emailValidationRequest.getEmailAddress()) || emailValidationRequest.getEmailAddress().isBlank())
            throw new IllegalArgumentException("Email Address is missing");
    }
}
