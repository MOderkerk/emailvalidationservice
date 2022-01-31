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

import de.oderkerk.tools.emailvalidation.logging.Slf4jMDCFilterConfiguration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;


/**
 * Structure of the response
 *
 * @version 1.0
 * @since 30.01.2022
 */
@Getter
@Setter
public class EmailValidationResponse {

    /**
     * Create a new response
     */
    public EmailValidationResponse() {
        validationErrorList = new ArrayList<>();
        this.emailIsValid = true;
        this.uniqueID = MDC.get(Slf4jMDCFilterConfiguration.DEFAULT_MDC_UUID_TOKEN_KEY);
    }

    /**
     * Id of the webrequest to search in the logs for additional informations
     */
    @Schema(description = "unique id found in the logs", name = "uniqueID")
    private String uniqueID;
    /**
     * flag indicating whether a email address is valid or not
     */
    @Schema(description = "flag if the email address is valid or not", name = "emailIsValid", implementation = Boolean.class)
    private boolean emailIsValid;

    /**
     * if a validation returned that the email is invalid, the occured errors are found here
     */
    private List<ValidationError> validationErrorList;


}
