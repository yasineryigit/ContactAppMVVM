package com.ossovita.contactappmvvm.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String number;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }
}
