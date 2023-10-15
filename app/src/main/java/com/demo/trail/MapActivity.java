package com.demo.trail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapActivity extends AppCompatActivity {

    private Matrix originalMatrix;
    private ImageView imageViewMap;
    private LinearLayout infoLayout;
    private TextView infoTextView;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private float scaleFactor = 1.0f;
    private float lastTouchX;
    private float lastTouchY;

    private String name;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);
        initView();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("name")) {
            name = intent.getStringExtra("name");
        }

        originalMatrix = new Matrix();
        originalMatrix.set(imageViewMap.getImageMatrix());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        gestureDetector = new GestureDetector(this, new GestureListener());


        imageViewMap.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                infoLayout.setVisibility(View.VISIBLE);
                                                infoTextView.setText("Текст, который нужно отобразить");
                                            }
                                        });


        imageViewMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                if (isInsideHouse(x, y)) {
                    infoLayout.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }
    private boolean isInsideHouse(float x, float y) {
        float houseLeft = imageViewMap.getX();
        float houseTop = imageViewMap.getY();

        int houseWidth = imageViewMap.getWidth();
        int houseHeight = imageViewMap.getHeight();

        if (x >= houseLeft && x <= houseLeft + houseWidth && y >= houseTop && y <= houseTop + houseHeight) {
            return true;
        } else {
            return false;
        }
    }


        @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getX() - lastTouchX;
                float deltaY = event.getY() - lastTouchY;
                imageViewMap.setTranslationX(imageViewMap.getTranslationX() + deltaX);
                imageViewMap.setTranslationY(imageViewMap.getTranslationY() + deltaY);
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                break;
        }
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 10.0f));
            imageViewMap.setScaleX(scaleFactor);
            imageViewMap.setScaleY(scaleFactor);
            return true;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            imageViewMap.setImageMatrix(originalMatrix);
            return true;
        }
    }
    private void initView(){
        imageViewMap = findViewById(R.id.imageViewMap);
        infoLayout = findViewById(R.id.infoLayout);
        infoTextView = findViewById(R.id.infoTextView);
    }
}

