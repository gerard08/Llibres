package com.que.llibres;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Gerard on 25/12/2017.
 */
public class opening extends AppCompatActivity {
double alpha=1.0;
double alpha2;
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
                ImageView background = (ImageView)findViewById(R.id.back);
                background.setAlpha(f);
            }

            public void onFinish() {
                setContentView(R.layout.choose);
                Button button = (Button)findViewById(R.id.login);
                Button button2 = (Button)findViewById(R.id.register);
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
                Button button = (Button)findViewById(R.id.login);
                Button button2 = (Button)findViewById(R.id.register);
                button.setAlpha(f);
                button2.setAlpha(f);
            }

            public void onFinish() {

            }
        }.start();
    }
}
