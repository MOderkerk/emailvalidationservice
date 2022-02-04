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
import org.junit.jupiter.api.Test;
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

    @Test
    void testRestController() throws IOException, URISyntaxException {


        RestTemplate restTemplate = new RestTemplate();

        EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
        emailValidationRequest.setEmailAddress("test@oderkerk.de,false,true");
        HttpEntity<EmailValidationRequest> requestHttpEntity = RequestEntity.post(new URL("http://localhost:" + randomServerPort + "/api/v1/validate").toURI()).contentType(MediaType.APPLICATION_JSON).body(emailValidationRequest);
        ResponseEntity<EmailValidationResponse> response = restTemplate.exchange((RequestEntity<?>) requestHttpEntity, EmailValidationResponse.class);
        assertTrue(Objects.requireNonNull(response.getBody()).isEmailIsValid());
    }

    @Test
    void testRestControllerError() throws IOException, URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        EmailValidationRequest emailValidationRequest = new EmailValidationRequest();
        emailValidationRequest.setEmailAddress("test@o'erkerk.de,false,true");
        HttpEntity<EmailValidationRequest> requestHttpEntity = RequestEntity.post(new URL("http://localhost:" + randomServerPort + "/api/v1/validate").toURI()).contentType(MediaType.APPLICATION_JSON).body(emailValidationRequest);
        ResponseEntity<EmailValidationResponse> response = restTemplate.exchange((RequestEntity<?>) requestHttpEntity, EmailValidationResponse.class);
        assertFalse(Objects.requireNonNull(response.getBody()).isEmailIsValid());
    }


}
