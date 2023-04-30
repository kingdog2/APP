package com.example.appfinalproject_09163104;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public SQLite DH = new SQLite(this);

    TextView textView;
    Button JSON_button, entry_button;

    byte[] byteArray;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSON_button = findViewById(R.id.JSON_button);
        entry_button = findViewById(R.id.entry_button);
        JSON_button.setOnClickListener(this);
        entry_button.setOnClickListener(this);

        textView = findViewById(R.id.textView);

        ///圖片drawable Bitmap 變 Byte[]
        /*Bitmap bitmap = BitmapFactory.decodeFile("/path/images/image.jpg");
          ByteArrayOutputStream blob = new ByteArrayOutputStream();
          bitmap.compress(CompressFormat.PNG, 0 /* Ignored for PNGs *//*, blob);*/
       /* byte[] bitmapdata = blob.toByteArray();*/
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.s);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.JSON_button:
                getData_JSONObject();
                Toast.makeText(this, "json", Toast.LENGTH_SHORT).show();
                break;
            case R.id.entry_button:
                Intent intent = new Intent(this, Show_infoActivity.class);
                startActivity(intent);
                ///Toast.makeText(this, "entry", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void getData_JSONObject(){
        Response.Listener<JSONArray> response_listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("jpyuMsg", "response = " + response.toString());
                parserJsonObject(response);
                Log.i("jpyuMsg", "after parserJsonObject");
            }
        };
        Response.ErrorListener response_errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("jpyuMsg", "error : " + error.toString());
            }
        };

        String url = "https://cloud.culture.tw/frontsite/trans/SearchShowAction.do?method=doFindTypeJ&category=1";
        Log.i("jpyuMsg", url);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, response_listener, response_errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
        Log.i("jpyuMsg", "csist");
    }
    private void parserJsonObject(JSONArray jsonArray){
        Log.i("jpyuMsg", "jpyu parser");
        DH = new SQLite(this);
        try {
            int i=0;
            while (true) {
                JSONObject data = jsonArray.getJSONObject(i);
                JSONObject data2 = data.getJSONArray("showInfo").getJSONObject(0);
                ///Log.i("jpyuMsg", data.toString());

                String title = data.getString("title");
                ///Log.i("jpyuMsg", data.getString("title"));


                String time = data2.getString("time");
                //Log.i("jpyuMsg", data2.getString("time"));
                String location = data2.getString("location");
                //Log.i("jpyuMsg", data2.getString("location"));
                String locationName = data2.getString("locationName");
                //Log.i("jpyuMsg", data2.getString("locationName"));
                String onSales = data2.getString("onSales");
                //Log.i("jpyuMsg", data2.getString("onSales"));
                String price = data2.getString("price");
                ///Log.i("jpyuMsg", data2.getString("price"));
                String endTime = data2.getString("endTime");
                ///Log.i("jpyuMsg", data2.getString("endTime"));


                String descriptionFilterHtml = data.getString("descriptionFilterHtml");
                ///Log.i("jpyuMsg", data.getString("descriptionFilterHtml"));
                String masterUnit = data.getString("masterUnit");
                ///Log.i("jpyuMsg", data.getString("masterUnit"));
                String webSales = data.getString("webSales");
                ///Log.i("jpyuMsg", data.getString("webSales"));
                String sourceWebPromote = data.getString("sourceWebPromote");
                ///Log.i("jpyuMsg", data.getString("sourceWebPromote"));

                int hitRate = data.getInt("hitRate");
                //Log.i("jpyuMsg", data.getInt("hitRate"));

                add(title, time, location, locationName, onSales, price, endTime, descriptionFilterHtml, masterUnit, webSales, sourceWebPromote, hitRate, byteArray);
                i++;

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
    }

    public void add(String title, String time, String location, String locationName, String onSales, String price, String endTime, String descriptionFilterHtml, String masterUnit, String webSales, String sourceWebPromote , int hitRate, byte[] image) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db =DH.getWritableDatabase();
        values.put("_title", title);
        values.put("_time", time);
        values.put("_location", location);
        values.put("_locationName", locationName);
        values.put("_onSales", onSales);
        values.put("_price", price);
        values.put("_endTime", endTime);
        values.put("_descriptionFilterHtml", descriptionFilterHtml);
        values.put("_masterUnit", masterUnit);
        values.put("_webSales", webSales);
        values.put("_sourceWebPromote", sourceWebPromote);
        values.put("_hitRate", hitRate);
        values.put("_photo", image);
        long result = db.insert("music", null, values);
        if (result == -1) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        } /*else {
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
        }*/
    }
}