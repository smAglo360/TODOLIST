package com.mnyakaru.to_dolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private final int id;
    private final String value;
    private final int priority;

    public String getDate() {
        return date;
    }

    private final String date;

    public Note(int id, String value, int priority, String date) {
        this.id = id;
        this.value = value;
        this.date = date;
        this.priority = priority;
    }

    @Ignore
    public Note(String value, int priority, String date) {
        this(0, value, priority, date);
    }


    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }
}
