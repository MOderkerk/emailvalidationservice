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

package de.oderkerk.tools.emailvalidation.logging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class Slf4jMDCFilterTest {

    public static final String X_HEADER_TOKEN = "X-Header-Token";
    private MockMvc mockMvc;


    @AfterEach
    void tearDown() {
    }

    @BeforeEach
    void setUp() {
        Slf4jMDCFilter filter = new Slf4jMDCFilter();
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .addFilter(filter)
                .build();
    }

    @RestController
    private class TestController {
        @GetMapping("/test")
        public Map<String, String> test() {
            return MDC.getCopyOfContextMap();
        }
    }

    @Test
    void shouldContainCorrelationId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER))
                .andReturn();
    }

    @Test
    void shouldContainGivenCorrelationId() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/test")
                        .header(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER, "123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(Slf4jMDCFilterConfiguration.DEFAULT_RESPONSE_TOKEN_HEADER))
                .andReturn();
    }

    @Test
    void shouldContainClientIP() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/test")
                        .header("X-Forwarded-For", "127.0.0.1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void shouldProtected() {
        assertFalse(new Slf4jMDCFilter().shouldNotFilterErrorDispatch());
    }

}
