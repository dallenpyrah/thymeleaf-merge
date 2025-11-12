package io.github.dallenpyrah.thymeleaf.merge.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/id")
    public String id() {
        return "id";
    }

    @GetMapping("/exclude")
    public String exclude() {
        return "exclude";
    }

    @GetMapping("/attrs")
    public String attrs() {
        return "attrs";
    }

    @GetMapping("/hxheavy")
    public String hxheavy() {
        return "hxheavy";
    }

    @GetMapping("/dataheavy")
    public String dataheavy() {
        return "dataheavy";
    }

    @GetMapping("/ariaheavy")
    public String ariaheavy() {
        return "ariaheavy";
    }

    @GetMapping("/stdattrs")
    public String stdattrs() {
        return "stdattrs";
    }
}
