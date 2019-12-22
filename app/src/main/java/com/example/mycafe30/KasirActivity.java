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

public class KasirActivity extends AppCompatActivity {
    Button btnMenu, btnMeja, btnCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasir);

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
        btnMenu = findViewById(R.id.btnMenu);
        btnMeja = findViewById(R.id.btnMeja);
        btnCafe = findViewById(R.id.btnEditCafe);
    }

    public void initListener(){
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KasirActivity.this, ListMenu.class);
                startActivity(intent);
            }
        });
        btnMeja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KasirActivity.this, ListMeja.class);
                startActivity(intent);
            }
        });
        btnCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KasirActivity.this, ListCafe.class);
                startActivity(intent);
            }
        });
    }
}
