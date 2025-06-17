package com.gh.demoserver.services;

import com.gh.demoserver.json.AstroResponse;
import org.springframework.web.service.annotation.GetExchange;

public interface AstroInterface {
    @GetExchange("/astros.json")
    AstroResponse getResponse();
}
