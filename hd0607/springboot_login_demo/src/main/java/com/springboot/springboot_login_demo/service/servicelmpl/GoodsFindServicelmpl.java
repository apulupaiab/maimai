package com.springboot.springboot_login_demo.service.servicelmpl;

import com.springboot.springboot_login_demo.domain.GoodsFind;
import com.springboot.springboot_login_demo.repository.GoodsFindDao;
import com.springboot.springboot_login_demo.service.GoodsFindService;
import org.apache.juli.logging.Log;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.util.StringUtils;


@Service
public class GoodsFindServicelmpl implements GoodsFindService {
    // 假设你有一个目录用于存放上传的图片
    private static final String UPLOAD_DIR = "D:\\qdd";

    // Spring Data JPA repository
    @Resource
    private GoodsFindDao goodsFindDao;

//    @Override
//    public GoodsFind createGoodsFind(GoodsFind goodsFind) {
//        return goodsFindDao.save(goodsFind);
//    }
@Override
public GoodsFind createGoodsFind(GoodsFind goodsFind) {
    try {

        // 保存GoodsFind对象
        GoodsFind savedGoodsFind = goodsFindDao.save(goodsFind);

        // 检查保存后的对象是否为null
        if (savedGoodsFind == null) {
            throw new RuntimeException("Failed to save GoodsFind entity");
        }

        return savedGoodsFind;
    } catch (Exception e) {
        // 添加适当的错误处理和日志记录
        Log log = null;
        log.error("Error creating GoodsFind entity", e);
        throw e; // 或者根据需要抛出自定义异常
    }
}

    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        // 检查文件是否为空
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // 获取文件原始名称
        String originalFilename = imageFile.getOriginalFilename();

        // 检查文件名是否合法
        if (!StringUtils.hasText(originalFilename)) {
            throw new IllegalArgumentException("File name is empty");
        }

        // 创建图片存储目录
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Unable to create upload directory");
        }

        // 构建新文件路径
        File file = new File(uploadDir, originalFilename);

        // 将上传的文件保存到服务器
        imageFile.transferTo(file);

        // 返回图片的URL，可以是绝对路径或相对于某个基础URL的路径
        String imageUrl = "D:\\qdd\\" + originalFilename; // 根据实际URL结构调整
        //s输出图片的URL
        System.out.println(imageUrl);

        return imageUrl;
    }

    @Override
    public GoodsFind findGoodsById(Long id) {
        return goodsFindDao.findById(id).orElse(null);
    }

    @Override
    public List<GoodsFind> searchGoodsFind(String query) {
        return goodsFindDao.findByGfnameContainingOrGfdetailContaining(query, query);
    }

    @Override
    public List<GoodsFind> searchGoodsFindsByNameAndDetail(String name, String detail) {
        // 调用dao层的搜索方法，这里假设name和detail都是从前端传入的搜索关键字
        return goodsFindDao.findByGfnameContainingOrGfdetailContaining(name, detail);
    }

    @Override
    public Page<GoodsFind> searchGoodsFinds(String name, Pageable pageable) {
        // 直接返回查询结果，无需显式地实例化 Page 对象
        return goodsFindDao.findByGfnameContaining(name, pageable);
    }



        @Override
        public void save(GoodsFind goodsFind) {
            goodsFindDao.save(goodsFind);
        }



@Override
public void deleteGoodsFind(Long id) throws IOException {
    // 从数据库中获取要删除的商品记录
    GoodsFind goodsFind = goodsFindDao.findById(id).orElse(null);
//    if (goodsFind != null) {
//        // 获取图片路径
//        String imagePath = goodsFind.getGfpic(); // 假设 getGfpic() 返回图片的完整路径
//
//        // 检查图片路径是否有效并且文件存在
//        File imageFile = new File(imagePath);
//        if (imageFile.exists()) {
//            // 尝试删除文件系统中的图片文件
//            if (!imageFile.delete()) {
//                throw new IOException("Unable to delete image file: " + imagePath);
//            }
//        }

        // 尝试删除数据库中的记录
        try {
            goodsFindDao.deleteById(id);
            // 如果没有异常抛出，认为删除成功
        } catch (Exception e) {
            // 日志记录异常信息
            // Log log = null;
            // log.error("Error deleting GoodsFind entity", e);
            // 这里抛出自定义异常或处理异常
            throw new IOException("Error deleting GoodsFind entity with id: " + id, e);
        }
//    }
//    else {
//        // 商品记录不存在，抛出异常或返回错误信息
//        throw new IllegalArgumentException("GoodsFind with id " + id + " not found");
//    }
}
}
