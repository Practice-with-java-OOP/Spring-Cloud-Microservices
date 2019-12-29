package com.syphan.practice.proxy.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GatewayController {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/test")
    public String test() {
        httpServletRequest.getHeader("Authorization");
        System.out.println();
        return "test";
    }
}
