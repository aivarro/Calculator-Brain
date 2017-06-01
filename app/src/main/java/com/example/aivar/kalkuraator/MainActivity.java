package com.example.aivar.kalkuraator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static class ComputingReceiver extends BroadcastReceiver {

        private String op1 = "0";
        private String op2 = "0";
        private String sign = "";
        private String unary1 = "";
        private String unary2 = "";

        private String op1Answer = "";
        private String op2Answer = "";
        private String answer = "";

        public ComputingReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                unary1 = intent.getStringExtra("unary1");
                op1 = intent.getStringExtra("op1");
                sign = intent.getStringExtra("sign");
                unary2 = intent.getStringExtra("unary2");
                op2 = intent.getStringExtra("op2");
            } catch (Exception e) {

            }

            validateOps();
            calculateAnswers();

            Intent answerIntent = new Intent("com.example.aivar.calcAnswer");
            Bundle args = new Bundle();

            args.putString("op1", op1Answer);
            args.putString("op2", op2Answer);
            args.putString("answer", answer);

            answerIntent.putExtra("answerBundle", args);

            context.sendBroadcast(answerIntent);
        }

        public void validateOps() {

            if(!unary1.isEmpty() && (!unary1.equals("sin") && !unary1.equals("cos") && !unary1.equals("tan") && !unary1.equals("√") || op1.isEmpty())) {
                unary1 = "";
                op1 = "0";
                op1Answer = "ERROR";
            }

            if(!unary2.isEmpty() && (!unary2.equals("sin") && !unary2.equals("cos") && !unary2.equals("tan") && !unary2.equals("√") || op2.isEmpty())) {
                unary2 = "";
                op2 = "0";
                op2Answer = "ERROR";
            }

            try {
                Double.parseDouble(op1);
            } catch (Exception e) {
                op1 = "0";
            }

            try {
                Double.parseDouble(op2);
            } catch (Exception e) {
                op2 = "0";
            }

            if(op1.isEmpty()) op1 = "0";
            if(op2.isEmpty()) op2 = "0";

        }

        public void calculateAnswers() {

            try {
                if (!unary1.isEmpty()) {
                    switch (unary1) {
                        case "√":
                            op1Answer = String.valueOf(Math.sqrt(Double.parseDouble(op1)));
                            break;
                        case "sin":
                            op1Answer = String.valueOf(Math.sin(Double.parseDouble(op1)));
                            break;
                        case "cos":
                            op1Answer = String.valueOf(Math.cos(Double.parseDouble(op1)));
                            break;
                        case "tan":
                            op1Answer = String.valueOf(Math.tan(Double.parseDouble(op1)));
                            break;
                    }
                } else {
                    if(!op1Answer.equals("ERROR")) op1Answer = "";
                }
            } catch (Exception e) {
                op1Answer = "ERROR";
            }

            try {
                if (!unary2.isEmpty()) {
                    switch (unary2) {
                        case "√":
                            op2Answer = String.valueOf(Math.sqrt(Double.parseDouble(op2)));
                            break;
                        case "sin":
                            op2Answer = String.valueOf(Math.sin(Double.parseDouble(op2)));
                            break;
                        case "cos":
                            op2Answer = String.valueOf(Math.cos(Double.parseDouble(op2)));
                            break;
                        case "tan":
                            op2Answer = String.valueOf(Math.tan(Double.parseDouble(op2)));
                            break;
                    }
                } else {
                    if(!op2Answer.equals("ERROR")) op2Answer = "";
                }
            } catch (Exception e) {
                op2Answer = "ERROR";
            }

            try {
                if (!sign.isEmpty()) {
                    String first = "";
                    String second = "";
                    if(op1Answer.isEmpty()) first = op1; else first = op1Answer;
                    if(op2Answer.isEmpty()) second = op2; else second = op2Answer;

                    switch (sign) {
                        case "+":
                            answer = String.valueOf(Double.valueOf(first) + Double.valueOf(second));
                            break;
                        case "-":
                            answer = String.valueOf(Double.valueOf(first) - Double.valueOf(second));
                            break;
                        case "×":
                            answer = String.valueOf(Double.valueOf(first) * Double.valueOf(second));
                            break;
                        case "/":
                            answer = String.valueOf(Double.valueOf(first) / Double.valueOf(second));
                            break;
                    }
                } else {
                    answer = !op1Answer.isEmpty() ? op1Answer : op1;
                }
            } catch (Exception e) {
                answer = "ERROR";
            }

        }
    }
}
