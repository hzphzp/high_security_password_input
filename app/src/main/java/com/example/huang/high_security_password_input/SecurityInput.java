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


import com.example.huang.high_security_password_input.tools.Calculator;
import com.example.huang.high_security_password_input.tools.RandomUntil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SecurityInput extends AppCompatActivity {
    //the number keyboard
    private int[] bIdNum = {R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4,
                    R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    private Button[] buttonNum = new Button[bIdNum.length];

    //the operator keyboard
    private int[] bIdOperator = {R.id.button_multiple, R.id.button_subtract, R.id.button_add};
    private Button[] buttonOperator = new Button[bIdOperator.length];

    //the function keyboard
    private int[] bIdFunc = {R.id.button_delete, R.id.button_clear, R.id.button_C, R.id.button_confirm};
    private Button[] buttonFunc = new Button[bIdFunc.length];

    //the circle image
    private int[] iId = {R.id.imageview_circle1, R.id.imageview_circle2, R.id.imageview_circle3,
                        R.id.imageview_circle4};
    private ImageView[] imageCircle = new ImageView[iId.length];

    //the text view
    private int[] tId = {R.id.textView_init_compute, R.id.textView_modulo};
    private TextView[] textViews = new TextView[tId.length];

    //random
    private int key;
    private int init_operator;
    private int init_op_num;
    private int modulo;

    //calculate formula
    private String formula;

    //pin
    private List<Integer> pin;
    private final List<Integer> pin_answer = Arrays.asList(2, 4, 1, 8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_input);
        init();
        refresh_random();
    }

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

    public void click(View view){
        String txt = ((Button) view).getText().toString();
        if(Pattern.matches("\\d", txt)){
            //react to the number pressing
            formula += txt;
        }
        else if(Pattern.matches("\\D*", txt)){
            //react to the non-number pressing
            switch(view.getId()){
                case R.id.button_multiple: formula += "*"; break;
                case R.id.button_subtract: formula += "-"; break;
                case R.id.button_add: formula += "+"; break;
                case R.id.button_C: formula = Integer.toString(key); break;
                case R.id.button_refresh: refresh_random();break;
                case R.id.button_delete:
                    formula = "";
                    if(!pin.isEmpty()){
                        pin.remove(pin.size() - 1);
                        changeCircle(pin.size());
                    }
                    break;
                case R.id.button_clear:
                    formula = Integer.toString(key);
                    pin.clear();
                    changeCircle(pin.size());
                    break;
                case R.id.button_confirm:
                    //calculate
                    try{
                        int result = Calculator.calc(formula, modulo);
                        addPin(result);
                    }catch (Exception e){
                        //the formula is illegal, inform the user that the system has empty the formula
                        Toast.makeText(getApplicationContext(), "wrong formula, enter again",
                                Toast.LENGTH_LONG).show();
                        formula = String.valueOf(key);
                    }
            }
        }

    }

    private void addPin(int m){
        pin.add(m);
        if(changeCircle(pin.size())){
            //if the pressing is available
            if(pin.size() == 4) {
                //if the pressing is the last number
                if (checkPin(pin)) {
                    //the pin is correct
                    Toast.makeText(getApplicationContext(), "correct pin",
                            Toast.LENGTH_LONG).show();
                } else {
                    //the pin is not correct
                    Toast.makeText(getApplicationContext(), "wrong pin",
                            Toast.LENGTH_LONG).show();
                }
            }
            //else add m to pin (already done)
            //so do nothing
        }
        else{
            //the size of pin is not legal
            if (pin.size() > 4){
                for (int i = 4; i < pin.size(); i++){
                    pin.remove(i);
                }
            }
        }
        System.out.println( "the number added to the pin is " + String.valueOf(m));
        refresh_random();
    }

    //to check the pin is or is not correct
    public boolean checkPin(List<Integer> pin){
        return pin.equals(pin_answer);
    }

    private void refresh_random(){
        String[] s = {"key X ", "key + "};
        key = RandomUntil.getNum(1, 6);
        init_op_num = RandomUntil.getNum(1, 20);
        modulo = RandomUntil.getNum(9, 20);
        init_operator = RandomUntil.getNum(2);
        textViews[0].setText("(" + s[init_operator] + String.valueOf(init_op_num) + ")");
        vibrate(key);
        textViews[1].setText(String.valueOf(modulo));
        switch(init_operator){
            case 0: key = key * init_op_num; break;
            case 1: key = key + init_op_num; break;
        }
        formula = Integer.toString(key);
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

        //find the pressings of operators
        for(int i = 0; i < bIdOperator.length; i++){
            buttonOperator[i] = findViewById(bIdOperator[i]);
        }

        //find the pressings of the functions
        for(int i = 0; i < bIdFunc.length; i++){
            buttonFunc[i] = findViewById(bIdFunc[i]);
        }

        //init the pin
        pin = new ArrayList<>();

        //formula
        formula = "";
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

