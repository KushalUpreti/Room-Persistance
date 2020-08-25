package com.example.sqlitelearn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddOrEditNotes extends AppCompatActivity {
    private EditText title;
    private EditText description;
    public static final String NODE_ID = "Node id";
    public static final String TITLE_DATA = "Title_data";
    public static final String DESC_DATA = "Desc_data";
    private static final String TAG = "AddNotes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = findViewById(R.id.add_title);
        description = findViewById(R.id.add_description);

        Intent intent = getIntent();
        //If the intent that started this activity has a NOTE_ID, change the title and populate the fields.
        if(intent.hasExtra(NODE_ID)){
            setTitle("Edit Nodes");
            title.setText(intent.getStringExtra(TITLE_DATA));
            description.setText(intent.getStringExtra(DESC_DATA));
        }else {
            setTitle("Add Nodes");
        }
    }

    private void saveNotes(){
        String titleText = title.getText().toString().trim();
        String descText = description.getText().toString().trim();

        if(titleText.length()!= 0 && descText.length()!= 0){
            Intent intent = new Intent();
            intent.putExtra(TITLE_DATA,titleText);
            intent.putExtra(DESC_DATA,descText);

            int id = getIntent().getIntExtra(NODE_ID,-1);
            if(id!=-1){
                intent.putExtra(NODE_ID,id);
            }

            setResult(RESULT_OK,intent); //Sending result back
            finish(); //Shutting this activity.
            return;
        }
        Toast.makeText(this, "The title and description fields should not be empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.save_button){
            saveNotes();
        }
        return true;
    }


}