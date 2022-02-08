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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class for validation an email address
 *
 * @since 30.01.2022
 */
@Component
@Getter
@Slf4j
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
     * domain adn toplevel
     */
    private String domainWithTld;
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
     * @throws IOException blacklist not found
     */
    public EmailValidationResponse validateEMailAddress(String emailAddress, boolean oneTimeMailAllowed, boolean tryDNSCheck) throws IOException, MXLookUpException {
        log.debug("Starting validation of {} with onetimemailcheck ={} and dnscheck={}", emailAddress, oneTimeMailAllowed, tryDNSCheck);
        splitEMailAddress(emailAddress);
        log.debug("Result of spilt : Recipient={} Domain={}, TLD={}", getRecipient(), getDomain(), getTld());
        if (this.emailValidationResponse.isEmailIsValid()) {
            this.emailValidationResponse = new RecipientPartAnalyzer(emailValidationResponse).analyze(getRecipient());
            this.emailValidationResponse = new DomainPartAnalyzer(emailValidationResponse).analyze(getDomain());
        }
        if (!oneTimeMailAllowed && OnetimeMailChecker.isListedInBlacklist(domainWithTld)) {
            this.emailValidationResponse.addErrorToResponse(200020, "EMail is a disposal or onetime mail and on the domain blacklist");
        }
        if (tryDNSCheck) {
            try {
                DNSServerLookup lookup = new DNSServerLookup();
                lookup.lookupMX(domainWithTld);
            } catch (MXLookUpException ex) {
                if ("Domain not found".equals(ex.getMessage()))
                    this.emailValidationResponse.addErrorToResponse(200022, ex.toString());
                else throw ex;
            }
        }
        return this.emailValidationResponse;
    }


    /**
     * tries to split the given email in recipient, domain and tld
     *
     * @param emailAddress to be splitted
     */
    protected void splitEMailAddress(String emailAddress) {
        log.debug("Start splitting email : {}", emailAddress);
        String[] parts = emailAddress.split("@", -1);
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
        if (emailValidationResponse.isEmailIsValid()) {
            if (recipient.isBlank())
                emailValidationResponse.addErrorToResponse(20009, "Email address has no recipient.");
            if (domain.isBlank()) emailValidationResponse.addErrorToResponse(20012, "Email address has no domain.");
        }


    }

    /**
     * Split the Domain part of the email into domain and tld
     *
     * @param parts Separated information of the email address
     */
    protected void splitDomainTld(String[] parts) {
        log.debug("Split domain into domain and TLD");
        String domainName = parts[1];
        domainWithTld = domainName;
        int posOfFirstDot = domainName.indexOf('.');
        if (posOfFirstDot == -1) {
            emailValidationResponse.addErrorToResponse(20004, "Domain of email address has no dot with in it. ");
        } else {
            String[] domainTldSplit = new String[2];
            domainTldSplit[0] = domainName.substring(0, posOfFirstDot);
            domainTldSplit[1] = domainName.substring(posOfFirstDot + 1);
            log.debug("splitted domain : {} TLD: {}", domainTldSplit[0], domainTldSplit[1]);


            if (parts[1].endsWith(".")) {
                emailValidationResponse.addErrorToResponse(20003, "Domain of email address end with a dot. Tld missing");
            } else {
                this.domain = domainTldSplit[0];
                this.tld = domainTldSplit[1];
            }
        }
    }
}



