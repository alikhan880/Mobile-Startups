package kz.kbtu.calculatorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.editText)
    TextView editText;
    @BindView(R.id.buttonAC)
    Button buttonAC;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.buttonDiv)
    Button buttonDiv;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.buttonMulti)
    Button buttonMulti;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.buttonMinus)
    Button buttonMinus;
    @BindView(R.id.button0)
    Button button0;
    @BindView(R.id.buttonEqual)
    Button buttonEqual;
    @BindView(R.id.buttonPlus)
    Button buttonPlus;
    @BindView(R.id.buttonPercent)
    Button buttonPercent;
    @BindView(R.id.buttonDel)
    Button buttonDel;

    private String expressionString = "";
    private String tempStringHolder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    private void cleanAll() {
        editText.setText("");
        tvResult.setText("");
        expressionString = "";
        tempStringHolder = "";
    }


    private void addToExpression(String s) {
        tempStringHolder += s;
        s = tempStringHolder;
        tempStringHolder = "";
        if (s.startsWith("-") && !((s.contains("-") && TextUtils.split(s, "-").length > 2) || s.contains("+") || s.contains("*") || s.contains("/") || s.contains("%"))) {
            tempStringHolder += s;
            display(tempStringHolder);
        } else if (s.contains("+") || s.contains("-") || s.contains("*") || s.contains("/") || s.contains("%")) {
            tempStringHolder += s;
            expressionString += tempStringHolder;
            tempStringHolder = "";
            display(expressionString);
        } else {
            tempStringHolder += s;
            display(tempStringHolder);
        }
    }

    private void display(String s) {
        if (s.length() > 8) {
            displayOnExpressionField(s);
        }
        if (!s.startsWith("-") && !(s.contains("-") || s.contains("*") || s.contains("/") || s.contains("+") || s.contains("%"))) {
            displayOnResultField(s);
        } else if (!s.startsWith("-") && (s.contains("-") || s.contains("*") || s.contains("/") || s.contains("+") || s.contains("%"))) {
            displayOnExpressionField(s);
            editText.setText("");
        } else if (s.startsWith("-") && ((s.contains("-") && TextUtils.split(s, "-").length > 2) || s.contains("*") || s.contains("/") || s.contains("+") || s.contains("%"))) {
            displayOnExpressionField(s);
            editText.setText("");
        } else {
            displayOnResultField(s);
        }

    }

    private void displayOnResultField(String s) {
        String temp = "";
        int x = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            temp += s.charAt(i);
            x++;
            if (x == 3) {
                temp += " ";
                x = 0;
            }

        }
        temp = new StringBuilder(temp).reverse().toString();
        editText.setText(temp);
    }

    private void displayOnExpressionField(String s) {
        tvResult.setText(s);
    }

    private void evaluateExpression() {
        if (tempStringHolder.length() > 0) {
            expressionString += tempStringHolder;
            display(expressionString);
            tempStringHolder = "";
        }
        if (expressionString.contains("%")) {
            /*


                Works only with 2 variables

             */
            String[] vars = expressionString.split("%");
            if (vars.length < 2) {
                editText.setText("Invalid number of variables");
                return;
            }
            int leftVar = Integer.valueOf(vars[0]);
            int rightVar = Integer.valueOf(vars[1]);
            int result = (int) ((leftVar * 1f * rightVar * 1f) / 100f);
//            Log.d("vars", leftVar + " " + rightVar);
//            Log.d("vars", result + "");
            display(String.valueOf(result));
            return;
        }
        try {
            Expression e = new ExpressionBuilder(expressionString).build();
            int result = (int) (e.evaluate());
            editText.setText(String.valueOf(result));
        } catch (IllegalArgumentException exception) {
            editText.setText(exception.getMessage());
        }
    }

    @OnClick({R.id.buttonDel, R.id.buttonPercent, R.id.buttonAC, R.id.button7, R.id.button8, R.id.button9, R.id.buttonDiv, R.id.button4, R.id.button5, R.id.button6, R.id.buttonMulti, R.id.button1, R.id.button2, R.id.button3, R.id.buttonMinus, R.id.button0, R.id.buttonEqual, R.id.buttonPlus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buttonAC:
                cleanAll();
                break;
            case R.id.button7:
                addToExpression(button7.getText().toString());
                break;
            case R.id.button8:
                addToExpression(button8.getText().toString());
                break;
            case R.id.button9:
                addToExpression(button9.getText().toString());
                break;
            case R.id.buttonDiv:
                addToExpression(buttonDiv.getText().toString());
                break;
            case R.id.button4:
                addToExpression(button4.getText().toString());
                break;
            case R.id.button5:
                addToExpression(button5.getText().toString());
                break;
            case R.id.button6:
                addToExpression(button6.getText().toString());
                break;
            case R.id.buttonMulti:
                addToExpression(buttonMulti.getText().toString());
                break;
            case R.id.button1:
                addToExpression(button1.getText().toString());
                break;
            case R.id.button2:
                addToExpression(button2.getText().toString());
                break;
            case R.id.button3:
                addToExpression(button3.getText().toString());
                break;
            case R.id.buttonMinus:
                addToExpression(buttonMinus.getText().toString());
                break;
            case R.id.button0:
                addToExpression(button0.getText().toString());
                break;
            case R.id.buttonEqual:
                evaluateExpression();
                break;
            case R.id.buttonPlus:
                addToExpression(buttonPlus.getText().toString());
                break;
            case R.id.buttonPercent:
                addToExpression(buttonPercent.getText().toString());
                break;
            case R.id.buttonDel:
                deleteSymbol();
                break;
        }
    }

    private void deleteSymbol() {
        if(tempStringHolder.length() > 0){
            tempStringHolder = tempStringHolder.substring(0, tempStringHolder.length() - 1);
            displayOnResultField(tempStringHolder);
        }
        else if(expressionString.length() > 0){
            expressionString = expressionString.substring(0, expressionString.length() - 1);
            displayOnExpressionField(expressionString);
            editText.setText("");
        }
    }

}
