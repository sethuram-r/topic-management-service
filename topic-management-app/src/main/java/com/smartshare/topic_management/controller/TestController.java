package com.smartshare.topic_management.controller;

import com.smartshare.user_management.service.ITestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final ITestService testService;

    @GetMapping(path = "/test")
    public void test() {
        testService.test();
    }
}
