package com.bingo.demo.bean;

/**
 * ================================
 *
 * @author: zcbin2
 * @email: zcbin2@grgbanking.com
 * @time: 2019/12/5 15:31
 * @version: 1.0
 * @description: =================================
 */
public class MainItemBean {
    public MainItemBean(String title) {
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
