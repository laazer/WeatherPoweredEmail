package com.laazer.wpe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Laazer
 */
@RestController
public class ForwardingController {

    @RequestMapping(value = "/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index");
    }
}
