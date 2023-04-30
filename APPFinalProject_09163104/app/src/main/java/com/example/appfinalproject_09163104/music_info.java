package com.example.appfinalproject_09163104;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class music_info extends AppCompatActivity implements View.OnClickListener{
    public SQLite DH = new SQLite(this);
    Intent intent;
    TextView title, time_and_endTime, location, locationName, onSales, price, descriptionFilterHtml, masterUnit, webSales, sourceWebPromote , hitRate;
    Button back_button0;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);

        intent = getIntent();
        int seek = intent.getIntExtra("info",0);

        title = findViewById(R.id.title);
        time_and_endTime = findViewById(R.id.time_and_endTime);
        location = findViewById(R.id.location);
        locationName = findViewById(R.id.locationName);
        onSales = findViewById(R.id.onSales);
        price = findViewById(R.id.price);
        descriptionFilterHtml = findViewById(R.id.descriptionFilterHtml);
        masterUnit = findViewById(R.id.masterUnit);
        webSales = findViewById(R.id.webSales);
        sourceWebPromote = findViewById(R.id.sourceWebPromote);
        hitRate = findViewById(R.id.hitRate);
        back_button0 = findViewById(R.id.back_button0);
        back_button0.setOnClickListener(this);

        SQLiteDatabase db =DH.getWritableDatabase();

        ////
        Cursor cursor = db.query("music", new String[]{"_id", "_title", "_time", "_location", "_locationName", "_onSales", "_price", "_endTime", "_descriptionFilterHtml", "_masterUnit", "_webSales", "_sourceWebPromote", "_hitRate", "_photo"},"_id" + "="+ seek,null,null,null,null);
        cursor.moveToFirst();
        title.append(cursor.getString(1));
        time_and_endTime.append(cursor.getString(2)+cursor.getString(7));
        location.append(cursor.getString(3));
        locationName.append(cursor.getString(4));
        onSales.append(cursor.getString(5));
        price.append(cursor.getString(6));
        descriptionFilterHtml.append(cursor.getString(8));
        masterUnit.append(cursor.getString(9));

        ///超連結        xml要加 android:autoLink="web"
        webSales.append(Html.fromHtml(cursor.getString(10)));
        webSales.setMovementMethod(LinkMovementMethod.getInstance());

        sourceWebPromote.append(Html.fromHtml(cursor.getString(11)));
        webSales.setMovementMethod(LinkMovementMethod.getInstance());

        hitRate.append(Integer.toString(cursor.getInt(12)));

        cursor.getBlob(13);
        ///圖片drawable Byte[] 變 Bitmap
        /*如果bitmapdata是字節數組，那麼獲取Bitmap是這樣完成的：Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);*/
        Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(13), 0, cursor.getBlob(13).length);
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(this, Show_infoActivity.class);
        startActivity(intent);
    }
}