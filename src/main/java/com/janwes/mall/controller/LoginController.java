package com.janwes.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mall.controller
 * @date 2021/6/29 15:10
 * @description
 */
@Api(tags = "用户中心")
@RestController
@RequestMapping("/user")
public class LoginController {

    @ApiOperation(value = "用户登录")
    @GetMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "登录成功";
    }
}
