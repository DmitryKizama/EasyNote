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

    @Column(name = NOTE)
    public String name;

    @Column(name = ID)
    public String id;

    public Note() {
        super();
    }

    public Note(String name, String id) {
        super();
        this.name = name;
        this.id = id;
    }



    public static Note findbyId(int id) {
        Note n = new Select().from(Note.class).where("Id = ?", id).executeSingle();
        return n;
    }

    public List<Note> items() {
        return getMany(Note.class, "Category");
    }
}
