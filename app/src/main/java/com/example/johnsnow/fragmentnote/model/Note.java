package com.example.johnsnow.fragmentnote.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Notes")
public class Note extends Model {

    public static final String NOTE = "NOTE";
    public static final String ID = "id_for_note";
    public static int idCounter;
    static {
        Note n = new Select().from(Note.class).orderBy(ID + " DESC").executeSingle();
        if (n != null){
            idCounter = n.idNumber;
        } else {
            idCounter = 0;
        }
    }


    @Column(name = NOTE)
    private String name;

    @Column(name = ID)
    private int idNumber;

    public int getIdNumber() {
        return idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public Note() {
        super();
    }

    public Note(String name) {
        super();
        this.name = name;
        this.idNumber = ++idCounter;
        save();
    }


    public static Note findbyId(int id) {
        return new Select().from(Note.class).where(ID + "= ?", id).executeSingle();
    }

    public static List<Note> getAll() {
        return new Select().from(Note.class).execute();
    }

}
