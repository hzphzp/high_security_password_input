package com.example.huang.high_security_password_input;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonLeft;
    Button buttonRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        int key = RandomUntil.getNum(5);
        textView.setText(String.valueOf(key));
    }

    private void initView(){
        textView = findViewById(R.id.textView);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonLeft:
                toSecurityInput();
                break;
            case R.id.buttonRight:
                toNormalInput();
                break;
        }
    }

    protected void toSecurityInput(){
        Log.e("debug", "toSecurityInput: ");

    }

    protected void toNormalInput(){
        Log.e("debug", "toNormalInput");
    }
}

