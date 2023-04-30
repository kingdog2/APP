package com.example.camera_photo;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends Activity{
    Intent intent;
    ImageView imageView0;
    Uri image_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView0 = (ImageView) findViewById(R.id.imageView0);
    }
    ///拍照
    public void Afunc(View view) {
        intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        image_url = getImageUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_url);
        startActivityForResult(intent, 100);
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            imageView0.setImageURI(image_url);
        }
    }
    /// Android7.0中为了提高私有文件的安全性,禁止向你的应用外公开 file:// URI "/temp/" + System.currentTimeMillis() + ".jpg"
    public Uri getImageUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        String path = file.getPath();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            image_url = Uri.fromFile(file);
        } else {
            //兼容android7.0 使用共享文件的形式
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, path);
            image_url = this.getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        Log.v("111","/temp/" + System.currentTimeMillis() + ".jpg");
        return image_url;
    }
    ///預覽 查看剛拍 或是相簿
    public void Bfunc(View view) {
        intent =  new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(image_url, "image/*");
        startActivity(intent);
    }
    ///傳送剛拍的照片
    public void Cfunc(View view) {
        ///uri用string傳送
        intent = new Intent(this, MainActivity2.class);
        intent.putExtra("uri", image_url.toString());
        startActivity(intent);
    }
}