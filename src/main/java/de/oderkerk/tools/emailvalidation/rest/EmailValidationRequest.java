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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Structure of the request for validating an email address
 *
 * @version 1.0
 * @since 30.01.2022
 */
@Getter
@Setter
public class EmailValidationRequest implements Serializable {

    /**
     * address to be validated
     */
    @Schema(name = "emailAddress", required = true, description = "Email address to be validated")
    private String emailAddress;

    @Schema(name = "oneTimeMailAllowed", required = true, description = "if true one time email addresses are allowed and not checked. If false it is checked against a blacklist of know one time mail domains")
    private boolean oneTimeMailAllowed;

    @Schema(name = "tryDNSCheck", required = true, description = "if true , the system will check whether the given domain exists")
    private boolean tryDNSCheck;

}
