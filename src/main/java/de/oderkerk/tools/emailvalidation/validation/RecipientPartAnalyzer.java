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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Analyzing the recipient part of an email
 */
@Slf4j
public class RecipientPartAnalyzer {
    /**
     * Response to add errors if found
     */
    EmailValidationResponse emailValidationResponse;

    /**
     * Setup pecipient part analyzer
     *
     * @param emailValidationResponse Result of check added to the response
     */
    public RecipientPartAnalyzer(EmailValidationResponse emailValidationResponse) {
        this.emailValidationResponse = emailValidationResponse;
    }

    /**
     * Analyse the given recipient if an error is found it will be added to the response object
     *
     * @param recipient String to be analyzed
     * @return EmailValidationResponse with error list within it
     */
    public EmailValidationResponse analyze(String recipient) {
        log.debug("Analyze recipient : {}", recipient);
        if (recipient.trim().length() > 64)
            emailValidationResponse.addErrorToResponse(20005, "Recipient part is too long. Length: " + recipient.trim().length());
        try {
            IllegalCharacterIdentification.checkString(recipient, CheckRuleEnum.RECIPIENT);
        } catch (IllegalArgumentException ex) {
            emailValidationResponse.addErrorToResponse(20008, ex.getMessage());
        }
        if (recipient.startsWith(".")|| recipient.endsWith(".")) emailValidationResponse.addErrorToResponse(20006, "Recipient starts or ends with a dot ");
        if (StringUtils.countMatches(recipient,'"')%2 != 0) emailValidationResponse.addErrorToResponse(20015, "Recipient has an unbalanced count of \" ");
        return this.emailValidationResponse;
    }
}
