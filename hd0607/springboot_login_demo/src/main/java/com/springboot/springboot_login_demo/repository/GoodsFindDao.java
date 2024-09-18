package com.springboot.springboot_login_demo.repository;

import com.springboot.springboot_login_demo.domain.GoodsFind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GoodsFindDao extends JpaRepository<GoodsFind, Long> {
    // 根据id查找GoodsFind实体
    GoodsFind findByGfid(Long id);

    // 根据name查找GoodsFind实体，注意使用驼峰命名法
    GoodsFind findByGfname(String name);

    // 根据商品名称或详情搜索商品，使用Containing以实现模糊搜索
    List<GoodsFind> findByGfnameContainingOrGfdetailContaining(String name, String detail);

    Page<GoodsFind> findByGfnameContaining(String name, Pageable pageable);
}