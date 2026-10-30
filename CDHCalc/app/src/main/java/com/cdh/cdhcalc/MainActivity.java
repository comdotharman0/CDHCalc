package com.cdh.cdhcalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView tvDisplay;
    private double firstOperand = 0;
    private String pendingOperator = "";
    private boolean isOperatorClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        int[] numIds = {
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numListener = v -> {
            Button b = (Button) v;
            String text = b.getText().toString();
            if (isOperatorClicked || tvDisplay.getText().toString().equals("0")) {
                tvDisplay.setText(text);
                isOperatorClicked = false;
            } else {
                tvDisplay.append(text);
            }
        };

        for (int id : numIds) {
            findViewById(id).setOnClickListener(numListener);
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> setOperator("/"));

        findViewById(R.id.btnEq).setOnClickListener(v -> calculate());
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            firstOperand = 0;
            pendingOperator = "";
            tvDisplay.setText("0");
            isOperatorClicked = false;
        });
    }

    private void setOperator(String op) {
        firstOperand = Double.parseDouble(tvDisplay.getText().toString());
        pendingOperator = op;
        isOperatorClicked = true;
    }

    private void calculate() {
        if (pendingOperator.isEmpty()) return;
        double secondOperand = Double.parseDouble(tvDisplay.getText().toString());
        double result = 0;

        switch (pendingOperator) {
            case "+": result = firstOperand + secondOperand; break;
            case "-": result = firstOperand - secondOperand; break;
            case "*": result = firstOperand * secondOperand; break;
            case "/": 
                if (secondOperand != 0) {
                    result = firstOperand / secondOperand;
                } else {
                    tvDisplay.setText("Err");
                    pendingOperator = "";
                    return;
                }
                break;
        }

        if (result == (long) result) {
            tvDisplay.setText(String.format("%d", (long) result));
        } else {
            tvDisplay.setText(String.valueOf(result));
        }
        pendingOperator = "";
        isOperatorClicked = true;
    }
}
