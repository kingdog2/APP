package com.example.check_excel;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/*無法遠端連接MySQL：message from server: "Host xxx is not allowed to connect to this MySQL server"
代表遠端登入root被限制*/
public class connect_sql{
    Connection connection;
    public Connection connect(){ ///172.20.10.2 192.168.0.111
        String ip="172.20.10.2", port="3306", db="aaa", username="george", password="5595";
        ///使用 StrictMode 找出在主執行緒的異常請求
        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String connectURL = null;
        try{
            ///
            /*jtds是比較穩定的驅動程式, 下載網址如下
            mysql-connector-java-5.1.27.jar
            JDBC API 是資料庫廠商依據JDBC規範的interface所開發出來的類別, 這些API重定了 Date, Time, SQLException等相關的類別, 所以又把這些廠商提供的API稱為驅動程式
            請注意, 1.3.0版是測試最穩定的版本, 1.3.1測試起來, 有些問題
            可用於MariaDB跟My SQL Server
            詳細參數說明
            http://dev.mysql.com/doc/connector-j/en/connector-j-reference-configuration-properties.html
            Drive Class: com.mysql.jdbc.Driver
            Drive Location: http://www.mysql.com/products/connector/
            JDBC Url Format: jdbc:mysql://<host><:port>/<database>
            My Server預設port為3306
            Examples:
            jdbc:mysql://blog.yslifes.com:3306/test
            jdbc:mysql://127.0.0.1:3306/test
            */
            /// 指定使用的driver
            Class.forName("com.mysql.jdbc.Driver");
            connectURL = "jdbc:mysql://" + ip + ":3306/" + db;
            connection = DriverManager.getConnection(connectURL, username, password);
        }catch (Exception e){
            Log.i("Error is", e.getMessage());
        }
        return connection;
    }
}
