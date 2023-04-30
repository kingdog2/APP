package com.example.ra08d_recycleview_readfromassets_json01_09163104;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> personNames;
    ArrayList<String> emailIds;
    ArrayList<String> mobileNumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("users");
            for(int i=0; i<userArray.length(); i++) {
                JSONObject userDetail = userArray.getJSONObject(i);

                personNames.add(userDetail.getString("name"));
                emailIds.add(userDetail.getString("email"));
                JSONObject contact = userDetail.getJSONObject("contact");
                mobileNumbers.add(userDetail.getString("mobile"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapter CustomAdapter = new CustomAdapter(MainActivity.this, personNames, emailIds, mobileNumbers);
        recyclerView.setAdapter(CustomAdapter);
    }


    public String loadJSONFromAsset(){
        String json = null;
        try{
            InputStream is = getAssets().open("users_list.json");
            int size =is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}