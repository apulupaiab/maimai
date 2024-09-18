package com.springboot.springboot_login_demo.domain;

import jakarta.persistence.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Entity // 标记这是一个实体类
@Table(name = "goodssell") // 指定与数据库表的对应关系
public class GoodsSell {

    @Id // 标记为实体的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增策略
    private long gsid; // id属性

    private String gsname; // 名称属性

    private String gsowner; // 所有者属性

    private double gsprice; // 价格属性

    private String gsdetail; // 详情属性

    private String gspic; // 图片属性

    public int getGswant() {
        return gswant;
    }

    public void setGswant(int gswant) {
        this.gswant = gswant;
    }

    private int gswant=0; // 状态属性


    // 在 GoodsFind 实体类中添加以下属性和相应的 getter 和 setter
    private byte[] gspicData; // 图片字节数据属性

    public long getGsid() {
        return gsid;
    }

    public void setGsid(long gsid) {
        this.gsid = gsid;
    }

    public String getGsname() {
        return gsname;
    }

    public void setGsname(String gsname) {
        this.gsname = gsname;
    }

    public double getGsprice() {
        return gsprice;
    }

    public void setGsprice(double gsprice) {
        this.gsprice = gsprice;
    }

    public String getGsowner() {
        return gsowner;
    }

    public void setGsowner(String gsowner) {
        this.gsowner = gsowner;
    }

    public String getGsdetail() {
        return gsdetail;
    }

    public void setGsdetail(String gsdetail) {
        this.gsdetail = gsdetail;
    }

    public String getGspic() {
        return gspic;
    }

    public void setGspic(String gspic) {
        this.gspic = gspic;
    }

    public byte[] getGspicData() {
        return gspicData;
    }

    public void setGspicData(byte[] gspicData) {
        this.gspicData = gspicData;
    }

    /**
     * 获取图片文件的字节数组。
     * @return 图片的字节数组，如果图片路径为null或者文件不存在，则返回null。
     */
    public byte[] getGspicBytes() {
        // 检查gfpic是否为null
        if (this.gspic == null) {
            return null; // 或者可以选择抛出一个自定义异常
        }

        File imageFile = new File(this.gspic);
        // 检查文件是否存在
        if (!imageFile.exists()) {
            // 文件不存在，可以选择返回null或者抛出异常
            return null; // 或者 throw new FileNotFoundException("Image file does not exist: " + this.gfpic);
        }

        // 检查文件是否可读
        if (!imageFile.canRead()) {
            // 文件不可读，可以选择返回null或者抛出异常
            return null; // 或者 throw new IOException("Image file is not readable: " + this.gfpic);
        }

        try (FileInputStream fis = new FileInputStream(imageFile)) {
            long fileSize = imageFile.length();
            // 将文件大小转换为整数，确保不超过Integer.MAX_VALUE
            int fileSizeInt = (int) Math.min(fileSize, Integer.MAX_VALUE);
            byte[] imageBytes = new byte[fileSizeInt];

            // 读取文件内容到字节数组
            int bytesRead = fis.read(imageBytes, 0, fileSizeInt);
            if (bytesRead != fileSizeInt) {
                // 如果读取的字节数与文件大小不一致，可以选择返回null或者抛出异常
                return null; // 或者 throw new IOException("Failed to read the entire image file.");
            }

            return imageBytes;
        } catch (IOException e) {
            // 处理可能发生的IO异常
            // 可以选择打印日志，返回null或者抛出运行时异常
            e.printStackTrace(); // 打印异常信息到标准错误流
            return null; // 或者 throw new RuntimeException("Error reading image file", e);
        }
    }
}
