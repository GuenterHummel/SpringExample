package com.gh.demoserver.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloRestControllerFunctionalTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void autowiringWorked(@LocalServerPort int port)
    {
        assertNotNull(restTemplate);
        System.out.println("Test server  running on port: " +  port);
    }

    @Test
    void greetWithoutName() {
        Greeting greeting = restTemplate.getForObject("/rest", Greeting.class);

        assertAll(
                () -> assertNotNull(greeting),
                () -> assertEquals("Hello, World!", greeting.message())
        );
    }

    @Test
    void greetWithName() {
        ResponseEntity<Greeting> response = restTemplate.getForEntity("/rest?name={name}", Greeting.class, "Dolly");

        assertAll(
                () -> assertTrue(response.getStatusCode().is2xxSuccessful()),
                () -> assertEquals(MediaType.APPLICATION_JSON,
                        response.getHeaders().getContentType()),
                () -> assertEquals("Hello, Dolly!",
                        Objects.requireNonNull(response.getBody()).message())
        );
    }

}
