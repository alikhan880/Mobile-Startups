package kz.kbtu.rotatingcarsapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private Runnable runnable;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();



        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                rotateDown();
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, 3000);
    }

    private void update() {
        handler.post(runnable);
    }

    private void bindViews() {
        iv1 = findViewById(R.id.iv_1);
        iv2 = findViewById(R.id.iv_2);
        iv3 = findViewById(R.id.iv_3);
        iv4 = findViewById(R.id.iv_4);

    }

    private void rotateDown() {
        Drawable pic1 = iv1.getDrawable();
        Drawable pic2= iv2.getDrawable();
        Drawable pic3 = iv3.getDrawable();
        Drawable pic4 = iv4.getDrawable();

        iv1.setImageDrawable(pic4);
        iv2.setImageDrawable(pic1);
        iv3.setImageDrawable(pic2);
        iv4.setImageDrawable(pic3);

    }




}
