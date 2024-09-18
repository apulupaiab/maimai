package com.springboot.springboot_login_demo.controller;


import com.springboot.springboot_login_demo.domain.User;
import com.springboot.springboot_login_demo.service.UserService;
import com.springboot.springboot_login_demo.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    //处理post请求，路由为/user/login
    @PostMapping("/login")
    public Result<User> loginController(@RequestParam String phoneNumber, @RequestParam String password){
        User user = userService.loginService(phoneNumber, password);
        if(user!=null){
            return Result.success(user,"登录成功！");
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }
//处理post请求，路由为/user/register
    @PostMapping("/register")
    public Result<User> registerController(@RequestBody User newUser){
        User user = userService.registService(newUser);
        if(user!=null){
            return Result.success(user,"注册成功！");
        }else{
            return Result.error("456","用户名已存在！");
        }
    }
}
