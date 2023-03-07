package com.example.portfoliosaver;

import android.app.Application;

import com.parse.Parse;


public class App extends Application {
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2Jkb1nWTWcBYFD7s4rDuO4RZWeNUYHC91cfxq9AZ")
                // if defined
                .clientKey("paTpQczZ0xN718pWW2xSrf8ou0oRA4ySAnZpBBzP")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}
