package com.example.rxlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button leftButton, rightButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        rightButton = findViewById(R.id.rightButton);
        leftButton = findViewById(R.id.leftButton);

    }
    public void log(Object o){
        textView.setText(textView.getText().toString() + "\n"+ o);
    }
}
