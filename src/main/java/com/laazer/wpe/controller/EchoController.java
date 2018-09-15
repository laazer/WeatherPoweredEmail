package com.laazer.wpe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Laazer
 */
@RestController
public class EchoController {

    @RequestMapping("/echo")
    public String echo() {
        return "echo";
    }
}
