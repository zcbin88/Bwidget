package com.bingo.widget.contactsview.indexableview;

/**
 * Created by zcb on 17/09/12
 */
public interface IndexableEntity {

    String getFieldIndexBy();

    void setFieldIndexBy(String indexField);

    void setFieldPinyinIndexBy(String pinyin);
}
