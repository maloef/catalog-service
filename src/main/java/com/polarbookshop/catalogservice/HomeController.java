package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.config.PolarProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final PolarProperties polarProperties;

    @GetMapping("/")
    public String getGreeting() {
        log.info("greeting called, response: {}", polarProperties.getGreeting());
        return polarProperties.getGreeting();
    }

}
