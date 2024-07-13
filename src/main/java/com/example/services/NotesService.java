package com.example.services;

import com.example.models.Note;
import com.example.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.data.domain.Sort;


@RequiredArgsConstructor
@Slf4j
@Service
public class NotesService {
    private final NoteRepository noteRepository;

    public List<Note> listNotes(String title) {
        if (title != null) return noteRepository.findByTitle(title);
        return noteRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> searchNotesByDeadline(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty()) {
            return noteRepository.findByDateTime(dateTime, Sort.by(Sort.Direction.ASC, "priority"));
        }
        return noteRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> listNotesByCategory(String category) {
        if (category != null && !category.isEmpty()) {
            return noteRepository.findByCategory(category, Sort.by(Sort.Direction.ASC, "priority"));
        }
        return noteRepository.findAll(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> filterNotesByStatus(Boolean status) {
        return noteRepository.findByStatus(status);
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
            noteToUpdate.setPriority(updatedNote.getPriority());
            noteToUpdate.setCategory(updatedNote.getCategory());
            noteToUpdate.setStatus(updatedNote.getStatus());
            noteRepository.save(noteToUpdate);
            log.info("Note updated successfully: {}", noteToUpdate);
        } else {
            log.error("Note with id {} not found", updatedNote.getId());
        }
    }

}
