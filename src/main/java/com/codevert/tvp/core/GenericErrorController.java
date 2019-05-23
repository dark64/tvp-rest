package com.codevert.tvp.core;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GenericErrorController implements ErrorController {

    @RequestMapping("/error")
    public Map<String, Object> handleError() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Peekaboo. You are not supposed to be here mate.");
        map.put("code", HttpStatus.I_AM_A_TEAPOT);
        return map;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
