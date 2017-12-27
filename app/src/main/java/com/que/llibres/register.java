package com.que.llibres;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    String passw1;
    String passw2;
    String email;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    Uri resultUri;
    ImageView foto_gallery;
    private StorageReference mStorageRef;
    String nick;
    String name;
    String id;
String perfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        EditText pass1 = findViewById(R.id.pass1);
        EditText pass2 = findViewById(R.id.pass2);
        passw1 = pass1.getText().toString();
        passw2 = pass2.getText().toString();

    }

    public void next(View view) {
        EditText mail =  findViewById(R.id.mail);
        EditText pass1 = findViewById(R.id.pass1);
        EditText pass2 = findViewById(R.id.pass2);
        passw1 = pass1.getText().toString();
        passw2 = pass2.getText().toString();
        email = mail.getText().toString();
        if (passw1.equals(passw2)) {
            setContentView(R.layout.profile_registering);
            foto_gallery = findViewById(R.id.profile);

            foto_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
            add("correu", "contrasenya", email, passw1);
        } else {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }

    }

    private void add(String what1, String what2, String solution1, String solution2) {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put(what1, solution1);
        user.put(what2, solution2);

        // Add a new document with a generated ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(register.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show();
                        id = documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    //per retallar l'imatge i posar-la a l'icona
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setInitialCropWindowPaddingRatio(0)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                foto_gallery.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //penja la foto de perfil de l'usuari al servidor
    private void post(Uri imatge) {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profile = mStorageRef.child(nick);
        profile.putFile(imatge)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        perfil=downloadUrl.toString();
                        Toast.makeText(register.this, perfil, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(register.this, R.string.internet, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void acabat(View view) {
        EditText nom = findViewById(R.id.name);
        EditText sobrenom = findViewById(R.id.nick);
        name = nom.getText().toString();
        nick = sobrenom.getText().toString();
        post(resultUri);
        afegeix("nom", "usuari","perfil", name, nick,perfil);
        SharedPreferences prefs = getSharedPreferences("usuari", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", id);
        editor.commit();
        startActivity(new Intent(this, main.class));
    }

    private void afegeix(String what1, String what2,String what3, String solution1, String solution2,String solution3) {
        // [START add_ada_lovelace]
        // Create a new user with a first and last name
        Map<String, Object> usuari = new HashMap<>();
        usuari.put(what1, solution1);
        usuari.put(what2, solution2);
        usuari.put(what3,solution3);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
       // DocumentReference alovelaceDocumentRef = db.collection("users").document(id);
        db.collection("users").document(id)
                .set(usuari, SetOptions.merge());
        // Add a new document with a generated ID

    }
}