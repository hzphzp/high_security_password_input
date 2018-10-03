package com.example.huang.high_security_password_input;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalInput extends AppCompatActivity {

    private final String TAG = "NormalInput";
    ImageView i1, i2, i3, i4;
    Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    Button button_0, button_delete, button_confirm;
    List<Integer> pin;
    List<Integer> pin_answer = Arrays.asList(2, 4, 1, 8);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_input);
        init();
    }

    //the function react to the pressing the number buttons
    public void press(int m){
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
    }

    //the act of the pressing of function button
    public void press(String mes){
        switch(mes){
            case "delete":
                if(!pin.isEmpty()){
                    pin.remove(pin.size() - 1);
                    changeCircle(pin.size());
                }
                break;
            case "clear":
                pin.clear();
                changeCircle(pin.size());
                break;
        }

    }

    //to check the pin is or is not correct
    public boolean checkPin(List<Integer> pin){
        return pin.equals(pin_answer);
    }

    //change circle to full or empty, and check the size is between 0 and 4
    public boolean changeCircle(int size){
        if(size < 0 || size > 4){
            //illegal size
            return false;
        }
        switch (size){
            case 4:
                i1.setImageResource(R.drawable.circle_added);
                i2.setImageResource(R.drawable.circle_added);
                i3.setImageResource(R.drawable.circle_added);
                i4.setImageResource(R.drawable.circle_added);
                break;
            case 3:
                i1.setImageResource(R.drawable.circle_added);
                i2.setImageResource(R.drawable.circle_added);
                i3.setImageResource(R.drawable.circle_added);
                i4.setImageResource(R.drawable.circle);
                break;
            case 2:
                i1.setImageResource(R.drawable.circle_added);
                i2.setImageResource(R.drawable.circle_added);
                i3.setImageResource(R.drawable.circle);
                i4.setImageResource(R.drawable.circle);
                break;
            case 1:
                i1.setImageResource(R.drawable.circle_added);
                i2.setImageResource(R.drawable.circle);
                i3.setImageResource(R.drawable.circle);
                i4.setImageResource(R.drawable.circle);
                break;
            case 0:
                i1.setImageResource(R.drawable.circle);
                i2.setImageResource(R.drawable.circle);
                i3.setImageResource(R.drawable.circle);
                i4.setImageResource(R.drawable.circle);
                break;
        }
        return true;
    }

    public void click(View view){
        switch(view.getId()){
            case R.id.button_0:
                press(0);
                break;
            case R.id.button_1:
                press(1);
                break;
            case R.id.button_2:
                press(2);
                break;
            case R.id.button_3:
                press(3);
                break;
            case R.id.button_4:
                press(4);
                break;
            case R.id.button_5:
                press(5);
                break;
            case R.id.button_6:
                press(6);
                break;
            case R.id.button_7:
                press(7);
                break;
            case R.id.button_8:
                press(8);
                break;
            case R.id.button_9:
                press(9);
                break;
            case R.id.button_delete:
                press("delete");
                break;
            case R.id.button_clear:
                press("clear");
                break;
        }
    }

    private void init(){
        //find the entered symbols the circles, if the pin number is pressed, than the circles will change
        i1 = findViewById(R.id.imageview_circle1);
        i2 = findViewById(R.id.imageview_circle2);
        i3 = findViewById(R.id.imageview_circle3);
        i4 = findViewById(R.id.imageview_circle4);

        //find the presses
        button_0 = findViewById(R.id.button_0);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        button_5 = findViewById(R.id.button_5);
        button_6 = findViewById(R.id.button_6);
        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button_8);
        button_9 = findViewById(R.id.button_9);
        button_delete = findViewById(R.id.button_delete);
        button_confirm = findViewById(R.id.button_clear);

        //init the pin
        pin = new ArrayList<>();
    }
}
