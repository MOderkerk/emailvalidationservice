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

package de.oderkerk.tools.emailvalidation;

import de.oderkerk.tools.emailvalidation.rest.EmailValidationRequest;
import de.oderkerk.tools.emailvalidation.rest.EmailValidationResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestITTest {

    @LocalServerPort
    int randomServerPort;


    @ParameterizedTest
    @CsvSource({
            "test@oderkerk.de",
            "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org"
    })
    void testRestControllerOK(String testcase) throws IOException, URISyntaxException {


        RestTemplate restTemplate = new RestTemplate();

        EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
        emailValidationRequest.setEmailAddress(testcase);
        HttpEntity<EmailValidationRequest> requestHttpEntity = RequestEntity.post(new URL("http://localhost:" + randomServerPort + "/api/v1/validate").toURI()).contentType(MediaType.APPLICATION_JSON).body(emailValidationRequest);
        ResponseEntity<EmailValidationResponse> response = restTemplate.exchange((RequestEntity<?>) requestHttpEntity, EmailValidationResponse.class);
        assertTrue(Objects.requireNonNull(response.getBody()).isEmailIsValid());
    }

    @ParameterizedTest
    @CsvSource({
            "test@ode'rkerk.de",
            "@oderkerk.de",
            "test@.de",
            "test",
            "@",
            "test@",
            "@io",
            "@oderkerk.org",
            ".test@oderkerk.org",
            "test.@oderkerk.org",
            "test..oderkerk.org",
            "test_exa-mple.com",
            "test\\@test@oderkerk.org",
            "test@-oderkerk.org",
            "test@oderkerk-.com",
            "test@.oderkerk.org",
            "test@oderkerk.org.",
            "test@oderkerk..com",
            "\"\"\"@oderkerk.org",
            "\"@oderkerk.org",
            "test\"@oderkerk.org",
            "test@a[255.255.255.255]",
            "test@255.255.255.255",
            "((comment)test@oderkerk.org",
            "test(comment)test@oderkerk.org",
            "test@oderkerk.org?",
            "test@oderkerk.org-",
            "(test@oderkerk.org",
            "test@(oderkerk.org",
            "test@[1.2.3.4",
            "test\"@oderkerk.org",
            "test@?.org",
            "??test@oderkerk.org",
            "?? ??test@oderkerk.org",
            " ??test@oderkerk.org",
            " ?? ??test@oderkerk.org",
            " ????test@oderkerk.org",
            " ???? test@oderkerk.org",
            "test@oderkerk.org??",
            "test@oderkerk.org?? ??",
            "test@oderkerk.org ??",
            "test@oderkerk.org ?? ??",
            "test@oderkerk.org ????",
            "test@oderkerk.org ???? ",
            "test\\©\"@oderkerk.org"


                })
                void testRestControllerInvalid(String testcase) throws IOException, URISyntaxException {


                    RestTemplate restTemplate = new RestTemplate();

                    EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
                    emailValidationRequest.setEmailAddress(testcase);
                    HttpEntity<EmailValidationRequest> requestHttpEntity = RequestEntity.post(new URL("http://localhost:" + randomServerPort + "/api/v1/validate").toURI()).contentType(MediaType.APPLICATION_JSON).body(emailValidationRequest);
                    ResponseEntity<EmailValidationResponse> response = restTemplate.exchange((RequestEntity<?>) requestHttpEntity, EmailValidationResponse.class);
                    assertFalse(Objects.requireNonNull(response.getBody()).isEmailIsValid());
                }


            }
