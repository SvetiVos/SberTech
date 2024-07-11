package com.example.services;

import com.example.models.Note;
import com.example.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class NotesService {
    private final NoteRepository noteRepository;

    public List<Note> listNotes(String title) {
        if (title != null) return noteRepository.findByTitle(title);
        return noteRepository.findAll();
    }

    public void saveNote(Note note) {
        log.info("Saving new {}", note);
        noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public void updateNote(Note updatedNote) {
        Optional<Note> existingNote = noteRepository.findById(updatedNote.getId());
        if(existingNote.isPresent()){
            Note noteToUpdate = existingNote.get();
            noteToUpdate.setTitle(updatedNote.getTitle());
            noteToUpdate.setContent(updatedNote.getContent());
            noteToUpdate.setContent(updatedNote.getDateTime());
            noteRepository.save(noteToUpdate);
            log.info("Note updated successfully: {}", noteToUpdate);
        } else {
            log.error("Note with id {} not found", updatedNote.getId());
        }
    }
}
