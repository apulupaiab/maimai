package com.springboot.springboot_login_demo.service.servicelmpl;

import com.springboot.springboot_login_demo.domain.GoodsFind;
import com.springboot.springboot_login_demo.domain.GoodsSell;
import com.springboot.springboot_login_demo.repository.GoodsSellDao;
import com.springboot.springboot_login_demo.service.GoodsSellService;
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
public class GoodsSellServicelmpl implements GoodsSellService {
    // 假设你有一个目录用于存放上传的图片
    private static final String UPLOAD_DIR = "D:\\qdd";

    // Spring Data JPA repository
    @Resource
    private GoodsSellDao goodsSellDao;

    @Override
    public GoodsSell createGoodsSell(GoodsSell goodsSell) {
        try {
            GoodsSell savedGoodsSell = goodsSellDao.save(goodsSell);
            if (savedGoodsSell == null) {
                throw new RuntimeException("Failed to save GoodsSell entity");
            }
            return savedGoodsSell;
        } catch (Exception e) {
            Log log = null; // Log should be properly initialized
            log.error("Error creating GoodsSell entity", e);
            throw e;
        }
    }

    @Override
    public String uploadImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        String originalFilename = imageFile.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new IllegalArgumentException("File name is empty");
        }
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Unable to create upload directory");
        }
        File file = new File(uploadDir, originalFilename);
        imageFile.transferTo(file);
        String imageUrl = "D:\\qdd\\" + originalFilename;
        System.out.println(imageUrl);
        return imageUrl;
    }

    @Override
    public GoodsSell findGoodsSellById(Long id) {
        return goodsSellDao.findById(id).orElse(null);
    }

    @Override
    public List<GoodsSell> searchGoodsSell(String query) {
        return goodsSellDao.findByGsnameContainingOrGsdetailContaining(query, query);
    }

    @Override
    public List<GoodsSell> searchGoodsSellsByNameAndDetail(String name, String detail) {
        return goodsSellDao.findByGsnameContainingOrGsdetailContaining(name, detail);
    }

    @Override
    public Page<GoodsSell> searchGoodsSells(String name, Pageable pageable) {
        return goodsSellDao.findByGsnameContaining(name, pageable);
    }

    @Override
    public void deleteGoodsSell(Long id) throws IOException {
        GoodsSell goodsSell = goodsSellDao.findById(id).orElse(null);
//        if (goodsSell != null) {
//            String imagePath = goodsSell.getGspic(); // Adjust method name if necessary
//            File imageFile = new File(imagePath);
//            if (imageFile.exists() && !imageFile.delete()) {
//                throw new IOException("Unable to delete image file: " + imagePath);
//            }
            goodsSellDao.deleteById(id);
//        } else {
//            throw new IllegalArgumentException("GoodsSell with id " + id + " not found");
//        }
    }

    @Override
    public void save(GoodsSell goodsSell) {
        goodsSellDao.save(goodsSell);
    }

}