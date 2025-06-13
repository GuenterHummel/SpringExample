package com.gh.demoserver.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareConcurrentModel;

import static org.junit.jupiter.api.Assertions.*;

class GreetingControllerUnitTest {

    @Test
    void greeting() {
        GreetingController controller = new GreetingController();
        Model model = new BindingAwareConcurrentModel();
        String result = controller.greeting("Gue", model);
        assertAll(
                () -> assertEquals("greeting", result),
                () -> assertEquals("Gue", model.asMap().get("name"))
        );
    }
}