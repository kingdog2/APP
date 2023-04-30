package com.example.select_photo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_picker;
    private ImageView imageView;
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        resolver=this.getContentResolver();
        btn_picker=findViewById(R.id.btn_picker);
        btn_picker.setOnClickListener(this);
    }
    //回傳接收(回傳後要幹嘛)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();//取得相片路徑
            try {
                //將該路徑的圖片轉成bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                //設定ImageView圖片
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_picker) {
            //打開相簿
            Intent intent = new Intent();
            ///video/視頻     audio/音頻    image/ 圖片 video/;image/同時兩個
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            //選擇相片後接回傳 進行跳轉，當選取完回到畫面後會呼叫onActivityForResult取得回傳的照片路徑(回傳給我)
            startActivityForResult(intent, 1);
        }
    }
}