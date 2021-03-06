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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IllegalCharacterIdentificationTest {

    @Test
    void checkRecipientIllegalPipe() {
        assertThrows(IllegalArgumentException.class, () -> IllegalCharacterIdentification.checkString("test|", CheckRuleEnum.RECIPIENT));
    }

    @Test
    void checkRecipientIllegalComma() {
        assertThrows(IllegalArgumentException.class, () -> IllegalCharacterIdentification.checkString("test|", CheckRuleEnum.RECIPIENT));
    }

    @Test
    void checkRecipientIllegalStart() {
        assertThrows(IllegalArgumentException.class, () -> IllegalCharacterIdentification.checkString("[test", CheckRuleEnum.RECIPIENT));
    }

    @Test
    void checkRecipientOk() {
        assertDoesNotThrow(() -> IllegalCharacterIdentification.checkString("test", CheckRuleEnum.RECIPIENT));
    }

    @Test
    void checkTldOK() {
        assertDoesNotThrow(() -> IllegalCharacterIdentification.checkString("test", CheckRuleEnum.TLD));
    }

    @Test
    void checkTldNOK() {
        assertThrows(IllegalArgumentException.class, () -> IllegalCharacterIdentification.checkString("tes|t", CheckRuleEnum.TLD));
    }

    @Test
    void checkTldEndSpecial() {
        assertThrows(IllegalArgumentException.class, () -> IllegalCharacterIdentification.checkString("test]#", CheckRuleEnum.TLD));
    }


}