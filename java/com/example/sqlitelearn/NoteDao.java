package com.example.sqlitelearn;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface NoteDao {
    @Insert
    void insert(NoteEntity note);

    @Update
    void update(NoteEntity note);

    @Delete
    void delete(NoteEntity note);

    @Query("DELETE FROM notes")
    void deleteAll();

    @Query("SELECT * FROM notes ORDER BY note_title ASC")
    LiveData<List<NoteEntity>> getAllData();
}
