package com.que.llibres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class main extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        verify(currentUser);
        //setContentView(R.layout.main);
      }

      public void logout(View view){
          FirebaseAuth.getInstance().signOut();
          mAuth = FirebaseAuth.getInstance();
          FirebaseUser currentUser = mAuth.getCurrentUser();
          updateUI(currentUser);
      }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        } else {
            startActivity(new Intent(this, opening.class));

        }
    }
    private void verify(FirebaseUser user){
        if (user.isEmailVerified()) {
            setContentView(R.layout.principal);
        }
        else{
            setContentView(R.layout.verify);
        }
    }
}
