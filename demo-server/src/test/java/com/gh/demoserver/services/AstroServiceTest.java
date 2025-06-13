package com.gh.demoserver.services;

import com.gh.demoserver.json.AstroResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AstroServiceTest {
    @Autowired
    private AstroService service;

    @Test
    void getPeopleInSpace() {
        String people = service.getPeopleInSpace();
        assertTrue(people.contains("people"));
        System.out.println(people);
    }

    @Test
    void getAstroResponse() {
        AstroResponse response = service.getAstroResponse();
        assertAll(
                () -> assertEquals("success", response.message()),
                () -> assertTrue(response.number() >= 0),
                () -> assertEquals(response.people().size(), response.number())
        );

        System.out.println(response);
    }

    @Test
    void getAstroResponseAsync() {
        AstroResponse response = service.getAstroResponseAsync();
        assertAll(
                () -> assertEquals("success", response.message()),
                () -> assertTrue(response.number() >= 0),
                () -> assertEquals(response.people().size(), response.number())
        );

        System.out.println(response);
    }
}
