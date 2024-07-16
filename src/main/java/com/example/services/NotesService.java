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
        if (title != null) return noteRepository.findByTitleAndArchivedFalse(title);
        return noteRepository.findByArchivedFalse(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> searchNotesByDeadline(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty()) {
            return noteRepository.findByDateTimeAndArchivedFalse(dateTime, Sort.by(Sort.Direction.ASC, "priority"));
        }
        return noteRepository.findByArchivedFalse(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> searchNotesByDeadlineArchived(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty()) {
            return noteRepository.findByDateTimeAndArchivedTrue(dateTime, Sort.by(Sort.Direction.ASC, "priority"));
        }
        return noteRepository.findByArchivedTrue(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> listNotesByCategory(String category) {
        if (category != null && !category.isEmpty()) {
            return noteRepository.findByCategoryAndArchivedFalse(category, Sort.by(Sort.Direction.ASC, "priority"));
        }
        return noteRepository.findByArchivedFalse(Sort.by(Sort.Direction.ASC, "priority"));
    }

    public List<Note> filterNotesByStatus(Boolean status) {
        return noteRepository.findByStatusAndArchivedFalse(status);
    }

    public List<Note> listArchivedNotes(String title) {
        if (title != null) return noteRepository.findByTitleAndArchivedTrue(title);
        return noteRepository.findByArchivedTrue(Sort.by(Sort.Direction.ASC, "priority"));
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
            noteToUpdate.setArchived(updatedNote.getArchived());
            noteRepository.save(noteToUpdate);
            log.info("Note updated successfully: {}", noteToUpdate);
        } else {
            log.error("Note with id {} not found", updatedNote.getId());
        }
    }

    public void changeStatusById(Long id) {
        Optional<Note> existingNote = noteRepository.findById(id);
        if (existingNote.isPresent()) {
            Note note = existingNote.get();
            if (note.getStatus() == null) {
                note.setStatus(true);
            } else {
                note.setStatus(!note.getStatus());
            }
            noteRepository.save(note);
            log.info("Note status changed successfully: {}", note);
        } else {
            log.error("Note with id {} not found", id);
        }
    }

    public void archiveNoteById(Long id, boolean archive) {
        Optional<Note> existingNote = noteRepository.findById(id);
        if (existingNote.isPresent()) {
            Note noteToArchive = existingNote.get();
            noteToArchive.setArchived(archive);
            noteRepository.save(noteToArchive);
            log.info("Note archived/unarchived successfully: {}", noteToArchive);
        } else {
            log.error("Note with id {} not found", id);
        }
    }

}
