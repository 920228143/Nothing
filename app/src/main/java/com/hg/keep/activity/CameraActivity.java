package com.hg.keep.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hg.keep.R;

public class CameraActivity extends AppCompatActivity {

    private View surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


    }

    private void setSurface() {
        surface = findViewById(R.id.surface);
        surface.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
