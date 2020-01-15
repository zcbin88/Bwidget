package com.bingo.demo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bingo.demo.R;
import com.bingo.widget.SignatureView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
 /**
  * ================================
  * @author:  zcb
  * @email:   zhang-cb@foxmail.com
  * @time:    2020-01-15 15:21
  * @version: 1.0
  * @description: 签名activity
  * =================================
  */
public class SignActivity extends AppCompatActivity implements View.OnClickListener{
    SignatureView signatureView;
    Button btnClear;
    Button btnSave;

    private String imgPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signatureView = findViewById(R.id.signature_view);
        btnClear = findViewById(R.id.btn_clear);
        btnSave = findViewById(R.id.btn_save);
        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_clear){
            clear();
        }else if (v.getId()==R.id.btn_save){
            commit();
        }
    }

    public void clear() {
        signatureView.clearCanvas();//Clear SignatureView
    }


    public void commit() {
        if (signatureEnpty()) {
            //("请签名");
            return;
        }

        savePicture();
//        if (StringUtils.isEmpty(imgPath)) {
//            //("签名图片异常");
//            return;
//        }


    }


    /**
     * 把签名获得到的透明的bitmap图片 放到特定背景色的bitmap中合成新的
     * @param color
     * @param orginBitmap
     * @return
     */
    public static Bitmap drawBg4Bitmap(int color, Bitmap orginBitmap) {
        Paint paint = new Paint();
        paint.setColor(color);
        Bitmap bitmap = Bitmap.createBitmap(orginBitmap.getWidth(),
                orginBitmap.getHeight(), orginBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0, 0, orginBitmap.getWidth(), orginBitmap.getHeight(), paint);
        canvas.drawBitmap(orginBitmap, 0, 0, paint);
        return bitmap;
    }

    /**
     * 保存签名生成的图片
     */
    public void savePicture() {
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(directory, System.currentTimeMillis() + ".png");
        FileOutputStream out = null;
        Bitmap tempBitmap = signatureView.getSignatureBitmap();
        if (tempBitmap == null) {
            return;
        }
        Bitmap bitmap = drawBg4Bitmap(Color.WHITE, tempBitmap);
        try {
            out = new FileOutputStream(file);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } else {
                throw new FileNotFoundException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();

                    if (bitmap != null) {
                        imgPath = file.getPath();
                        Toast.makeText(getApplicationContext(),
                                "Image saved successfully at " + file.getPath(),
                                Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                            new MyMediaScanner(this, file);
                        } else {
                            ArrayList<String> toBeScanned = new ArrayList<String>();
                            toBeScanned.add(file.getAbsolutePath());
                            String[] toBeScannedStr = new String[toBeScanned.size()];
                            toBeScannedStr = toBeScanned.toArray(toBeScannedStr);
                            MediaScannerConnection.scanFile(this, toBeScannedStr, null,
                                    null);
                        }
                    }
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
                tempBitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 是否有写入内容
     *
     * @return true 内容为空说明没有签名  false 内容不为空说明有签名
     */
    public boolean signatureEnpty() {
        return signatureView.isBitmapEmpty();
    }


    private class MyMediaScanner implements
            MediaScannerConnection.MediaScannerConnectionClient {

        private MediaScannerConnection mSC;
        private File file;

        MyMediaScanner(Context context, File file) {
            this.file = file;
            mSC = new MediaScannerConnection(context, this);
            mSC.connect();
        }

        @Override
        public void onMediaScannerConnected() {
            mSC.scanFile(file.getAbsolutePath(), null);
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            mSC.disconnect();
        }
    }
}
