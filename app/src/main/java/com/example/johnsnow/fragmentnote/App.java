package com.example.johnsnow.fragmentnote;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.example.johnsnow.fragmentnote.helper.UIhelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UIhelper.init(getApplicationContext());
        ActiveAndroid.initialize(this);
    }
}
