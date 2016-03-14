package com.kizema.johnsnow.colornotes.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "UserColor")
public class UserColor extends Model {

    public static final String COLOR_CODE = "Color";
    public static final String IS_CHOOSEN = "ChosenColor";

    @Column(name = COLOR_CODE)
    private int colorCode;

    @Column(name = IS_CHOOSEN)
    private boolean isChoosen;

    public UserColor(){
        super();
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    public void setIsChoosen(boolean isChoosen) {
        this.isChoosen = isChoosen;
    }

    public int getColorCode() {
        return colorCode;
    }

    public boolean isChoosen() {
        return isChoosen;
    }

    public static List<UserColor> getAll() {
        return new Select().from(UserColor.class).execute();
    }

    public static List<UserColor> getAllUsers() {
        return new Select().from(UserColor.class).where(IS_CHOOSEN + "= ?", true).execute();
    }

}
