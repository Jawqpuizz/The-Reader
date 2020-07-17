package com.gurusvasti.thereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class showResultActivity extends AppCompatActivity {

    EditText btnText;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        btnText = (EditText)findViewById(R.id.edit_text);
        btnSave = (Button) findViewById(R.id.btn_save);


        Intent intent = getIntent();
        String st = intent.getStringExtra("result");
        btnText.setText(st);
    }
}
