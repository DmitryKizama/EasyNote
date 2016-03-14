package com.kizema.johnsnow.colornotes;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.kizema.johnsnow.colornotes.helper.UIHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UIHelper.init(getApplicationContext());
        ActiveAndroid.initialize(this);
    }
}
