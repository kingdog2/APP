package com.example.check_excel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button){
            Intent intent = new Intent(this, student.class);
            startActivity(intent);
            //createExcelFile();
        }
    }
}

 /*
    ******* Connection connection;
    File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/check.xls");
    *******
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
    */
/*考慮到更新(不覆蓋)
    ///創EXCEL(不覆蓋)
    private void createExcelFile(){
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("演算法");
        try {
            ///不存在 建立
            if (!filePath.exists()){
                filePath.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(filePath);
                wb.write(outputStream);
                Toast.makeText(getApplicationContext(), "Excel Created in " + filePath, Toast.LENGTH_SHORT).show();
                if (outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
            }
            ///更改excel內容
            updateExcel();
        } catch (Exception e){
            Toast.makeText(this, "(Failed)檔案出錯", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void updateExcel(){
        FileInputStream input = null;
        HSSFWorkbook book;
        HSSFSheet sheet;
        ///讀
        try {
            input = new FileInputStream(filePath);
            ///讀取sql server資料並寫入excel裡面
            book = new HSSFWorkbook(input);
            sheet = book.getSheetAt(0);
            if(book.getSheet("微積分")==null){
                book.createSheet("微積分");///小心!!!有還創會一樣失敗
            }
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
                        cell.setCellValue(set.getInt(1));
                        cell = row123.createCell(1);
                        cell.setCellValue(set.getString(2));
                        cell = row123.createCell(2);
                        cell.setCellValue(set.getString(4));

                        for (int j=5;j<=rsmd.getColumnCount();j++){
                            ///圖片的話跳過
                            cell = row123.createCell(j-2);
                            cell.setCellValue(set.getString(j));
                        }
                        set.next();
                        data_len++;
                    }
                    connection.close();
                } catch (Exception e){
                    Log.i("Error:", e.getMessage());
                }
            }

            ///寫(覆蓋)
            try {
                FileOutputStream outputStream = new FileOutputStream(filePath);
                book.write(outputStream);
                if (outputStream!=null){
                    outputStream.flush();
                    outputStream.close();
                }
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "(Failed)寫入excel出錯", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "(Failed)讀取excel出錯", Toast.LENGTH_SHORT).show();
        }
    }
*/
