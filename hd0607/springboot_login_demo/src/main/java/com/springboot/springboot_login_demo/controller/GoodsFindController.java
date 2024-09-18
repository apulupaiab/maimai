package com.springboot.springboot_login_demo.controller;


import com.springboot.springboot_login_demo.domain.GoodsFind;
import com.springboot.springboot_login_demo.service.GoodsFindService;
import com.springboot.springboot_login_demo.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/findgoods") // 定义路由前缀
public class GoodsFindController {

    @Resource
    private GoodsFindService goodsFindService; // 注入GoodsFindService
    // 处理post请求，路由为/findgoods/create
    // 图片上传端点
    private static final Logger logger = LoggerFactory.getLogger(GoodsFindController.class);

    @PostMapping("/upload-image")
    public Result<?> uploadImage(@RequestParam("file") MultipartFile imageFile) {
        Result<?> result = new Result<>();
        try {
            // 处理图片上传逻辑，例如保存到服务器
            String imageUrl = goodsFindService.uploadImage(imageFile);
            // 输出图片的URL
            System.out.println(imageUrl);
            // 更新 result 对象的引用，使其指向新的 Result 对象
            result = Result.success(imageUrl, "Image uploaded successfully");
        } catch (Exception e) {
            result = Result.error("500", "Failed to upload image");
        }
        return result;
    }
    // 表单数据提交端点
    @PostMapping("/create")
    public Result<GoodsFind> createGoodsFind(@RequestBody GoodsFind newGoodsFind){
        GoodsFind goodsFind = goodsFindService.createGoodsFind(newGoodsFind);
        if(goodsFind != null){
            return Result.success(goodsFind, "创建成功！");
        }else{
            return Result.error("500", "创建失败！");
        }
    }

    // 处理站内搜索的GET请求
    @GetMapping("/search1")
    public List<GoodsFind> searchGoodsFind(@RequestParam String query) {
        return goodsFindService.searchGoodsFind(query);
    }
    // 添加一个处理搜索请求的GET方法
    @GetMapping("/search2")
    public Result<List<GoodsFind>> searchGoodsFinds(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "detail", required = false) String detail) {
        // 调用service层的搜索方法，并返回结果
        List<GoodsFind> goodsFinds = goodsFindService.searchGoodsFindsByNameAndDetail(name, detail);
        return Result.success(goodsFinds, "Search successful");
    }

@GetMapping("/search")
public Page<GoodsFind> searchGoodsFinds(
        @RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "0") int pageNum,
        @RequestParam(defaultValue = "5") int pageSize) {
    Pageable pageable = PageRequest.of(pageNum, pageSize);
    Page<GoodsFind> goodsFinds = goodsFindService.searchGoodsFinds(name, pageable);
    return goodsFinds;
}

@GetMapping("/search-images/{goodsFindId}")
public ResponseEntity<StreamingResponseBody> searchGoodsFindsWithImages(@PathVariable Long goodsFindId) throws IOException {
    GoodsFind goodsFind = goodsFindService.findGoodsById(goodsFindId);
    // 准备一个包装器来构建StreamingResponseBody
    StreamingResponseBody stream = new StreamingResponseBody() {
        @Override
        public void writeTo(OutputStream outputStream) throws IOException {

                byte[] imageBytes = goodsFind.getGfpicBytes();
                if (imageBytes != null) {
                    // 写入图片数据到输出流
                    outputStream.write(imageBytes);
                    outputStream.flush(); // 刷新输出流以确保数据被发送
                }

        }
    };

    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Images-Count", "1");

    // 构建ResponseEntity对象，包含StreamingResponseBody和HTTP状态
    return ResponseEntity.ok()
            .headers(headers)
            .body(stream);
}
    @PostMapping("/increase-want/{id}")
    public Result<?> increaseWant(@PathVariable Long id) {
        try {
            GoodsFind goodsFind = goodsFindService.findGoodsById(id);
            if (goodsFind != null) {
                goodsFind.setGfwant(goodsFind.getGfwant() + 1); // 增加想要人数
                goodsFindService.save(goodsFind); // 保存更新后的对象
                return Result.success(goodsFind.getGfwant(), "增加想要人数成功！");
            } else {
                return Result.error("404", "商品未找到！");
            }
        } catch (Exception e) {
            return Result.error("500", "增加想要人数失败！");
        }
    }
    @GetMapping("/{id}")
    public Result<GoodsFind> getGoodsFindById(@PathVariable Long id) {
        GoodsFind goodsFind = goodsFindService.findGoodsById(id);
        if (goodsFind != null) {
            return Result.success(goodsFind, "获取商品详情成功！");
        } else {
            return Result.error("404", "商品未找到！");
        }
    }
    // 删除商品的端点
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result<?> deleteGoodsFind(@PathVariable Long id) {
        Result<?> result = new Result<>();
        try {
            goodsFindService.deleteGoodsFind(id);
            result = Result.success(null, "商品删除成功！");
        } catch (Exception e) {
            result = Result.error("500", "删除商品失败！");
        }
        return result;
    }
}