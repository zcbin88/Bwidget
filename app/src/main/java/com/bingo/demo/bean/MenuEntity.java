package com.bingo.demo.bean;

import java.io.Serializable;

/**
 * ================================
 *
 * @author: zcb
 * @email: zhang-cb@foxmail.com
 * @time: 2018/5/18 10:10
 * @version: 1.0
 * @description: =================================
 */
public class MenuEntity implements Serializable {

    public MenuEntity() {
    }

    public MenuEntity(String name, int img) {
        this.name = name;
        this.imgId = img;
    }

    private String name;
    private int imgId;

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
