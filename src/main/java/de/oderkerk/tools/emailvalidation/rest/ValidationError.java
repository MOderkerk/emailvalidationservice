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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Structure of validation addresses
 */
@Schema(name = "ValidationError", description = "Detail information of validation errors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError implements Serializable {

    /**
     * Error number of the error occurred
     */
    @Schema(name = "errorNo", description = "unique error number ")
    private int errorNo;

    /**
     * Error text with additional information of the error
     */
    @Schema(name = "errorText", description = "additional information of the error ")
    private String errorText;


}
