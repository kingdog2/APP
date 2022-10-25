package com.example.ex05_recyclerview_mylistadapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.ex05_recyclerview_mylistadapter.MyListAdapter;
import com.example.ex05_recyclerview_mylistadapter.MyListData;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.recyclerview);
        MyListAdapter adapter = new MyListAdapter(getMyListData());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public MyListData[] getMyListData(){
        MyListData[] myListData =new MyListData[]{
                new MyListData("Email", android.R.drawable.ic_dialog_email);
                new MyListData("Info", android.R.drawable.ic_dialog_info);
                new MyListData("Delete", android.R.drawable.ic_delete);
                new MyListData("Dialer", android.R.drawable.ic_dialog_dialer);
                new MyListData("Alert", android.R.drawable.ic_dialog_alert);
                new MyListData("Map", android.R.drawable.ic_dialog_map);
                new MyListData("Email", android.R.drawable.ic_dialog_email);
                new MyListData("Info", android.R.drawable.ic_dialog_info);
                new MyListData("Delete", android.R.drawable.ic_delete);
                new MyListData("Dialer", android.R.drawable.ic_dialog_dialer);
                new MyListData("Alert", android.R.drawable.ic_dialog_alert);
                new MyListData("Map", android.R.drawable.ic_dialog_map);
        };
        return myListData;
    }
}