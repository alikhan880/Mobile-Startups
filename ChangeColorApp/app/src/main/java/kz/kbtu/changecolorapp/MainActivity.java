package kz.kbtu.changecolorapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnChangeColor;
    private View v;
    private Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();
        btnChangeColor = findViewById(R.id.btn_change_color);
        v = findViewById(R.id.root_view);

        v.setBackgroundColor(Color.RED);
        btnChangeColor.setBackgroundColor(Color.BLUE);

        btnChangeColor = findViewById(R.id.btn_change_color);
        v = findViewById(R.id.root_view);

        v.setBackgroundColor(Color.RED);
        btnChangeColor.setBackgroundColor(Color.BLUE);

        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colorBtn = ((ColorDrawable)btnChangeColor.getBackground()).getColor();
                int colorView = ((ColorDrawable)v.getBackground()).getColor();
                v.setBackgroundColor(colorBtn);
                btnChangeColor.setBackgroundColor(colorView);
            }
        });
    }

}


