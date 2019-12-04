package com.bingo.widget.dialog.picker;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/**
 * ================================
 *
 * @author: zcbin2
 * @email: zcbin2@grgbanking.com
 * @time: 2019/4/18 9:15
 * @version: 1.0
 * @description: =================================
 */
public class PickDialogUtil {

    Activity activity;

    private static class SingletonHolder {
        private static final PickDialogUtil INSTANCE = new PickDialogUtil();
    }
    public static PickDialogUtil getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void singleRowDialog(Context context,String title, Map map, String currentId, SingleRowPickerDialog.OnPickerChangeListener listener){
        if (0 == map.size()) {
            return;
        }
        new SingleRowPickerDialog(context, title, map, currentId, listener).show();
    }


























}
