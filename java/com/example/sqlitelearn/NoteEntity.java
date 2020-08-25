package com.example.sqlitelearn;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class NoteEntity {

    public NoteEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "note_title")
    private String title;

    @ColumnInfo(name = "note_desc")
    private String description;

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}