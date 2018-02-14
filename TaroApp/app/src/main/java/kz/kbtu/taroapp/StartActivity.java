package kz.kbtu.taroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvProf;
    private TextView tvCar;
    private TextView tvMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bindViews();
    }

    private void bindViews() {
        tvProf = findViewById(R.id.tv_choiceProf);
        tvMoney = findViewById(R.id.tv_choiceMoney);
        tvCar = findViewById(R.id.tv_choiceCar);
        tvProf.setOnClickListener(this);
        tvMoney.setOnClickListener(this);
        tvCar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (view.getId()){
            case R.id.tv_choiceProf:
                intent.putExtra("choice", 1);
                startActivity(intent);
                break;
            case R.id.tv_choiceCar:
                intent.putExtra("choice", 2);
                startActivity(intent);
                break;
            case R.id.tv_choiceMoney:
                intent.putExtra("choice", 3);
                startActivity(intent);
                break;
        }
    }
}
