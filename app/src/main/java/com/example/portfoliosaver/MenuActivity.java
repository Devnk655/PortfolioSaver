package com.example.portfoliosaver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    Button btnservices,btnskills;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnservices = findViewById(R.id.btnmyservices);
        btnskills=findViewById(R.id.btnmyskills);
        btnservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,MyServices.class);
                startActivity(intent);
            }
        });
        btnskills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MenuActivity.this,SkillsActivity.class);
                startActivity(intent1);
            }
        });

    }
}