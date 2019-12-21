package com.example.mycafe30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mycafe30.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnCafe, btnMenu, btnMeja, btnUser;

    DatabaseReference databaseReference;
    List<User> Users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

    }

    public void initView(){
        btnCafe = findViewById(R.id.btnCafe);
        btnMenu = findViewById(R.id.btnMenu);
        btnMeja = findViewById(R.id.btnMeja);
        btnUser = findViewById(R.id.btnUser);
    }

    public void initListener(){
//        btnCafe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //this method is actually performing the write operation
//                Intent intent = new Intent(MainActivity.this, ListCafe.class);
//                startActivity(intent);
//            }
//        });
//        btnMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //this method is actually performing the write operation
//                Intent intent = new Intent(MainActivity.this, ListMenu.class);
//                startActivity(intent);
//            }
//        });
//        btnMeja.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //this method is actually performing the write operation
//                Intent intent = new Intent(MainActivity.this, ListMeja.class);
//                startActivity(intent);
//            }
//        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this method is actually performing the write operation
                Intent intent = new Intent(MainActivity.this, ListUser.class);
                startActivity(intent);
            }
        });
    }

}