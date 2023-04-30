package com.example.camera_photo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView3;
    Button button3;
    Intent intent;
    Uri image_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        intent = getIntent();
        ///String to Uri
        image_uri = Uri.parse(intent.getStringExtra("uri"));
        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
        imageView3.setImageURI(image_uri);

        /*圖片給圖
        imageView3.setImageURI(image_uri); ///轉成String傳送 收到再轉回uri
        imageView3.setImageBitmap(Bitmap); ///轉成byte[]傳送 收到再轉回Bitmap
        imageView3.setImageResource(R.drawable.???); ///直接用
         */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView3:
                Toast.makeText(this, image_uri.toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}