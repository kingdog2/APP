package com.example.check_excel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class student extends AppCompatActivity implements View.OnClickListener {
    Connection connection;

    Intent intent;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton add_button;
    private MyAdapter myAdapter;

    ArrayList<MyListData> listdata = new ArrayList<MyListData>();

    public void createData(){
        connect_sql con = new connect_sql();
        connection = con.connect();
        MyListData myListData;
        Bitmap bitmap;
        if (con != null) {
            try {
                String sql = "select * from image";
                Statement smt = connection.prepareStatement(sql);
                ResultSet set = smt.executeQuery(sql);
                set.first();

                while (!set.wasNull()) {
                    /// bytes 轉成 bitmap (SQL圖片的部分)
                    if (set.getBytes(3)==null){
                            bitmap = null;
                    }else {
                       bitmap  = BitmapFactory.decodeByteArray(set.getBytes(3), 0, set.getBytes(3).length);
                    }
                    myListData = new MyListData(set.getString(2), set.getString(4),"132", bitmap,0);
                    listdata.add(myListData);
                    set.next();
                }
                connection.close();
            } catch (Exception e) {
                Log.i("Error:", e.getMessage());
            }
        }
        /*測試資料
        MyListData myListData;
        myListData = new MyListData("A同學", "09163104","132",android.R.drawable.ic_dialog_alert,0);
        listdata.add(myListData);
        myListData = new MyListData("B同學", "09148104","132",android.R.drawable.ic_dialog_alert,1);
        listdata.add(myListData);
        myListData = new MyListData("C同學", "09185604","132",android.R.drawable.ic_dialog_alert,1);
        listdata.add(myListData);
        myListData = new MyListData("D同學", "09166504","132",android.R.drawable.ic_dialog_alert,1);
        listdata.add(myListData);
        myListData = new MyListData("E同學", "09163154","132",android.R.drawable.ic_dialog_alert,1);
        listdata.add(myListData);
        myListData = new MyListData("F同學", "09163108","132",android.R.drawable.ic_dialog_map,1);
        listdata.add(myListData);
        myListData = new MyListData("G同學", "09173104","132",android.R.drawable.ic_dialog_email,1);
        listdata.add(myListData);
        myListData = new MyListData("H同學", "09183104","132",android.R.drawable.ic_dialog_info,1);
        listdata.add(myListData);
        myListData = new MyListData("I同學", "09165104","132",android.R.drawable.ic_delete,1);
        listdata.add(myListData);
        myListData = new MyListData("J同學", "09163454","132",android.R.drawable.ic_dialog_dialer,1);
        listdata.add(myListData);
        myListData = new MyListData("K同學", "09161104","132",android.R.drawable.ic_dialog_alert,1);
        listdata.add(myListData);
        myListData = new MyListData("L同學", "091786263","132",android.R.drawable.ic_dialog_alert,1);
        listdata.add(myListData);*/
    }
    ///刪除所選取的同學(被褪學or沒讀了)
    private void delete_student_sql(String _studentID){
        connect_sql con = new connect_sql();
        connection = con.connect();
        if (con != null) {
            try {///DELETE FROM `image` WHERE `studentID` = '564646';
                String sql = "DELETE FROM `image` WHERE `studentID` = '"+_studentID+"';";
                Statement smt = connection.prepareStatement(sql);
                smt.execute(sql);
                connection.close();
            } catch (Exception e) {
                Log.i("Error:", e.getMessage());
            }
        }
    }
    ///給add_student用的新增新同學 未完成
    private void add_student_sql(String _studentID){
        connect_sql con = new connect_sql();
        connection = con.connect();
        if (con != null) {
            try {
                String sql = "INSERT INTO `image`(`id`, `name`, `image`, `studentID`) VALUES ('','','','')";
                Statement smt = connection.prepareStatement(sql);
                smt.execute(sql);
                connection.close();
            } catch (Exception e) {
                Log.i("Error:", e.getMessage());
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //intent = getIntent();
        ///監聽按鈕
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(this);

        ///創資料
        createData();
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.add_button:
                ///intent = new Intent(this, add_sutdent.class);
                //startActivity(intent);
                Toast.makeText(this, "建立咯!!!~~~~~~~", Toast.LENGTH_SHORT).show();
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        class MyViewHolder extends RecyclerView.ViewHolder{
            public View itemView;
            ImageView item_image;
            TextView item_name, item_studentID;

            public MyViewHolder(@NonNull View v) {
                super(v);
                itemView = v;
                item_image = v.findViewById(R.id.item_image);
                item_name = v.findViewById(R.id.item_name);
                item_studentID = v.findViewById(R.id.item_studentID);
            }
        }

        @NonNull
        @Override
        ///讀取item 這個layout
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            MyViewHolder vh = new MyViewHolder(item);
            return vh;
        }

        ///給值、按鈕事件都在這呦~~~~~~
        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.item_name.setText(listdata.get(position).getName());
            holder.item_studentID.setText(listdata.get(position).getStudentID());
            ///給予圖片
            if (listdata.get(position).getImageId()==null){
                holder.item_image.setImageResource(android.R.drawable.ic_dialog_alert);
            }else {
                holder.item_image.setImageBitmap(listdata.get(position).getImageId());
            }
            ///按
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(student.this, ((TextView)(v.findViewById(R.id.item_name))).getText().toString(), Toast.LENGTH_SHORT).show();
                    ///存點擊藥師的名稱
                    String _name = ((TextView)(v.findViewById(R.id.item_name))).getText().toString();
                    ///小事框
                    AlertDialog dialog = new AlertDialog.Builder(student.this)
                            .setTitle("刪除提醒!!!")
                            .setMessage("確定刪除"+_name)
                            .setPositiveButton("OK", null)
                            .setNegativeButton("Cancel", null)
                            .show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete_student_sql((listdata.get(holder.getAdapterPosition())).getStudentID());
                            listdata.remove(holder.getAdapterPosition());
                            myAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(student.this, "delete scuess", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }
    }
}