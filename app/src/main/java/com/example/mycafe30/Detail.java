package com.example.mycafe30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class Detail extends AppCompatActivity {

    TextView id, nama_menu, harga, deskripsi;
    String gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        Intent intent = getIntent();
        id.setText(intent.getStringExtra("id"));
        nama_menu.setText(intent.getStringExtra("nama_menu"));
        harga.setText(intent.getStringExtra("harga"));
        deskripsi.setText(intent.getStringExtra("deskripsi"));
        gambar = intent.getStringExtra("gambar");

        try {
            setDefaultImage(gambar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initView(){
        id = findViewById(R.id.id);
        nama_menu = findViewById(R.id.nama_menu);
        harga = findViewById(R.id.harga);
        deskripsi = findViewById(R.id.deskripsi);
    }

    public void setDefaultImage(String urlPhoto) throws IOException {
        ImageView imageViewOutput = findViewById(R.id.imageViewOutput);
        Glide.with(this).load(urlPhoto).into(imageViewOutput);
    }

}
