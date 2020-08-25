package com.example.sqlitelearn;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNotes = repository.getAllNotes();
        System.out.println("Called again");
    }

    public void insert(NoteEntity entity) {
        repository.insert(entity);
    }

    public void update(NoteEntity entity) {
        repository.update(entity);
    }

    public void delete(NoteEntity entity) {
        repository.delete(entity);
    }

    public void deleteAllNotes() {
        repository.deleteAll();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }
}
