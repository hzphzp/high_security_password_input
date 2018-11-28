package com.example.huang.high_security_password_input;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighSecurity extends AppCompatActivity {
    //the number keyboard
    private int[] bIdNum = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
            R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    private Button[] buttonNum = new Button[bIdNum.length];

    //the function keyboard
    private int[] bIdFunc = {R.id.button_delete, R.id.button_clear};
    private Button[] buttonFunc = new Button[bIdFunc.length];

    //the circle image
    private int[] iId = {R.id.imageview_circle1, R.id.imageview_circle2, R.id.imageview_circle3,
            R.id.imageview_circle4};
    private ImageView[] imageCircle = new ImageView[iId.length];

    //the text view
    private int[] tId = {R.id.textView_formula, R.id.textView_modulo};
    private TextView[] textViews = new TextView[tId.length];

    //random
    private int key;
    private int modulo;

    //calculate formula
    private String formula;

    //pin
    private List<Integer> cryptPin;
    private List<Integer> cryptPin_answer;
    private final List<Integer> pin_answer = Arrays.asList(2, 4, 1, 8);

    public void vibrate(int t){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        List<Long> timings = Arrays.asList(0L, 500L, 500L, 500L, 500L, 500L, 500L, 500L, 500L, 500L, 500L);
        long[] time = new long[2 * t + 1];
        for(int i = 0; i < 2 * t + 1; i++){
            time[i] = timings.get(i);
        }
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(time, -1);
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            v.vibrate(time, -1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_security);
        init();
        refresh_random();
    }

    private void init(){
        //find the entered symbols the circles, if the pin number is pressed, than the circles will change
        for(int i = 0; i < iId.length; i++){
            imageCircle[i] = findViewById(iId[i]);
        }

        //find the textViews of the layout
        for(int i =0; i < tId.length; i++){
            textViews[i] = findViewById(tId[i]);
        }

        //find the pressing of number
        for(int i = 0; i < bIdNum.length; i++){
            buttonNum[i] = findViewById(bIdNum[i]);
        }


        //find the pressings of the functions
        for(int i = 0; i < bIdFunc.length; i++){
            buttonFunc[i] = findViewById(bIdFunc[i]);
        }

        //init the pin
        cryptPin = new ArrayList<>();
        cryptPin_answer = new ArrayList<>();

        //formula
        formula = "";
    }

    protected void refresh_random(){
        key = RandomUntil.getNum(1, 6);
        modulo = RandomUntil.getNum(9, 20);
        formula = FormulaGen();
        vibrate(key);
        textViews[0].setText(formula);
        textViews[1].setText(String.valueOf(modulo));
    }

    public String FormulaGen(){
        String operators = "+-*";
        int maxNum = 9;
        int numOfOperators = RandomUntil.getNum(3, 7);
        ExpNode expNode = new ExpNode(numOfOperators);
        expNode.fillNode(maxNum, operators);
        Log.d("debug", expNode.formula);
        Log.d("debug", String.valueOf(expNode.value));
        return expNode.formula;
    }
}
