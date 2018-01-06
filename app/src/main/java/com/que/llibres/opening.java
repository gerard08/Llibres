package com.que.llibres;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

public class opening extends AppCompatActivity {
double alpha=1.0;
double alpha2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        anima();
    }
    //Això és l'animació de la pantalla d'inici
    private void anima(){
        new CountDownTimer(3000, 1) {
            public void onTick(long millisUntilFinished) {
                alpha=alpha-0.01;
                double d = alpha;
                float f = (float)d;
                ImageView background = findViewById(R.id.back);
                background.setAlpha(f);
            }

            public void onFinish() {
                setContentView(R.layout.choose);
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
                Button button =  findViewById(R.id.login);
                Button button2 = findViewById(R.id.register);
                button.setAlpha(0);
                button2.setAlpha(0);
                anima2();
            }
        }.start();
    }
    //Això és l'animació de la pantalla de selecció ( a l'iniciar-se)
    private void anima2(){
        new CountDownTimer(1000, 1) {
            public void onTick(long millisUntilFinished) {
                alpha2=alpha2+0.04;
                double d = alpha2;
                float f = (float)d;
                Button button =  findViewById(R.id.login);
                Button button2 = findViewById(R.id.register);
                button.setAlpha(f);
                button2.setAlpha(f);
            }

            public void onFinish() {

            }
        }.start();
    }

    public void registra(View view){
        startActivity(new Intent(this, register.class));
    }
    public void login(View view){startActivity(new Intent(this, login.class));}
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, main.class));
            finish();
        } else {
          //  Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}