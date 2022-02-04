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

import lombok.extern.slf4j.Slf4j;
import org.xbill.DNS.Name;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;
import org.xbill.DNS.lookup.LookupSession;

import java.util.concurrent.ExecutionException;

@Slf4j
public class DNSServerLookup {

    /**
     * Perform mx Lookup to check if it will be possible to send an email to this server
     *
     * @param domain to be checked
     * @throws MXLookUpException if domain is not found or another error occured
     */
    public void lookupMX(String domain) throws MXLookUpException {
        log.debug("Perform MX lookup for {}", domain);


        try {

            LookupSession lookupSession = LookupSession.defaultBuilder().build();
            Name mxLookUp = Name.fromString(domain);
            lookupSession.lookupAsync(mxLookUp, Type.MX)
                    .whenComplete((answers, ex) -> {
                        if (ex == null) {
                            log.debug(answers.toString());
                        } else {
                            log.error(ex.toString());
                        }
                    })
                    .toCompletableFuture()
                    .get();


        } catch (TextParseException | InterruptedException | ExecutionException e) {
            log.error(e.toString());

            if (e.toString().contains("NoSuchDomainException")) {
                throw new MXLookUpException("Domain not found");
            } else {
                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                throw new MXLookUpException(e.toString());
            }
        }

    }
}
