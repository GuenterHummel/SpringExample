package com.gh.demoserver.json;

import java.util.List;

public record AstroResponse(String message,
                            int number,
                            List<Assignment> people) {
    record Assignment (String craft, String name) {}
}
