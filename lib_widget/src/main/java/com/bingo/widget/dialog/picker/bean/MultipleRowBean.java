package com.bingo.widget.dialog.picker.bean;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2019/2/21 14:34
 * @version: 1.0
 * @description:
 * =================================
 */
public class MultipleRowBean {
    private String id;
    private String content;
    private boolean isSelected;

    public MultipleRowBean() {
    }

    public MultipleRowBean(String id, String content,boolean isSelected) {
        this.id = id;
        this.content = content;
        this.isSelected=isSelected;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
