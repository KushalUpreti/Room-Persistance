package com.example.sqlitelearn;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class Repository {
    private NoteDao noteDao;
    private LiveData<List<NoteEntity>> allNotes;

    public Repository(Application application) {
        NoteDB database = NoteDB.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllData();
    }

    public void insert(final NoteEntity entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(entity);
            }
        }).start();
    }

    public void update(final NoteEntity entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.update(entity);
            }
        }).start();
    }

    public void delete(final NoteEntity entity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(entity);
            }
        }).start();
    }

    public void deleteAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteAll();
            }
        }).start();
    }

    public LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }
}
