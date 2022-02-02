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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class IllegalCharacterIdentification {

    private IllegalCharacterIdentification() {
    }
    /**
     * Illegal chars used in recipient
     */
    private static final String REGEX_RECIPIENT = "\\b[|']";
    private static final String REGEX_TLD = "[^A-Za-z0-9]";
    private static final String REGEX_DOMAIN = "\\b[|']";

    /**
     * Check the given String gainst the given rule
     *
     * @param input string to be checked
     * @param rule  rule to be used
     */
    public static void checkString(String input, CheckRuleEnum rule) {
        String regex;
        switch (rule) {
            case RECIPIENT:
                regex = REGEX_RECIPIENT;
                break;
            case TLD:
                regex = REGEX_TLD;
                break;
            case DOMAIN:
                regex = REGEX_DOMAIN;
                break;
            default:
                throw new IllegalArgumentException("Invalid checkrule selected");
        }
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(input);
        if (matcher.find()) throw new IllegalArgumentException("Illegal characters found");


    }
}
