package com.springboot.springboot_login_demo.repository;


import com.springboot.springboot_login_demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUname(String uname); //通过用户名uname查找用户，注意要按照JPA的格式使用驼峰命名法
    User findByPhoneNumber(String phoneNumber);
    User findByPhoneNumberAndPassword(String phoneNumber, String password);

//    User findByUnameAndPassword(String uname, String password);//通过用户名uname和密码查找用户
}

//@Repository
//public interface UserDao extends JpaRepository<User, Long> {
//
//    // 根据用户名查找用户
//    User findByUname(String uname);
//
//    // 根据电话号码查找用户
//    User findByPhoneNumber(String phoneNumber);
//
//    // 使用自定义查询根据电话号码和密码查找用户
//    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber AND u.password = :password")
//    User findByPhoneNumberAndPassword(@Param("phoneNumber") String phoneNumber, @Param("password") String password);
//
//    // 如果您还需要根据用户名和密码查找用户的方法，也可以使用自定义查询
//    // @Query("SELECT u FROM User u WHERE u.uname = :uname AND u.password = :password")
//    // User findByUnameAndPassword(@Param("uname") String uname, @Param("password") String password);
//}
