package com.springboot.springboot_login_demo.service.servicelmpl;

import com.springboot.springboot_login_demo.domain.User;
import com.springboot.springboot_login_demo.repository.UserDao;
import com.springboot.springboot_login_demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServicelmpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User loginService(String phoneNumber, String password) {
        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
//        User user = userDao.findByUnameAndPassword(uname, password);
        User user = userDao.findByPhoneNumberAndPassword(phoneNumber, password);
        // 重要信息置空
        if(user != null){
            user.setPassword("");
        }
        return user;
    }

    @Override
    public User registService(User user) {
        //当新用户的用户名已存在时
        if(userDao.findByUname(user.getUname())!=null){
            // 无法注册
            return null;
        }else{
            //返回创建好的用户对象(带uid)
            User newUser = userDao.save(user);
            if(newUser != null){
                newUser.setPassword("");
            }
            return newUser;
        }
    }
}


