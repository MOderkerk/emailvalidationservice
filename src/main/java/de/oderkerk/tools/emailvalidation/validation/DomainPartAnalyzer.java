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

/**
 * Analyzing the domain part of an email
 */
@Slf4j
public class DomainPartAnalyzer {
    EmailValidationResponse emailValidationResponse;

    public DomainPartAnalyzer(EmailValidationResponse emailValidationResponse) {
        this.emailValidationResponse = emailValidationResponse;
    }

    /**
     * Analyse the given recipient if an error is found it will be added to the response object
     *
     * @param domain String to be analyzed
     * @return EmailValidationResponse with error list within it
     */
    public EmailValidationResponse analyze(String domain) {
        log.debug("Analyze recipient : {}", domain);
        if (domain.trim().length() > 253)
            emailValidationResponse.addErrorToResponse(20010, "Domain part is too long. Length: " + domain.trim().length());
        try {
            IllegalCharacterIdentification.checkString(domain, CheckRuleEnum.DOMAIN);
        } catch (IllegalArgumentException ex) {
            emailValidationResponse.addErrorToResponse(20011, ex.getMessage());
        }
        if (domain.startsWith("-")|| domain.endsWith("-")) emailValidationResponse.addErrorToResponse(20013, "Domain starts or ends with a dash ");
        return this.emailValidationResponse;
    }
}
