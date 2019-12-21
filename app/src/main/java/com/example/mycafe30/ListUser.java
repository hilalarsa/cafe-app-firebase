package com.example.mycafe30;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycafe30.ListAdapter.UserList;
import com.example.mycafe30.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUser extends AppCompatActivity {
    DatabaseReference databaseReference;
    List<User> Users;

    Button btnAdd;
    EditText editTextNama, editTextEmail, editTextUsername, editTextPassword;
    ListView listViewUsers;

    private String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        initViews();
        initListener();

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User User = Users.get(i);
                CallUpdateAndDeleteDialog(User.getIdUser(), User.getNama(), User.getEmail(), User.getUsername(), User.getPassword(), User.getLevel());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous User list
                Users.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting User from firebase console
                    User User = postSnapshot.getValue(User.class);
                    //adding User to the list
                    Users.add(User);
                }
                //creating Userlist adapter
                UserList UserAdapter = new UserList(ListUser.this, Users);
                //attaching adapter to the listview
                listViewUsers.setAdapter(UserAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String id_user, String nama, String email, final String username, final String password,final String level) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_popup_user, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateName = dialogView.findViewById(R.id.updateName);
        final EditText updateEmail = dialogView.findViewById(R.id.updateEmail);
        final EditText updateUsername = dialogView.findViewById(R.id.updateUsername);
        final EditText updatePassword = dialogView.findViewById(R.id.updatePassword);

        updateName.setText(nama);
        updateEmail.setText(email);
        updateUsername.setText(username);
        updatePassword.setText(password);

        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteUser);
//        final Button buttonUpload = dialogView.findViewById(R.id.buttonUploadImage);
//        final Button buttonViewList = dialogView.findViewById(R.id.buttonViewList);
        //username for set dialog title
        dialogBuilder.setTitle("Please fill with your data");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = updateName.getText().toString().trim();
                String email = updateEmail.getText().toString().trim();
                String username = updateUsername.getText().toString().trim();
                String password = updatePassword.getText().toString().trim();
                //checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(nama)) {
                    if (!TextUtils.isEmpty(email)) {
                        if (!TextUtils.isEmpty(username)) {
                            if (!TextUtils.isEmpty(password)) {
                                //Method for update data
                                updateUser(id_user, nama, email, username, password);
                                b.dismiss();
                            }
                        }
                    }
                }

            }
        });
        // Click listener for Delete data
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Method for delete data
                deleteUser(id_user);
                b.dismiss();
            }
        });

//        buttonUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Method for delete data
//                Intent intent = new Intent(MainActivity.this, Upload.class);
//                intent.putExtra("key", userid);
//                intent.putExtra("source", "user");
//                intent.putExtra("matkuluserid", userid);
//                intent.putExtra("urlPhoto", urlPhoto);
//                startActivity(intent);
//            }
//        });
//
//        buttonViewList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Method for delete data
//                Intent intent = new Intent(MainActivity.this, ListMatkul.class);
//                intent.putExtra("key", userid);
////                intent.putExtra("urlPhoto", urlPhoto);
//                startActivity(intent);
//            }
//        });
    }

    private void initViews(){
        btnAdd = findViewById(R.id.btn_add);
        editTextNama = findViewById(R.id.nama);
        editTextEmail = findViewById(R.id.email);
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);

        listViewUsers = findViewById(R.id.listViewUsers);
        Users = new ArrayList<>();
    }

    private void initListener(){
        //adding an onclicklistener to button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        //getting the values to save
        String nama = editTextNama.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(nama)) {
            if (!TextUtils.isEmpty(email)) {

                //it will create a unique id and we will use it as the Primary Key for our User
                String id = databaseReference.push().getKey();
                //creating an User Object
                User User = new User(id, nama, email, username, password, "1");
                //Saving the User
                databaseReference.child(id).setValue(User);

                editTextNama.setText("");
                editTextEmail.setText("");
                editTextUsername.setText("");
                editTextPassword.setText("");
                Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please enter an Email", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a Name", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateUser(String id, String nama, String email, String username, String password) {
        //getting the specified User reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        User User = new User(id, nama, email, username, password, "1");
        //update  User  to firebase
        UpdateReference.setValue(User);
        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteUser(String id) {
        //getting the specified User reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
//        DatabaseReference DeleteReferenceMatkul = FirebaseDatabase.getInstance().getReference("Matkul").child(id);
        //removing User
        DeleteReference.removeValue();
//        DeleteReferenceMatkul.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
