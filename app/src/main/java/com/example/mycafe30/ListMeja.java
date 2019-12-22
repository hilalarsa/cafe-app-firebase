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

import com.example.mycafe30.ListAdapter.MejaList;
import com.example.mycafe30.ListAdapter.UserList;
import com.example.mycafe30.Model.Meja;
import com.example.mycafe30.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ListMeja extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<Meja> Mejas;

    Button btnAdd;
    EditText editTextNo, editTextJumlah, editTextStatus;
    ListView listViewMejas;

    private String TAG = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meja);

        databaseReference = FirebaseDatabase.getInstance().getReference("Mejas");

        initViews();
        initListener();

        listViewMejas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meja Meja = Mejas.get(i);
                CallUpdateAndDeleteDialog(Meja.getIdMeja(), Meja.getNoMeja(), Meja.getJumlahKursi(), Meja.getStatus(), Meja.getIdUser());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous Meja list
                Mejas.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Meja from firebase console
                    Meja Meja = postSnapshot.getValue(Meja.class);
                    //adding Meja to the list
                    Mejas.add(Meja);
                }
                //creating Mejalist adapter
                MejaList MejaAdapter = new MejaList(ListMeja.this, Mejas);
                //attaching adapter to the listview
                listViewMejas.setAdapter(MejaAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CallUpdateAndDeleteDialog(final String id_meja, String NoMeja, String JumlahKursi, final String Status, final String IdUser) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_popup_meja, null);
        dialogBuilder.setView(dialogView);
        //Access Dialog views
        final EditText updateNo = dialogView.findViewById(R.id.updateNo);
        final EditText updateJumlah = dialogView.findViewById(R.id.updateJumlah);
        final EditText updateStatus = dialogView.findViewById(R.id.updateStatus);

        updateNo.setText(NoMeja);
        updateJumlah.setText(JumlahKursi);
        updateStatus.setText(Status);

        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateMeja);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteMeja);
//        final Button buttonUpload = dialogView.findViewById(R.id.buttonUploadImage);
//        final Button buttonViewList = dialogView.findViewById(R.id.buttonViewList);
        //mejaname for set dialog title
        dialogBuilder.setTitle("Please fill with your data");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String no = updateNo.getText().toString().trim();
                String jumlah = updateJumlah.getText().toString().trim();
                String status = updateStatus.getText().toString().trim();
                //checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(no)) {
                    if (!TextUtils.isEmpty(jumlah)) {
                        if (!TextUtils.isEmpty(status)) {
                            updateMeja(id_meja, no, jumlah, status);
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
                deleteMeja(id_meja);
                b.dismiss();
            }
        });

    }

    private void initViews() {
        btnAdd = findViewById(R.id.btn_add);
        editTextNo = findViewById(R.id.no);
        editTextJumlah = findViewById(R.id.jumlah);
        editTextStatus = findViewById(R.id.status);

        listViewMejas = findViewById(R.id.listViewMejas);
        Mejas = new ArrayList<>();
    }

    private void initListener(){
        //adding an onclicklistener to button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMeja();
            }
        });
    }

    private void addMeja() {
        //getting the values to save
        String no = editTextNo.getText().toString().trim();
        String jumlah = editTextJumlah.getText().toString().trim();
        String status = editTextStatus.getText().toString().trim();

        //checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(no)) {
            if (!TextUtils.isEmpty(jumlah)) {

                //it will create a unique id and we will use it as the Primary Key for our User
                String id = databaseReference.push().getKey();
                //creating an User Object
                Meja Meja = new Meja(id, no, jumlah, status, "1");
                //Saving the Meja
                databaseReference.child(id).setValue(Meja);

                editTextNo.setText("");
                editTextJumlah.setText("");
                editTextStatus.setText("");
                Toast.makeText(this, "Meja added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please enter an Jumlah", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Please enter a No Meja", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateMeja(String id, String no, String jumlah, String status) {
        //getting the specified Meja reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Mejas").child(id);
        Meja Meja = new Meja(id, no, jumlah, status, "1");
        //update  Meja  to firebase
        UpdateReference.setValue(Meja);
        Toast.makeText(getApplicationContext(), "Meja Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteMeja(String id) {
        //getting the specified Meja reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Mejas").child(id);
//        DatabaseReference DeleteReferenceMatkul = FirebaseDatabase.getInstance().getReference("Matkul").child(id);
        //removing Meja
        DeleteReference.removeValue();
//        DeleteReferenceMatkul.removeValue();
        Toast.makeText(getApplicationContext(), "Meja Deleted", Toast.LENGTH_LONG).show();
        return true;
    }
}
