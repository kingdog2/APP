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
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/////當日有被點到 才會顯示 (需要目前時間)select * from image where time_0110 is not null
public class check extends AppCompatActivity implements View.OnClickListener{
    ///今天日期
    String  _data_time = "time_0113";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    Button excel_get;
    FloatingActionButton add_button;
    Intent intent;
    private MyAdapter myAdapter;
    ArrayList<MyListData> listdata2 = new ArrayList<MyListData>();

    Connection connection;
    File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/check.xls");

    ///獲取資料
    public void createData(){
        connect_sql con = new connect_sql();
        connection = con.connect();
        MyListData myListData;
        Bitmap bitmap;

        if (con != null) {
            try {
                String sql = "select * from image where "+ _data_time +" is not null";
                Statement smt = connection.prepareStatement(sql);
                ResultSet set = smt.executeQuery(sql);
                set.first();

                ///找當日time_XXX 在SQL的值是多少
                int today_index = 0;
                ResultSetMetaData rsmd = set.getMetaData();
                for (int i=1;i<=rsmd.getColumnCount();i++){
                    if (rsmd.getColumnLabel(i).equals(_data_time)) {
                        today_index = i;
                        Toast.makeText(this, rsmd.getColumnLabel(i), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                ///每列資料
                while (!set.wasNull()) {
                    /// bytes 轉成 bitmap (SQL圖片的部分)
                    if (set.getBytes(3)==null){
                        bitmap = null;
                    }else {
                        bitmap  = BitmapFactory.decodeByteArray(set.getBytes(3), 0, set.getBytes(3).length);
                    }
                    myListData = new MyListData(set.getString(2), set.getString(4), set.getString(today_index), bitmap,0);
                    listdata2.add(myListData);
                    set.next();
                }
                connection.close();
            } catch (Exception e) {
                Log.i("Error:", e.getMessage());
            }
        }
    }
    ///創EXCEL(不管有無都重新創)
    private void createExcelFile(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("演算法");

        connect_sql con = new connect_sql();
        connection = con.connect();
        if (con != null){
            try {
                String sql = "select * from image";
                Statement smt = connection.prepareStatement(sql);
                ResultSet set = smt.executeQuery(sql);
                set.first();

                ////////excel儲存
                ///欄位row0
                HSSFRow row0 = sheet.createRow(0);
                if(wb.getSheet("微積分")==null){
                    wb.createSheet("微積分");///小心!!!有還創會一樣失敗
                }
                /// ***取得欄位數與名稱***
                ResultSetMetaData rsmd = set.getMetaData();
                for (int i=1;i<=rsmd.getColumnCount();i++){
                    HSSFCell cell = row0.createCell(i-1);
                    cell.setCellValue(rsmd.getColumnLabel(i));
                    sheet.setColumnWidth(i-1, 30*200);
                }
                //// ***資料row1,2,3,4,5,6,,,***
                int data_len = 1;
                set.first();
                while(!set.wasNull()){

                    HSSFRow row123 = sheet.createRow(data_len);
                    HSSFCell cell = row123.createCell(0);
                    cell.setCellValue(set.getString(1));
                    cell = row123.createCell(1);
                    cell.setCellValue(set.getString(2));

                    cell = row123.createCell(2);
                    cell.setCellValue("image");
                    cell = row123.createCell(3);
                    cell.setCellValue(set.getString(4));
                    for (int j=5;j<=rsmd.getColumnCount();j++){
                        ///圖片的話跳過
                        cell = row123.createCell(j-1);
                        cell.setCellValue(set.getString(j));
                    }
                    System.out.println(set.getString(1));
                    set.next();
                    data_len++;
                }
                System.out.println(data_len);
                connection.close();
            } catch (Exception e){
                Log.i("Error:", e.getMessage());
            }
            ///excel是否成功存檔
            try {
                filePath.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(filePath);
                wb.write(outputStream);
                Toast.makeText(getApplicationContext(), "Excel Created in ", Toast.LENGTH_SHORT).show();
                if (outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
    ///從資料庫刪除被選取資料的當日點名
    private void delete_check_sql(String _studentID){
        connect_sql con = new connect_sql();
        connection = con.connect();
        if (con != null) {
            try {
                String sql = "UPDATE `image` SET `"+ _data_time +"`= null WHERE `studentID` = " + _studentID;
                Statement smt = connection.prepareStatement(sql);
                smt.execute(sql);
                connection.close();
            } catch (Exception e) {
                Log.i("Error:", e.getMessage());
            }
        }
    }
    ///給add_check 補點名
    /*
    private void add_check_sql(String _studentID){
        connect_sql con = new connect_sql();
        connection = con.connect();
        if (con != null) {
            try {///目前時間XX:XX
                Date date = new Date();
                SimpleDateFormat dateFormat= new SimpleDateFormat("hh:mm");
                String sql = "UPDATE `image` SET `"+ _data_time +"`= '" + dateFormat.format(date) + "' WHERE `studentID` = " + _studentID;
                Statement smt = connection.prepareStatement(sql);
                smt.execute(sql);
                connection.close();
            } catch (Exception e) {
                Log.i("Error:", e.getMessage());
            }
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        excel_get = findViewById(R.id.excel_get);
        excel_get.setOnClickListener(this);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ///創資料
        createData();


        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_button:
                ///intent = new Intent(this, add_check.class);
                ///startActivity(intent);
                Toast.makeText(this, "新增點名", Toast.LENGTH_SHORT).show();
                break;
            case R.id.excel_get:
                createExcelFile();
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
            ImageButton item_image;
            TextView item_name, item_studentID, item_introduce;

            public MyViewHolder(@NonNull View v) {
                super(v);
                itemView = v;
                item_image = v.findViewById(R.id.item_image);
                item_name = v.findViewById(R.id.item_name);
                item_studentID = v.findViewById(R.id.item_studentID);
                item_introduce = v.findViewById(R.id.item_introduce);
            }
        }

        @NonNull
        @Override
        ///讀取item 這個layout
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
            MyAdapter.MyViewHolder vh = new MyAdapter.MyViewHolder(item);
            return vh;
        }

        ///給值、按鈕事件都在這呦~~~~~~
        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            holder.item_name.setText(listdata2.get(position).getName());
            holder.item_studentID.setText(listdata2.get(position).getStudentID());
            holder.item_introduce.setText(listdata2.get(position).getIntroduce());
            ///給予圖片
            if (listdata2.get(position).getImageId()==null){
                holder.item_image.setImageResource(android.R.drawable.ic_dialog_alert);
            }else {
                holder.item_image.setImageBitmap(listdata2.get(position).getImageId());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ///存點擊藥師的名稱
                    String _name = ((TextView)(v.findViewById(R.id.item_name))).getText().toString();

                    ///小事框
                    AlertDialog dialog = new AlertDialog.Builder(check.this)
                            .setTitle("刪除提醒!!!")
                            .setMessage("確定刪除"+_name)
                            .setPositiveButton("OK", null)
                            .setNegativeButton("Cancel", null)
                            .show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ///add_check 的部分
                            //add_check_sql((listdata2.get(holder.getAdapterPosition())).getStudentID());
                            ///刪除此資料在SQL的
                            delete_check_sql((listdata2.get(holder.getAdapterPosition())).getStudentID());
                            listdata2.remove(holder.getAdapterPosition());
                            myAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(check.this, "delete scuess", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }

        @Override
        public int getItemCount() {
            return listdata2.size();
        }
    }
}