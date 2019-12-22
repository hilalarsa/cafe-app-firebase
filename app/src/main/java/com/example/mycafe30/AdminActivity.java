package com.example.mycafe30;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    Button btnCafe, btnUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();
        initListener();
    }

    //menu logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuLogout:
                SharedPreferences setting = getSharedPreferences("key", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = setting.edit();
                editor.remove("username");
                editor.remove("password");
                editor.commit();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initView(){
        btnCafe = findViewById(R.id.btnCafe);
        btnUser = findViewById(R.id.btnUser);
    }

    public void initListener(){
        btnCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this method is actually performing the write operation
//                Intent intent = new Intent(AdminActivity.this, ListCafe.class);
//                startActivity(intent);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this method is actually performing the write operation
                Intent intent = new Intent(AdminActivity.this, ListUser.class);
                startActivity(intent);
            }
        });
    }
}
