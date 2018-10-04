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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class SecurityInput extends AppCompatActivity {
    private Vibrator v;
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
    private int[] tId = {R.id.textView_init_compute, R.id.textView_random_key, R.id.textView_modulo};
    private TextView[] textViews = new TextView[tId.length];

    //random
    private int key;
    private int init_operator;
    private int init_op_num;
    private int modula;

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
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(500);
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
                    formula = "";
                    pin.clear();
                    changeCircle(pin.size());
                    break;
                case R.id.button_confirm:
                    //calculate TODO:
            }
        }

    }

    private void refresh_random(){
        String[] s = {"key X ", "key - ", "key + "};
        key = RandomUntil.getNum(5);
        init_op_num = RandomUntil.getNum(1, 20);
        modula = RandomUntil.getNum(10);
        init_operator = RandomUntil.getNum(3);
        textViews[0].setText("(" + s[init_operator] + String.valueOf(init_op_num) + ")");
        textViews[1].setText(String.valueOf(key));
        textViews[2].setText(String.valueOf(modula));
        switch(init_operator){
            case 0: key = key * init_op_num; break;
            case 1: key = key - init_op_num; break;
            case 2: key = key + init_op_num; break;
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

