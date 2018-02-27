package com.que.llibres;


import android.graphics.Camera;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

@Override
public class scan extends AppCompatActivity implements SurfaceHolder.Callback{
    Camera camera;
    SurfaceView preview;
    SurfaceHolder surfaceHolder;
    boolean camCondition=false;
    Button cap;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam);
        getWindow().setFormat(PixelFormat.UNKNOWN);
        preview=findViewById(R.id.cam);
        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        cap=findViewById(R.id.captura);
    }
}