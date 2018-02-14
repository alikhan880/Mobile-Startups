package kz.kbtu.taroapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private TextView tvProfText;
    private TextView tvTag;
    private ImageView ivProf;
    private String[] profs = {"Архитектор", "Спасатель", "Генетик", "Пожарный", "Космонавт", "Хирург", "Врач", "Частный детектив", "Следователь", "Астронавт", "Системный администратор"};
    private int[] photos = {R.drawable.architect, R.drawable.rescue, R.drawable.genetic, R.drawable.fireman, R.drawable.kosmonavt, R.drawable.hirurg, R.drawable.vrach, R.drawable.detektiv, R.drawable.sledovatel, R.drawable.astrounaut, R.drawable.sysadmin};
    private String[] cars = {"Toyota", "Mercedes-Benz", "BMW", "Hyundai", "Audi", "Lexus", "Nissan", "Infiniti", "Porsche", "Bentley", "Rolls-Royce"};
    private String[] salaries = {"10000", "20000", "30000", "40000", "1000000", "2000000", "3000000", "800000"};
    private Random random;
    private int choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bindViews();
        random = new Random();
        Intent intent = getIntent();
        if(intent != null){
            choice = intent.getIntExtra("choice", -1);
        }
        Log.d("choice", choice + "");

        if(choice == 1){
            showProfession();
        }
        else if(choice == 2){
            showCar();
        }
        else if(choice == 3){
            showSalary();
        }
    }

    private void showSalary() {
        tvTag.setText("Моя будущая зарплата");
        int idx = random.nextInt(salaries.length);
        tvProfText.setText(salaries[idx]);
    }

    private void showCar() {
        tvTag.setText("Моя будущая машина");
        int idx = random.nextInt(cars.length);
        tvProfText.setText(cars[idx]);
    }

    private void showProfession() {
        tvTag.setText("Моя будущая профессия");
        ivProf.setVisibility(View.VISIBLE);
        int idx = random.nextInt(profs.length);
        tvProfText.setText(profs[idx]);
        ivProf.setImageResource(photos[idx]);
    }


    private void bindViews() {
        tvProfText = findViewById(R.id.tv_prof_text);
        ivProf = findViewById(R.id.iv_prof);
        tvTag = findViewById(R.id.tv_tag);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        else
            return false;
    }
}
