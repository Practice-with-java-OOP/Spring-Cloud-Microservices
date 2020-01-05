package com.syphan.practice.proxy.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {

    @GetMapping("/test")
    public String test() {
        return "oke nhe";
    }
}
