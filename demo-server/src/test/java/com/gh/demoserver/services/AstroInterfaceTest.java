package com.gh.demoserver.services;

import com.gh.demoserver.json.AstroResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AstroInterfaceTest {
    @Autowired
    private AstroInterface astroInterface;

    @Test
    void getResponse() {
        AstroResponse response = astroInterface.getResponse();
        assertAll(
                () -> assertEquals("success", response.message()),
                () -> assertTrue(response.number() >= 0),
                () -> assertEquals(response.people().size(), response.number())
        );

        System.out.println(response);
    }
}