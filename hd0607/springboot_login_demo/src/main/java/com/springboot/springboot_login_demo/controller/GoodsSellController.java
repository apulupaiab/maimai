package com.springboot.springboot_login_demo.controller;

import com.springboot.springboot_login_demo.domain.GoodsFind;
import com.springboot.springboot_login_demo.domain.GoodsSell;
import com.springboot.springboot_login_demo.service.GoodsSellService;
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
@RequestMapping("/sellgoods") // 定义路由前缀
public class GoodsSellController {

    @Resource
    private GoodsSellService goodsSellService; // 注入GoodsSellService
    // 图片上传端点
    private static final Logger logger = LoggerFactory.getLogger(GoodsSellController.class);

    @PostMapping("/upload-image")
    public Result<?> uploadImage(@RequestParam("file") MultipartFile imageFile) {
        Result<?> result = new Result<>();
        try {
            // 处理图片上传逻辑，例如保存到服务器
            String imageUrl = goodsSellService.uploadImage(imageFile);
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
    public Result<GoodsSell> createGoodsSell(@RequestBody GoodsSell newGoodsSell) {
        GoodsSell goodsSell = goodsSellService.createGoodsSell(newGoodsSell);
        if (goodsSell != null) {
            return Result.success(goodsSell, "创建成功！");
        } else {
            return Result.error("500", "创建失败！");
        }
    }

    // 处理站内搜索的GET请求
    @GetMapping("/search1")
    public List<GoodsSell> searchGoodsSell(@RequestParam String query) {
        return goodsSellService.searchGoodsSell(query);
    }

    // 添加一个处理搜索请求的GET方法
    @GetMapping("/search2")
    public Result<List<GoodsSell>> searchGoodsSells(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "detail", required = false) String detail) {
        // 调用service层的搜索方法，并返回结果
        List<GoodsSell> goodsSells = goodsSellService.searchGoodsSellsByNameAndDetail(name, detail);
        return Result.success(goodsSells, "Search successful");
    }

    @GetMapping("/search")
    public Page<GoodsSell> searchGoodsSells(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<GoodsSell> goodsSells = goodsSellService.searchGoodsSells(name, pageable);
        return goodsSells;
    }

    @GetMapping("/search-images/{goodsSellId}")
    public ResponseEntity<StreamingResponseBody> searchGoodsSellsWithImages(@PathVariable Long goodsSellId) throws IOException {
        GoodsSell goodsSell = goodsSellService.findGoodsSellById(goodsSellId);
        // 准备一个包装器来构建StreamingResponseBody
        StreamingResponseBody stream = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                byte[] imageBytes = goodsSell.getGspicBytes();
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

    // 删除商品的端点
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result<?> deleteGoodsSell(@PathVariable Long id) {
        Result<?> result = new Result<>();
        try {
            goodsSellService.deleteGoodsSell(id);
            result = Result.success(null, "商品删除成功！");
        } catch (Exception e) {
            result = Result.error("500", "删除商品失败！");
        }
        return result;
    }

    @PostMapping("/increase-want/{id}")
    public Result<?> increaseWant(@PathVariable Long id) {
        try {
            GoodsSell goodsSell = goodsSellService.findGoodsSellById(id);
            if (goodsSell != null) {
                goodsSell.setGswant(goodsSell.getGswant() + 1); // 增加想要人数
                goodsSellService.save(goodsSell); // 保存更新后的对象
                return Result.success(goodsSell.getGswant(), "增加想要人数成功！");
            } else {
                return Result.error("404", "商品未找到！");
            }
        } catch (Exception e) {
            return Result.error("500", "增加想要人数失败！");
        }
    }
    @GetMapping("/{id}")
    public Result<GoodsSell> getGoodsSellById(@PathVariable Long id) {
        GoodsSell goodsSell = goodsSellService.findGoodsSellById(id);
        if (goodsSell != null) {
            return Result.success(goodsSell, "获取商品详情成功！");
        } else {
            return Result.error("404", "商品未找到！");
        }
    }
}
