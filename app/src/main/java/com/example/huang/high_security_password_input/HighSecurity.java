package com.example.huang.high_security_password_input;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huang.high_security_password_input.tools.ExpNode;
import com.example.huang.high_security_password_input.tools.RandomUntil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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

    //formula
    private ExpNode expNode;

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

    public void checkPin(){
        if(cryptPin.size() != 4){
            return;
        }
        if(cryptPin.equals(cryptPin_answer)){
            // pin码正确
            Toast.makeText(getApplicationContext(), "correct pin",
                    Toast.LENGTH_LONG).show();
            cryptPin.clear();
            cryptPin_answer.clear();
            refresh_random();
        }
        else{
            //the pin is not correct
            Toast.makeText(getApplicationContext(), "wrong pin",
                    Toast.LENGTH_LONG).show();
            cryptPin.clear();
            cryptPin_answer.clear();
            refresh_random();
        }
    }

    public void click(View view){
        String txt = ((Button) view).getText().toString();
        if(Pattern.matches("\\d", txt)){
            //react to the number pressing
            cryptPin.add(Integer.parseInt(txt));
            System.out.println("cryptPin" + txt);
            cryptPin_answer.add(expNode.value % modulo);
            System.out.println("cryptPin_answer" + String.valueOf(expNode.value % modulo));
            checkPin();
            refresh_random();
        }
        else if(Pattern.matches("\\D*", txt)){
            //react to the non-number pressing
            switch(view.getId()){
                case R.id.button_delete:
                    if(!cryptPin.isEmpty()){
                        cryptPin.remove(cryptPin.size() - 1);
                        cryptPin_answer.remove(cryptPin_answer.size() - 1);
                        refresh_random();
                    }
                    break;
                case R.id.button_clear:
                    cryptPin.clear();
                    cryptPin_answer.clear();
                    refresh_random();
                    break;
                case R.id.button_refresh:
                    refresh_random();
            }
        }
    }








    protected void refresh_random(){
        //更换 key， exp， modulo
        key = RandomUntil.getNum(1, 5); // TODO
        vibrate(key);
        modulo = RandomUntil.getNum(5, 10);
        expNode = FormulaGen(key, pin_answer.get(cryptPin.size()));
        textViews[0].setText(expNode.maskFormula);
        textViews[1].setText(String.valueOf(modulo));
        changeCircle(cryptPin.size());
    }

    public ExpNode FormulaGen(int currentKey, int currentPin){
        String operators = "+-*";
        int maxNum = 9;
        int numOfOperators = RandomUntil.getNum(2, 5);
        ExpNode expNode = new ExpNode(numOfOperators);
        while(expNode.value <= 0 || expNode.value > 50) {
            // 这里防止结果不好看， 用户不好口算
            expNode.insertX(numOfOperators, currentKey, currentPin, maxNum, operators);
        }
        return expNode;
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
    }


    //change circle to full or empty, and check the size is between 0 and 4
    public boolean changeCircle(int size){
        if(size < 0 || size > 4){
            //illegal size
            return false;
        }
        for(int i = 0; i < size; i++){
            imageCircle[i].setImageResource(R.drawable.circle_added);
        }
        for(int i = size; i < imageCircle.length; i++){
            imageCircle[i].setImageResource(R.drawable.circle);
        }
        return true;
    }
}
