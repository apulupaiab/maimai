package com.springboot.springboot_login_demo.service;

import com.springboot.springboot_login_demo.domain.GoodsFind;
import com.springboot.springboot_login_demo.domain.GoodsSell;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface GoodsSellService {
    /**
     * 创建商品销售业务逻辑
     * @param goodsSell 要创建的GoodsSell对象，属性中主键id要为空，若id不为空可能会覆盖已存在的记录
     * @return 创建成功的GoodsSell对象
     */
    GoodsSell createGoodsSell(GoodsSell goodsSell);
    String uploadImage(MultipartFile imageFile) throws IOException;
    GoodsSell findGoodsSellById(Long id);
    //search1
    List<GoodsSell> searchGoodsSell(String query);
    // 添加一个根据商品名称和详情搜索商品的方法
    //search2
    List<GoodsSell> searchGoodsSellsByNameAndDetail(String name, String detail);
    //search3
    Page<GoodsSell> searchGoodsSells(String name, Pageable pageable);
    void deleteGoodsSell(Long id) throws IOException;
    void save(GoodsSell goodsSell);
    // 可以根据需要添加更多的业务逻辑方法，例如更新、删除或查找商品销售等
    // GoodsSell updateGoodsSell(GoodsSell goodsSell);
    // void deleteGoodsSell(Long id);
    // GoodsSell findGoodsSellById(Long id);
    // List<GoodsSell> findAllGoodsSells();
}