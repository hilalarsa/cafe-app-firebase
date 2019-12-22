package com.example.mycafe30;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycafe30.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button btLogin;
    EditText username, password;

    DatabaseReference databaseReference;
    List<User> Users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.btLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");



        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameFromEditText = username.getText().toString();
                final String passwordFromEditText = password.getText().toString();
                databaseReference.orderByChild("username").equalTo(usernameFromEditText).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            User userObj = user.getValue(User.class);

                            if (userObj.getPassword().equals(passwordFromEditText)) {
                                Toast.makeText(LoginActivity.this, "Welcome! You've logged in as : Admin", Toast.LENGTH_SHORT).show();

                                if(userObj.getLevel().equals("1")){
                                    Toast.makeText(LoginActivity.this, "Welcome! You've logged in as : Admin", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this, "Welcome! You've logged in as : Kasir", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, KasirActivity.class);
                                    startActivity(intent);
                                }

                            } else {
                                Toast.makeText(LoginActivity.this, "wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("TAG", databaseError.toString());
                    }
                });
            }
        });
    }
}
