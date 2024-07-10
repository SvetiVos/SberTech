package com.example.services;

import com.example.models.Note;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class NotesService {
    private List<Note> notes = new ArrayList<>();
    private long ID = 0;

    {
        notes.add(new Note(++ID, "Заметка №1", "Сделать домашку №1"));
        notes.add(new Note(++ID, "Заметка №2", "Сделать домашку №2"));
        notes.add(new Note(++ID, "Заметка №3", "Не сойти с ума"));
        notes.add(new Note(++ID, "Заметка №4", "Выжить"));
    }

    public List<Note> listNotes() {
        return notes;
    }

    public void saveNote(Note note) {
        note.setId(++ID);
        notes.add(note);
    }

    public void deleteNote(Long id) {
        notes.removeIf(note -> note.getId().equals(id));
    }

    public Note getNoteById(Long id) {
        for (Note note : notes) {
            if (note.getId().equals(id)) return note;
        }
        return null;
    }

    public void updateNote(Note updatedNote) {
        for (Note note : notes) {
            if (note.getId() == updatedNote.getId()) {
                note.setTitle(updatedNote.getTitle());
                note.setContent(updatedNote.getContent());
            }
        }
    }
}
