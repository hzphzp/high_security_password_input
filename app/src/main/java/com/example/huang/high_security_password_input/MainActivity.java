package com.example.huang.high_security_password_input;

import android.content.Intent;
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
    }

    private void initView(){
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
            case R.id.buttonBelow:
                toHighSecurity();
                break;
        }
    }

    protected void toSecurityInput(){
        Log.d("debug", "toSecurityInput: ");
        Intent intent = new Intent(this, SecurityInput.class);
        startActivity(intent);
    }

    protected void toNormalInput(){
        Log.d("debug", "toNormalInput");
        Intent intent = new Intent(this, NormalInput.class);
        startActivity(intent);
    }

    protected void toHighSecurity(){
        Log.d("debug", "toHighSecurity");
        Intent intent = new Intent(this, HighSecurity.class);
        startActivity(intent);
    }
}

