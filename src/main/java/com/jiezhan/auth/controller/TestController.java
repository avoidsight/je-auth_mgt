package com.jiezhan.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zp
 * @Date: 2019-11-20 10:48
 * @Description:
 */

@RestController
@RequestMapping("c")
public class TestController {

    @GetMapping("/healthCheck")
    public String healthCheck(){
        return "success";
    }
}
