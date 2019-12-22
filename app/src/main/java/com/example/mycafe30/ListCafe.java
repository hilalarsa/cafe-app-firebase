package com.example.mycafe30;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mycafe30.ListAdapter.CafeList;
import com.example.mycafe30.ListAdapter.MenuList;
import com.example.mycafe30.Model.Cafe;
import com.example.mycafe30.Model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ListCafe extends AppCompatActivity {
    DatabaseReference databaseReference;
    List<Cafe> Cafes;

    Button btnAdd;
    EditText editTextNamaCafe, editTextLokasi, editTextDeskripsi;
    ListView listViewCafes;

    private String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cafe);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cafe");

        initViews();
        initListener();

        listViewCafes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cafe Cafe = Cafes.get(i);
                CallUpdateAndDeleteDialog(Cafe.getIdCafe(), Cafe.getNamaCafe(), Cafe.getLokasi(), Cafe.getDeskripsi(), Cafe.getIdUser());
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
                Cafes.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Menu from firebase console
                    Cafe Cafe = postSnapshot.getValue(Cafe.class);
                    //adding Menu to the list
                    Cafes.add(Cafe);
                }
                //creating Menulist adapter
                CafeList CafeAdapter = new CafeList(ListCafe.this, Cafes);
                //attaching adapter to the listview
                listViewCafes.setAdapter(CafeAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String id_cafe, String nama_cafe, String lokasi, final String deskripsi,final String id_user) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_popup_cafe, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateNamaCafe = dialogView.findViewById(R.id.updateNamaCafe);
        final EditText updateLokasi = dialogView.findViewById(R.id.updateLokasi);
        final EditText updateDeskripsi = dialogView.findViewById(R.id.updateDeskripsi);

        updateNamaCafe.setText(nama_cafe);
        updateLokasi.setText(lokasi);
        updateDeskripsi.setText(deskripsi);


        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateCafe);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteCafe);
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
                String nama_cafe = updateNamaCafe.getText().toString().trim();
                String lokasi = updateLokasi.getText().toString().trim();
                String deskripsi = updateDeskripsi.getText().toString().trim();
                //checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(nama_cafe)) {
                    if (!TextUtils.isEmpty(lokasi)) {
                        if (!TextUtils.isEmpty(deskripsi)) {
                                //Method for update data
                                updateCafe(id_cafe, nama_cafe, lokasi, deskripsi);
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
                deleteCafe(id_cafe);
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
        editTextNamaCafe = findViewById(R.id.nama_cafe);
        editTextLokasi = findViewById(R.id.lokasi);
        editTextDeskripsi = findViewById(R.id.deskripsi);

        listViewCafes = findViewById(R.id.listViewCafes);
        Cafes = new ArrayList<>();
    }

    private void initListener(){
        //adding an onclicklistener to button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCafe();
            }
        });
    }

    private void addCafe() {
        //getting the values to save
        String nama_cafe = editTextNamaCafe.getText().toString().trim();
        String lokasi = editTextLokasi.getText().toString().trim();
        String deskripsi = editTextDeskripsi.getText().toString().trim();

        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(nama_cafe)) {
            if (!TextUtils.isEmpty(lokasi)) {

                //it will create a unique id and we will use it as the Primary Key for our User
                String id = databaseReference.push().getKey();
                //creating an User Object
                Cafe Cafe = new Cafe(id, nama_cafe, lokasi, deskripsi, "1");
                //Saving the Menu
                databaseReference.child(id).setValue(Cafe);

                editTextNamaCafe.setText("");
                editTextLokasi.setText("");
                editTextDeskripsi.setText("");
                Toast.makeText(this, "Cafe added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please enter an Email", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a Name", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateCafe(String id, String nama_cafe, String lokasi, String deskripsi) {
        //getting the specified User reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Cafe").child(id);
        Cafe Cafe = new Cafe(id, nama_cafe, lokasi, deskripsi, "1");
        //update  User  to firebase
        UpdateReference.setValue(Cafe);
        Toast.makeText(getApplicationContext(), "Cafe Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteCafe(String id) {
        //getting the specified Menu reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Cafe").child(id);
        //removing Menu
        DeleteReference.removeValue();
//        DeleteReferenceMatkul.removeValue();
        Toast.makeText(getApplicationContext(), "Cafe Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
