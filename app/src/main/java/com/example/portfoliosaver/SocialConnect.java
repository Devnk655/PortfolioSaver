package com.example.portfoliosaver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SocialConnect extends AppCompatActivity {
Button btnlinkedin,btngithub,btninstagram;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_connect);
        btnlinkedin = findViewById(R.id.btnname);
        btngithub = findViewById(R.id.btnlocation);
        btninstagram = findViewById(R.id.btnemail);


    btnlinkedin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            gotoUrl("https://www.linkedin.com/in/deven-umesh-khangar-07957a211/");
        }
    });
        btngithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://github.com/Devnk655/Video-Player");
            }
        });
        btninstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.instagram.com/deven_khengar/");
            }
        });
    }
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}