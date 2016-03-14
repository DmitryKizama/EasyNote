package com.kizema.johnsnow.colornotes;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.kizema.johnsnow.colornotes.helper.ColorHelper;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.helper.UserColorHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UIHelper.init(getApplicationContext());
        ColorHelper.init(getApplicationContext());
        ActiveAndroid.initialize(this);

        UserColorHelper.get().init(getApplicationContext());
    }
}
