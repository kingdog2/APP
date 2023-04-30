package com.example.appfinalproject_09163104;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Show_infoActivity extends AppCompatActivity {
    public SQLite DH = new SQLite(this);
    ArrayList<stringData> stringDatas = new ArrayList<stringData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        SQLiteDatabase db =DH.getWritableDatabase();
        Cursor cursor = db.query("music", new String[]{"_id", "_title", "_time", "_location", "_locationName", "_onSales", "_price", "_endTime", "_descriptionFilterHtml", "_masterUnit", "_webSales", "_sourceWebPromote", "_hitRate", "_photo"},null,null,null,null,null);
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            stringData stringData = new stringData();
            stringData.setTitle(cursor.getString(1));
            stringData.setTime(cursor.getString(2));
            stringData.setLocation(cursor.getString(3));
            stringDatas.add(stringData);
            cursor.moveToNext();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

        @NonNull
        @Override
        public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.list_item,parent, false);
            MyListAdapter.ViewHolder viewHolder = new MyListAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder holder, int position) {
            final stringData myListData =  stringDatas.get(position);
            holder.textView0.setText((position+1)+" "+ stringDatas.get(position).getTitle());
            holder.textView1.setText(stringDatas.get(position).getTime());
            holder.textView2.setText(stringDatas.get(position).getLocation());

        }

        @Override
        public int getItemCount() {
            return stringDatas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView textView0;
            public TextView textView1;
            public TextView textView2;
            public LinearLayout linearLayout ;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.textView0 = (TextView) itemView.findViewById(R.id.textView0);
                this.textView1 = (TextView) itemView.findViewById(R.id.textView1);
                this.textView2 = (TextView) itemView.findViewById(R.id.textView2);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Show_infoActivity.this, music_info.class);
                        int i = Integer.valueOf(((TextView)v.findViewById(R.id.textView0)).getText().toString().split(" ")[0]).intValue();
                        intent.putExtra("info", i);
                        startActivity(intent);

                        ///Toast.makeText(Show_infoActivity.this, ((TextView)v.findViewById(R.id.textView0)).getText().toString().split(" ")[0], Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}