package com.bingo.widget.dialog.picker.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * ================================
 *
 * @author: Administrator
 * @email: zcbin2@grgbanking.com
 * @time: 2019/2/25 18:40
 * @version: 1.0
 * @description: =================================
 */
public class BranchUserListBean {

    int status;
    String message;
    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

    }
}
