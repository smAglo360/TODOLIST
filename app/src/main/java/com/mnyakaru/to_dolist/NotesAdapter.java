package com.mnyakaru.to_dolist;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    private List<Note> notes = new ArrayList<>();

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (
                        R.layout.note_item,
                        parent,
                        false
                );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        int colorResId;
        switch (note.getPriority()) {
            case 1:
                colorResId = R.drawable.notes_priority_2_note;
                break;
            case 2:
                colorResId = R.drawable.notes_priority_3_note;
                break;
            default:
                colorResId = R.drawable.notes_priority_1_note;
                break;
        }
        viewHolder.linearLayoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoteClickListener.onNoteClickListener(note);
            }
        });
        viewHolder.textViewDate.setText(note.getDate());
        viewHolder.textViewNoteText.setText(note.getValue());
        Drawable color = ContextCompat.getDrawable(viewHolder.itemView.getContext(), colorResId);
        viewHolder.linearLayoutNote.setBackground(color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayoutNote;
        TextView textViewNoteText;
        TextView textViewDate;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutNote = itemView.findViewById(R.id.linearLayoutNote);
            textViewNoteText = itemView.findViewById(R.id.textViewNoteText);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }

    interface OnNoteClickListener {
        void onNoteClickListener(Note note);
    }
}
