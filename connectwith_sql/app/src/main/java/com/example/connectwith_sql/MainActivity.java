package com.example.connectwith_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
Connection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton imageButton = findViewById(R.id.imageButton);
        connect_sql con = new connect_sql();
        connection = con.connect();
        if (con != null){
            try {
                String sql = "select * from image";
                Statement smt = connection.prepareStatement(sql);
                ResultSet set = smt.executeQuery(sql);
                set.first();
                /*while(set.next()){
                    System.out.println(set.getString(2));
                    System.out.println(set.getBlob(3));
                }*/

                //取得欄位數與名稱
                ResultSetMetaData rsmd = set.getMetaData();
                for (int i=1;i<=rsmd.getColumnCount();i++){
                    System.out.println(rsmd.getColumnLabel(i));
                }
                /*長度*/
                ///欄位長度for (int i=1;i<=rsmd.getColumnCount();i++){System.out.println(i+rsmd.getColumnLabel(i));}
                //資料長度int data_len = 1;
                //                while(set.next()) {
                //                    data_len++;
                //                }
                //                System.out.println(data_len);


                /*取得圖片*/
                /*法一getBlob*/
                ///blob轉成Byte
                ///Blob blob = set.getBlob(3);
                ///byte [] bytes = blob.getBytes(1l, (int)blob.length());
                ///圖片drawable Byte[] 變 Bitmap
                ///*如果bitmapdata是字節數組，那麼獲取Bitmap是這樣完成的：Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);*/
                ///Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                /*法二getBytes*/
                //Bitmap bitmap = BitmapFactory.decodeByteArray(set.getBytes(3), 0, set.getBytes(3).length);
                //imageButton.setImageBitmap(bitmap);


                connection.close();
            } catch (Exception e){
                Log.i("Error:", e.getMessage());
            }
        }
    }
}