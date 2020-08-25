package com.example.sqlitelearn;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerAdapter extends RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder> {
    private List<NoteEntity> allNotes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity note = allNotes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        System.out.println(allNotes.size());
        return allNotes.size();
    }

    public void setNotes(List<NoteEntity> notes) {
        this.allNotes = notes;
        notifyDataSetChanged();
    }

    public NoteEntity getNotes(int position) {
        return allNotes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);

            itemView.setOnClickListener(new View.OnClickListener() { //When the cardview gets clicked, everything gets triggered
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClickListener(allNotes.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(NoteEntity note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
