package com.example.sqlitelearn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    public static final int NOTE_REQUEST = 1;
    public static final int NOTE_REQUEST2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteRecyclerAdapter noteRecyclerAdapter = new NoteRecyclerAdapter();
        recyclerView.setAdapter(noteRecyclerAdapter);

        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                noteRecyclerAdapter.setNotes(noteEntities);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteRecyclerAdapter.getNotes(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        noteRecyclerAdapter.setOnItemClickListener(new NoteRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(NoteEntity note) {
                Intent intent = new Intent(MainActivity.this, AddOrEditNotes.class);
                intent.putExtra(AddOrEditNotes.NODE_ID,note.getId());
                intent.putExtra(AddOrEditNotes.TITLE_DATA,note.getTitle());
                intent.putExtra(AddOrEditNotes.DESC_DATA,note.getDescription());
                startActivityForResult(intent,NOTE_REQUEST2);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            Intent intent = new Intent(this, AddOrEditNotes.class);
            startActivityForResult(intent, NOTE_REQUEST);
        }else if(item.getItemId() == R.id.deleteAll){
            noteViewModel.deleteAllNotes();
            Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddOrEditNotes.TITLE_DATA);
            String desc = data.getStringExtra(AddOrEditNotes.DESC_DATA);

            NoteEntity noteEntity = new NoteEntity(title, desc);
            noteViewModel.insert(noteEntity);
        }
        else if(requestCode == NOTE_REQUEST2 && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddOrEditNotes.NODE_ID,-1);
            if(id == -1){
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddOrEditNotes.TITLE_DATA);
            String desc = data.getStringExtra(AddOrEditNotes.DESC_DATA);

            NoteEntity entity = new NoteEntity(title,desc);
            entity.setId(id);
            noteViewModel.update(entity);

        }
        else {
            Toast.makeText(this, "Note save cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
