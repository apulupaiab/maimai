package com.springboot.springboot_login_demo.service;


import com.springboot.springboot_login_demo.domain.User;

public interface UserService {
    /**
     * 登录业务逻辑
     * @param phoneNumber 账户名
     * @param password 密码
     * @return
     */
    User loginService(String phoneNumber, String password);

    /**
     * 注册业务逻辑
     * @param user 要注册的User对象，属性中主键uid要为空，若uid不为空可能会覆盖已存在的user
     * @return
     */
    User registService(User user);
}
