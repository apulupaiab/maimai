package com.springboot.springboot_login_demo.service;

import com.springboot.springboot_login_demo.domain.GoodsFind;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.util.List;

public interface GoodsFindService {
    /**
     * 创建物品业务逻辑
     * @param goodsFind 要创建的GoodsFind对象，属性中主键id要为空，若id不为空可能会覆盖已存在的记录
     * @return 创建成功的GoodsFind对象
     */
    GoodsFind createGoodsFind(GoodsFind goodsFind);
    String uploadImage(MultipartFile imageFile) throws IOException;
    GoodsFind findGoodsById(Long id);
    //search1
    List<GoodsFind> searchGoodsFind(String query);
    // 添加一个根据商品名称和详情搜索商品的方法
    //search2
    List<GoodsFind> searchGoodsFindsByNameAndDetail(String name, String detail);
//    GoodsFind createGoodsFindWithImage(GoodsFind goodsFind, MultipartFile imageFile);
    //search3
    Page<GoodsFind> searchGoodsFinds(String name, Pageable pageable);
    void deleteGoodsFind(Long id)throws IOException;


    // 新增保存商品的方法
    void save(GoodsFind goodsFind);
//    byte[] getGfpicBytesByGoodsFindId(Long goodsFindId);

    // 可以根据需要添加更多的业务逻辑方法，例如更新、删除或查找物品等
    // GoodsFind updateGoodsFind(GoodsFind goodsFind);
    // void deleteGoodsFind(Long id);
    // GoodsFind findGoodsFindById(Long id);
    // List<GoodsFind> findAllGoodsFinds();
}

