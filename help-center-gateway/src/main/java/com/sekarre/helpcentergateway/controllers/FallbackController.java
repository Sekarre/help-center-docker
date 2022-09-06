package com.sekarre.helpcentergateway.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fallback")
public class FallbackController {

    @RequestMapping("/default")
    public Mono<String> getFallbackMessage() {
        return Mono.just("Request failure. Try again later");
    }
}
