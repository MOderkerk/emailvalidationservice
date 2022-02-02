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
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Class for validation an email address
 *
 * @since 30.01.2022
 */
@Component
@Getter
public class EMailValidator implements Serializable {

    /**
     * recipient part of email
     */
    private String recipient;
    /**
     * domain part of email
     */
    private String domain;
    /**
     * top level Domain of email
     */
    private String tld;
    /**
     * Response to be sent to the user
     */
    private EmailValidationResponse emailValidationResponse;

    /**
     * Class for Validating email addresses
     */
    public EMailValidator() {
        this.emailValidationResponse = new EmailValidationResponse();
    }

    /**
     * Validates an email address . Depending on the boolean flags' additional checks are executed
     *
     * @param emailAddress       address to be validated
     * @param oneTimeMailAllowed flag for performing the check against the known list of one time mail domains
     * @param tryDNSCheck        flag for performing  a dns lookup of the domain
     * @return Result of the validation and if errors have been occurred with the list of errors
     */
    public EmailValidationResponse validateEMailAddress(String emailAddress, boolean oneTimeMailAllowed, boolean tryDNSCheck) {
        splitEMailAddress(emailAddress);



        return null;
    }

    /**
     * tries to split the given email in recipient, domain and tld
     *
     * @param emailAddress to be splitted
     */
    protected void splitEMailAddress(String emailAddress) {

        String[] parts = emailAddress.split("@");
        switch (parts.length) {
            case 1:
                emailValidationResponse.addErrorToResponse(20001, "Email address has no @ sign.");
                break;
            case 2:
                recipient = parts[0];
                splitDomainTld(parts);
                break;
            default:
                emailValidationResponse.addErrorToResponse(20002, "Email address has multiple @ signs.");
        }

    }

    /**
     * Split the Domain part of the email into domain and tld
     *
     * @param parts Separated information of the email address
     */
    protected void splitDomainTld(String[] parts) {
        String domainName = parts[1];
        String[] domainTldSplit = domainName.split("\\.");
        switch (domainTldSplit.length) {
            case 1:
                if (parts[1].endsWith(".")) {
                    emailValidationResponse.addErrorToResponse(20003, "Domain of email address end with a dot. Tld missing");
                } else {
                    emailValidationResponse.addErrorToResponse(20004, "Domain of email address has no dot with in it. ");
                }
                break;
            case 2:
                this.domain = domainTldSplit[0];
                this.tld = domainTldSplit[1];
                break;
            default:
                emailValidationResponse.addErrorToResponse(20002, "Domain of email address has multiple dots.  ");
                break;
        }
    }


}
