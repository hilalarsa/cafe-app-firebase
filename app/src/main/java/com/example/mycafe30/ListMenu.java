package com.example.mycafe30;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycafe30.ListAdapter.MenuList;
import com.example.mycafe30.Model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListMenu extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<Menu> Menus;

    Button btnAdd;
    EditText editTextNamaMenu, editTextDeskripsi, editTextHarga;
    ListView listViewMenus;

    String ID_USER;

    private String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_menu);

        Intent intent = getIntent();
        ID_USER = intent.getStringExtra("ID_USER");
        Log.d("TAG", ID_USER);

        initViews();
        initListener();

        databaseReference = FirebaseDatabase.getInstance().getReference("Menu").child(ID_USER);

        listViewMenus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Menu Menu = Menus.get(i);
                CallUpdateAndDeleteDialog(Menu.getIdMenu(), Menu.getNamaMenu(), Menu.getDeskripsi(), Menu.getHarga(), Menu.getGambar(), Menu.getIdUser());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous Menu list
                Menus.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Menu from firebase console
                    Menu Menu = postSnapshot.getValue(Menu.class);
                    //adding Menu to the list
                    Menus.add(Menu);
                }
                //creating Menulist adapter
                MenuList MenuAdapter = new MenuList(ListMenu.this, Menus);
                //attaching adapter to the listview
                listViewMenus.setAdapter(MenuAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String id_menu, final String nama_menu, final String deskripsi, final String harga, final String gambar,final String id_user) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_popup_menu, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateNamaMenu = dialogView.findViewById(R.id.updateNamaMenu);
        final EditText updateDeskripsi = dialogView.findViewById(R.id.updateDeskripsi);
        final EditText updateHarga = dialogView.findViewById(R.id.updateHarga);

        updateNamaMenu.setText(nama_menu);
        updateDeskripsi.setText(deskripsi);
        updateHarga.setText(harga);

        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateMenu);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteMenu);
        final Button buttonUpload = dialogView.findViewById(R.id.buttonUploadImage);
//        final Button buttonViewList = dialogView.findViewById(R.id.buttonViewList);
        //username for set dialog title
        dialogBuilder.setTitle("Please fill with your data");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama_menu = updateNamaMenu.getText().toString().trim();
                String deskripsi = updateDeskripsi.getText().toString().trim();
                String harga = updateHarga.getText().toString().trim();
                //checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(nama_menu)) {
                    if (!TextUtils.isEmpty(deskripsi)) {
                        if (!TextUtils.isEmpty(harga)) {
                            updateMenu(id_menu, nama_menu, deskripsi, harga, "");
                            b.dismiss();

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
                deleteMenu(id_menu);
                b.dismiss();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Method for delete data
                Intent intent = new Intent(ListMenu.this, Upload.class);
                intent.putExtra("key", id_menu);
                intent.putExtra("ID_USER", ID_USER);
                intent.putExtra("urlPhoto", gambar);
                startActivity(intent);
            }
        });
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
        editTextNamaMenu = findViewById(R.id.nama_menu);
        editTextDeskripsi = findViewById(R.id.deskripsi);
        editTextHarga = findViewById(R.id.harga);

        listViewMenus = findViewById(R.id.listViewMenus);
        Menus = new ArrayList<>();
    }

    private void initListener(){
        //adding an onclicklistener to button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMenu();
            }
        });
    }



    private void addMenu() {
        //getting the values to save
        String nama_menu = editTextNamaMenu.getText().toString().trim();
        String deskripsi = editTextDeskripsi.getText().toString().trim();
        String harga = editTextHarga.getText().toString().trim();

        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(nama_menu)) {
            if (!TextUtils.isEmpty(deskripsi)) {

                //it will create a unique id and we will use it as the Primary Key for our User
                String id = databaseReference.push().getKey();
                //creating an User Object
                Menu Menu = new Menu(id, nama_menu, deskripsi, harga, "", ID_USER);
                //Saving the Menu
                databaseReference.child(id).setValue(Menu);

                editTextNamaMenu.setText("");
                editTextDeskripsi.setText("");
                editTextHarga.setText("");
                Toast.makeText(this, "Menu added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please enter an Email", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a Name", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateMenu(String id, String nama_menu, String deskripsi, String harga, String gambar) {
        //getting the specified User reference
        DatabaseReference UpdateReference = databaseReference.child(id);
        Menu Menu = new Menu(id, nama_menu, deskripsi, harga, gambar, ID_USER);
        //update  User  to firebase
        UpdateReference.setValue(Menu);
        Toast.makeText(getApplicationContext(), "Menu Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteMenu(String id) {
        //getting the specified Menu reference
        DatabaseReference DeleteReference = databaseReference.child(id);
        //removing Menu
        DeleteReference.removeValue();
//        DeleteReferenceMatkul.removeValue();
        Toast.makeText(getApplicationContext(), "Menu Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

}
