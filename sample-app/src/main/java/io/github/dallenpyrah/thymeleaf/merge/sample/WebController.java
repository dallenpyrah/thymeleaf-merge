package io.github.dallenpyrah.thymeleaf.merge.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
