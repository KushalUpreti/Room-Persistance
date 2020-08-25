package com.example.sqlitelearn;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NoteEntity.class}, version = 1)
public abstract class NoteDB extends RoomDatabase {
    private static final String TAG = "NoteDB";
    public abstract NoteDao noteDao();

    private static NoteDB instance;

    public static synchronized NoteDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDB.class,
                    "note_database").fallbackToDestructiveMigration().addCallback(callback)
                        .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDb(instance).start();
        }
    };

    private static class PopulateDb extends Thread implements Runnable {
        private static final String TAG  = "";
        private NoteDao noteDao;

        public PopulateDb(NoteDB db) {
            this.noteDao = db.noteDao();
        }

        @Override
        public void run() {
            noteDao.insert(new NoteEntity("First", "Description 1"));
        }
    }
}
