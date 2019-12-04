package com.bingo.widget.model;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2018/5/21 14:15
 * @version: 1.0
 * @description:
 * =================================
 */
public class ItemBean {

    /**/
    private String title;
    private int icon;

    private boolean isCheck;

    public ItemBean(String title, int icon, boolean isCheck) {
        this.title = title;
        this.icon = icon;
        this.isCheck = isCheck;
    }

    public ItemBean() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ItemBean(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public String getTitle() {
        return title==null?"TITLE":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
