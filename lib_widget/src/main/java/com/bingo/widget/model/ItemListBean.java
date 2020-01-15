package com.bingo.widget.model;

/**
 * ================================
 *
 * @author: zcbin2
 * @email: zcbin2@grgbanking.com
 * @time: 2019/4/2 15:49
 * @version: 1.0
 * @description: =================================
 */
public class ItemListBean {
    /**/
    private String title;
    private boolean isCheck;
    private String tag;
    private int laguageId;

    public ItemListBean() {
    }

    public ItemListBean(String title,int laguageId,boolean isCheck) {
        this.title = title;
        this.laguageId=laguageId;
        this.isCheck = isCheck;
    }

    public String getTag() {
        return tag==null?"test tag":tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getLaguageId() {
        return laguageId;
    }

    public void setLaguageId(int laguageId) {
        this.laguageId = laguageId;
    }

    public String getTitle() {
        return title==null?"TITLE":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
