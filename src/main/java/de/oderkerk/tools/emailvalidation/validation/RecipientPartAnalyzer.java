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

/**
 * Analyzing the recipient part of an email
 */
public class RecipientPartAnalyzer {
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
        if (recipient.trim().length() > 64)
            emailValidationResponse.addErrorToResponse(20005, "Recipient part is too long. Length: " + recipient.trim().length());
        return this.emailValidationResponse;
    }
}
