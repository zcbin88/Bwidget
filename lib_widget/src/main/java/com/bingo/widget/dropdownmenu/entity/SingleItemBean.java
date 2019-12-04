package com.bingo.widget.dropdownmenu.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ================================
 *
 * @author: zcbin2
 * @email: zcbin2@grgbanking.com
 * @time: 2019/5/9 12:09
 * @version: 1.0
 * @description: =================================
 */
public class SingleItemBean implements Parcelable {
    private String id;
    private String content;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
    }

    public SingleItemBean() {
    }

    protected SingleItemBean(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<SingleItemBean> CREATOR = new Parcelable.Creator<SingleItemBean>() {
        @Override
        public SingleItemBean createFromParcel(Parcel source) {
            return new SingleItemBean(source);
        }

        @Override
        public SingleItemBean[] newArray(int size) {
            return new SingleItemBean[size];
        }
    };
}
