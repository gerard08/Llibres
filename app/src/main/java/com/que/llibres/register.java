package com.que.llibres;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
String passw1;
String passw2;
String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        EditText mail=(EditText)findViewById(R.id.mail);
        EditText pass1=(EditText)findViewById(R.id.pass1);
        EditText pass2=(EditText)findViewById(R.id.pass2);
        passw1=pass1.getText().toString();
        passw2=pass2.getText().toString();
    }
    public void next(View view){

        if( passw1.equals(passw2)){
            setContentView(R.layout.profile_registering);
            add("nom","cognom","gerard","martínez");
        }
        else{
            Toast.makeText(this, "oops", Toast.LENGTH_SHORT).show();
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

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        // [END add_ada_lovelace]
    }
}