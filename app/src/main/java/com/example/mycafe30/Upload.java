package com.example.mycafe30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Upload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
    private final String myBucket = "gs://gaapp-ec04b.appspot.com";
    private StorageReference mStorageRef;
    private String TAG = "TAG";

    public Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        Intent intent = getIntent();
        final String key = intent.getStringExtra("key");
        final String menuuserid = intent.getStringExtra("matkuluserid");
        final String source = intent.getStringExtra("source");
        final String urlPhoto = intent.getStringExtra("urlPhoto");

        Toast.makeText(this, "Upload image for "+source, Toast.LENGTH_SHORT).show();

        Button buttonChooseImage = findViewById(R.id.buttonChooseImage);
        Button buttonUploadImage = findViewById(R.id.buttonUploadImage);

        final EditText editTextImageName = findViewById(R.id.editTextImageName);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(myBucket);

        try {
            setDefaultImage(urlPhoto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                Log.d("hello", "images/"+editTextImageName.getText().toString()+".jpg");

                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
            }
        });

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference riversRef;

                riversRef = mStorageRef.child("images/"+editTextImageName.getText().toString()+".jpg");

                FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance().getReference().getStorage();
                StorageReference desertRef = mFirebaseStorage.getReferenceFromUrl(urlPhoto);


                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Log.d(TAG, urlPhoto+" DELETED");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Log.d(TAG, urlPhoto+" DELETE FAILED");
                    }
                });
//                Uri uploadUri = Uri.fromFile(new File(filePath.toString()));
                UploadTask uploadTask = riversRef.putFile(filePath);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(), "Storage success", Toast.LENGTH_SHORT).show();

                        mStorageRef.child("images/"+editTextImageName.getText().toString()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef;

                                String downloadUrl = uri.toString();

                                Log.d(TAG,source);
                                Log.d(TAG,key);
                                Log.d(TAG,menuuserid);
                                Log.d(TAG,downloadUrl);
                                if(source.equals("user")){
                                    myRef = database.getReference("Users");
//                                    User mUser = new User(key, "", "", downloadUrl);
                                    myRef.child(key).child("image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Upload.this, "Success! User Image was save in database.",
                                                        Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(Upload.this, "Oops! Something went wrong, please try again",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else if(source.equals("matkul")){
                                    myRef = database.getReference("Matkul").child(menuuserid);
//                                    Matkul mMatkul = new Matkul(key, "", "", downloadUrl);
                                    myRef.child(key).child("matkulImage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Upload.this, "Success! Matkul Image was save in database.",
                                                        Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(Upload.this, "Oops! Something went wrong, please try again",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Storage failed, error: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                Log.d("hello", "IDK WHAT HAPPENED");
                                Log.d("hello", e.getMessage().toString());
                            }
                        });
                    }


                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Storage fail"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("storage",e.getMessage());
                    }
                });

                Log.d("hello", "Process complete");
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageViewOutput = (ImageView) findViewById(R.id.imageViewOutput);
        Button buttonChooseImage = (Button) findViewById(R.id.buttonChooseImage);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Log.d("hello", "banana: "+filePath.toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageViewOutput.setImageBitmap(bitmap);
                buttonChooseImage.setText(filePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDefaultImage(String urlPhoto) throws IOException {
        ImageView imageViewOutput = findViewById(R.id.imageViewOutput);
        Glide.with(this).load(urlPhoto).into(imageViewOutput);
    }
}
