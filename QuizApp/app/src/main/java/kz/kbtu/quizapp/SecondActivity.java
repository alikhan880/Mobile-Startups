package kz.kbtu.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tvRight, tvWrong, tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        int right = intent.getIntExtra("right", -1);
        int wrong = intent.getIntExtra("wrong", -1);
        int time = intent.getIntExtra("time", -1);
        tvRight = findViewById(R.id.tv_right_cnt);
        tvWrong = findViewById(R.id.tv_wrong_cnt);
        tvTime = findViewById(R.id.tv_time_cnt);
        tvRight.setText("Right answers: " + right);
        tvWrong.setText("Wrong answers: " + wrong);
        tvTime.setText("Time elapsed: " + time + " sec");
    }
}
