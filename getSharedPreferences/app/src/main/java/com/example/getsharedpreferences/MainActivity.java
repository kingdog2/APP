package com.example.getsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    TextView textView = findViewById(R.id.textView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ///拿資料靠他
        sp = getSharedPreferences("game", MODE_PRIVATE);
        editor = sp.edit(); ///存資料靠她
    }
    public void test1(View view){
        String username = sp.getString("username", "brad");
        boolean isSound = sp.getBoolean("sound", true);
        int stage = sp.getInt("stage", 1);
        Log.v("brad", username + ':' + isSound + ':' + stage);
        textView.setText(username + ':' + isSound + ':' + stage);
    }

    public void test2(View view) {
        editor.putString("username", "ry_user");
        editor.putBoolean("sound", false);
        editor.putInt("stage", 100);
        editor.commit();    ///確認存入
        textView.setText("");
        Toast.makeText(this, "Save OK", Toast.LENGTH_SHORT).show();
    }
}