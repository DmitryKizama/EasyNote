package com.kizema.johnsnow.colornotes;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.kizema.johnsnow.colornotes.helper.UIhelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UIhelper.init(getApplicationContext());
        ActiveAndroid.initialize(this);
    }
}
