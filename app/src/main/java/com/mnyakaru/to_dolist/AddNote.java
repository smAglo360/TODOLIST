package com.mnyakaru.to_dolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

public class AddNote extends AppCompatActivity {

    private EditText editTextNoteText;
    private MaterialButton buttonCreateNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHigh;
    @SuppressLint("StaticFieldLeak")
    private static CalendarView calendarViewDeadlineDate;
    private AddNoteViewModel addNoteViewModel;

    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        initViews();
        saveUserDate();
        buttonCreateNote.setOnClickListener(v -> saveNote());
        addNoteViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldCloseScreen) {
                if (shouldCloseScreen) {
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNote.class);
    }

    private void saveUserDate() {
        calendarViewDeadlineDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view,
                                            int year,
                                            int month,
                                            int dayOfMonth) {
                updateDate(year, month, dayOfMonth);
            }
        });
    }

    private String setUpDate(int year, int month, int dayOfMonth) {
        return "Until: " + dayOfMonth + ":" + month + ":" + year;
    }

    public void updateDate(int year, int month, int dayOfMonth) {
        date = setUpDate(year, month, dayOfMonth);
    }

    private void saveNote() {
        String value = editTextNoteText.getText().toString();
        int priority = getPriority();
        Note note = new Note(value, priority, date);
        if (note.getValue().isEmpty()) {
            Toast.makeText(
                    AddNote.this,
                    R.string.error_empty_user_note_text_field,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            addNoteViewModel.saveNote(note);
        }
    }

    private int getPriority() {
        int priority;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        } else if (radioButtonMedium.isChecked()) {
            priority = 1;
        } else if (radioButtonHigh.isChecked()) {
            priority = 2;
        } else {
            priority = 1;
        }
        return priority;
    }

    private void initViews() {
        editTextNoteText = findViewById(R.id.editTextNoteText);
        calendarViewDeadlineDate = findViewById(R.id.calendarViewDeadlineDate);
        buttonCreateNote = findViewById(R.id.buttonCreateNote);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        radioButtonHigh = findViewById(R.id.radioButtonHigh);
    }
}