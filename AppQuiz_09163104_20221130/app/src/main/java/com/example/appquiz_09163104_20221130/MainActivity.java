package com.example.appquiz_09163104_20221130;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    Button bparsejson;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);
        bparsejson = (Button) findViewById(R.id.bparesjson);
        bparsejson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData_JSONObject();
            }
        });
    }
    private void getData_JSONObject(){
        Response.Listener<JSONObject> response_listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        ///https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json
        String url = "https://gis.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json";
        Log.i("jpyuMsg", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, response_listener, response_errorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
        Log.i("jpyuMsg", "csist");
    }
    private void parserJsonObject(JSONObject jsonObject){
        Log.i("jpyuMsg", "jpyu parser");
        try {
            JSONObject data = jsonObject.getJSONObject("XML_Head");
            data = data.getJSONObject("Infos");
            JSONArray data2 = data.getJSONArray("Info");
            Log.i("jpyuMsg", data.toString());
            JSONObject obj = data2.getJSONObject(0);
            String Name = obj.getString("Name");
            String Toldescribe = obj.getString("Toldescribe");
            output.setText("景點"+Name+"\n"+"景點介紹:"+Toldescribe);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}