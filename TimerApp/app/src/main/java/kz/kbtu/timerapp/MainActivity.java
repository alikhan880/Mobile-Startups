package kz.kbtu.timerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvTimer;
    private Button btnStart;
    private Button btnReset;
    private boolean isStarted;
    private Timer timer;
    private int timerCount = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        tvTimer = findViewById(R.id.tv_timer);
        btnStart = findViewById(R.id.btn_start);
        btnReset = findViewById(R.id.btn_reset);
        btnStart.setOnClickListener(this);
        btnReset.setOnClickListener(this);
    }

    private void start(){
        if(isStarted){
            isStarted = false;
            pause();
        }
        else{
            isStarted = true;
            switchButtonLabel();
            startTimer();
        }
    }

    private void pause() {
        timer.cancel();
        switchButtonLabel();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(timerCount <= 0){
                            stop();
                        }
                        tvTimer.setText(String.valueOf(timerCount));
                        timerCount--;
                    }
                });

            }
        }, 0, 1000);

    }

    private void stop() {
        timer.cancel();
        btnStart.setVisibility(View.GONE);
    }

    private void switchButtonLabel(){
        if(isStarted){
            btnStart.setText("Pause");
        }
        else{
            btnStart.setText("Start");
        }
    }

    private void resetTimer(){
        timer.cancel();
        isStarted = false;
        switchButtonLabel();
        timerCount = 60;
        tvTimer.setText("60");
    }

    private void reset(){
        if(btnStart.getVisibility() == View.GONE) btnStart.setVisibility(View.VISIBLE);
        isStarted = false;
        switchButtonLabel();
        resetTimer();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start:
                start();
                break;
            case R.id.btn_reset:
                reset();
                break;
        }
    }
}
