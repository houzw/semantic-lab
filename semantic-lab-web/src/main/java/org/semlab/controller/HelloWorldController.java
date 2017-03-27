package org.semlab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/3/26 17:41
 */
@RestController
public class HelloWorldController
{
    @GetMapping("/hello")
    public  String index()
    {
        return "hello world";
    }
}
