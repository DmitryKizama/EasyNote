package com.kizema.johnsnow.colornotes;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.kizema.johnsnow.colornotes.helper.ColorHelper;
import com.kizema.johnsnow.colornotes.helper.UIHelper;
import com.kizema.johnsnow.colornotes.helper.UserColorHelper;

public class App extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        UIHelper.init(appContext);
        ColorHelper.init(appContext);
        ActiveAndroid.initialize(this);

        UserColorHelper.get().init(appContext);
    }

    public static synchronized Context getAppContext(){
        return appContext;
    }
}
