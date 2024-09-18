package com.springboot.springboot_login_demo.repository;

import com.springboot.springboot_login_demo.domain.GoodsSell;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoodsSellDao extends JpaRepository<GoodsSell, Long> {
    // 根据id查找GoodsSell实体
    GoodsSell findByGsid(Long id);

    // 根据name查找GoodsSell实体，注意使用驼峰命名法
    GoodsSell findByGsname(String name);

    // 根据商品名称或详情搜索商品，使用Containing以实现模糊搜索
    List<GoodsSell> findByGsnameContainingOrGsdetailContaining(String name, String detail);

    // 请注意，这里的方法签名与原始的GoodsFindDao略有不同，因为属性名已经改变
    Page<GoodsSell> findByGsnameContaining(String name, Pageable pageable);
}
