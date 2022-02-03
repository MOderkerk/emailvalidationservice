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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Analyzer to check if a domain is for onetime disposal mails
 */
@Slf4j
public class OnetimeMailChecker {

    private OnetimeMailChecker() {
        //nothing to do
    }

    /**
     * Blacklist for check
     */
    private static List<String> blacklist = new ArrayList<>();

    /**
     * Check if a domain is in the blacklist
     *
     * @param domain domain incl tdl to be checked
     * @return true = blacklisted false = not blacklisted
     * @throws IOException file not found
     */
    public static boolean isListedInBlacklist(String domain) throws IOException {
        log.debug("Check {} if in blacklist", domain);
        if (blacklist.isEmpty()) readBlacklist();
        return blacklist.stream().anyMatch(Predicate.isEqual(domain));
    }

    private static void readBlacklist() throws IOException {
        log.info("Blacklist is empty. Maybe first access. Let's load the actual blacklist");
        BlacklistLoader blacklistLoader = new BlacklistLoader();
        blacklist = blacklistLoader.readBlacklist();
        log.info("{} entries loaded into blacklist", blacklist.size());
    }


}
