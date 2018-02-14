package kz.kbtu.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String[] questions = {"Кто является создателем социальной сети Facebook?",
            "Какой мессенджер является самым популярным в мире?",
            "Какой из предложенных логотипов принадлежит файловому хостингу?",
            "Какой из предложенных логотипов принадлежит компании Suzuki?",
            "Кто является основателем компании Apple?"};
    private int[][] pictures =
            {{R.drawable.answer11, R.drawable.answer12, R.drawable.answer13, R.drawable.answer14},
                    {R.drawable.answer21, R.drawable.answer22, R.drawable.answer23, R.drawable.answer24},
                    {R.drawable.answer31, R.drawable.answer32, R.drawable.answer33, R.drawable.answer34},
                    {R.drawable.answer41, R.drawable.answer42, R.drawable.answer43, R.drawable.answer44},
                    {R.drawable.answer51, R.drawable.answer52, R.drawable.answer53, R.drawable.answer54}};
    private int[] rightAnswers = {R.drawable.answer11, R.drawable.answer23, R.drawable.answer32, R.drawable.answer44, R.drawable.answer53};

    Button nextBtn;
    ImageView image1, image2, image3, image4;
    ArrayList<ImageView> images;
    TextView qNumberText, questionText;
    int cntRight, cntWrong;
    int timeCnt;
    Timer timer;
    int currentQuestion = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = findViewById(R.id.picture1);
        image2 = findViewById(R.id.picture2);
        image3 = findViewById(R.id.picture3);
        image4 = findViewById(R.id.picture4);
        nextBtn = findViewById(R.id.nextBtn);
        questionText = findViewById(R.id.questionText);
        qNumberText = findViewById(R.id.questionNumberText);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(image1);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(image2);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(image3);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(image4);
            }
        });

        images = new ArrayList<>();
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupNextQuestion();
            }
        });
        nextBtn.setEnabled(false);
        setupNextQuestion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupNextQuestion() {
        if(currentQuestion >= questions.length){
            stopTimer();
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("right", cntRight);
            intent.putExtra("wrong", cntWrong);
            intent.putExtra("time", timeCnt);
            startActivity(intent);
            Log.d("check", "start passed");
            currentQuestion = cntRight = cntWrong = timeCnt = 0;
            setupNextQuestion();
            return;
        }
        setImageClickable(true);
        nextBtn.setEnabled(false);
        questionText.setText(questions[currentQuestion]);
        qNumberText.setText("ВОПРОС " + (currentQuestion + 1));
        for(int i = 0; i < pictures[currentQuestion].length; i++){
            Drawable a = getDrawable(pictures[currentQuestion][i]);
            images.get(i).setImageDrawable(a);
            images.get(i).setBackgroundColor(Color.parseColor("#00FFFFFF"));
            if(rightAnswers[currentQuestion] == pictures[currentQuestion][i]){
                images.get(i).setContentDescription("answer");
            }
            else{
                images.get(i).setContentDescription("");
            }
        }
        currentQuestion++;

    }

    private void checkAnswer(ImageView answer) {
        setImageClickable(false);
        nextBtn.setEnabled(true);
        if(answer.getContentDescription() == "answer"){
            answer.setBackgroundColor(Color.parseColor("#46FF97"));
            cntRight++;
        }
        else{
            answer.setBackgroundColor(Color.parseColor("#FF6279"));
            cntWrong++;
        }
    }

    private void setImageClickable(boolean flag){
        image1.setClickable(flag);
        image2.setClickable(flag);
        image3.setClickable(flag);
        image4.setClickable(flag);
    }

    private void startTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeCnt++;
            }
        }, 0, 1000);
    }

    private void stopTimer(){
        timer.cancel();
    }
}
