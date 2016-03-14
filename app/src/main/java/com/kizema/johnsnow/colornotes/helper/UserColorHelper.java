package com.kizema.johnsnow.colornotes.helper;

import android.content.Context;

import com.kizema.johnsnow.colornotes.R;
import com.kizema.johnsnow.colornotes.model.UserColor;

import java.util.List;

/**
 * Created by somename on 14.03.2016.
 */
public class UserColorHelper {


    private static UserColorHelper inst;
    private UserColorHelper(){}

    public synchronized static UserColorHelper get(){
        if (inst == null){
            inst = new UserColorHelper();
        }

        return inst;
    }

    public void init(Context context){
        List<UserColor> l = UserColor.getAll();
        if (l == null || l.size() == 0){
            //prepopulate UserColorTable

            int[] rainbow = context.getResources().getIntArray(R.array.rainbow);
            for (int i = 0; i < rainbow.length; i++) {
                UserColor userColor = new UserColor();
                userColor.setColorCode(rainbow[i]);
                userColor.setIsChoosen(true);
                userColor.save();
            }
        }
    }


}
