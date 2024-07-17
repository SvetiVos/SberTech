package com.example.controller;

import com.example.models.Note;
import com.example.services.NotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.format.DateTimeFormatter;


@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NotesService notesService;

    @GetMapping("/")
    public String getNotesPage(@RequestParam(name = "title", required = false) String title,
                               @RequestParam(name = "dateTime", required = false) LocalDateTime dateTime,
                               Model model) {
        List<Note> notesList;
        if (dateTime != null) {
            notesList = notesService.searchNotesByDeadline(dateTime);
        } else {
            notesList = (title != null && !title.isEmpty()) ? notesService.listNotes(title) : notesService.listNotes(null);
        }
        model.addAttribute("notesList", notesList);
        model.addAttribute("note", new Note());
        return "notesList";
    }


    @GetMapping("/archive")
    public String getArchive(@RequestParam(name = "title", required = false) String title,
                             @RequestParam(name = "dateTime", required = false) LocalDateTime dateTime,
                             Model model) {
        List<Note> archivedNotesList;
        if (dateTime != null) {
            archivedNotesList = notesService.searchNotesByDeadlineArchived(dateTime);
        } else {
            archivedNotesList = (title != null && !title.isEmpty()) ? notesService.listArchivedNotes(title) : notesService.listArchivedNotes(null);
        }
        model.addAttribute("archivedNotesList", archivedNotesList);
        model.addAttribute("note", new Note());
        return "archivedNotesList";
    }

    @GetMapping("/filter")
    public String getNotesPageStatus(Boolean status, Model model) {
        List<Note> notesList = notesService.filterNotesByStatus(status);
        model.addAttribute("notesList", notesList);
        model.addAttribute("note", new Note());
        return "notesList";
    }

    @GetMapping("/note/{id}")
    public String noteInfo(@PathVariable Long id, Model model) {
        model.addAttribute("note", notesService.getNoteById(id));
        return "note-info";
    }

    @PostMapping("/note/create")
    public String createNote(Note note) {
        notesService.saveNote(note);
        if (note.getRepeatable() != null) {
            notesService.createRepeatingNotes(note);
        }
        return "redirect:/";
    }

    @PostMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        notesService.deleteNote(id);
        return "redirect:/";
    }

    @PostMapping("/note/change/status/{id}")
    public String changeStatus(@PathVariable Long id) {
        notesService.changeStatusById(id);
        return "redirect:/";
    }

    @PostMapping("/note/update")
    public String updateNote(Note updatedNote) {
        notesService.updateNote(updatedNote);
        return "redirect:/";
    }

    @PostMapping("/note/archive/{id}")
    public String archiveNote(@PathVariable Long id) {
        notesService.archiveNoteById(id, true);
        return "redirect:/";
    }

    @PostMapping("/note/unarchive/{id}")
    public String unarchiveNote(@PathVariable Long id) {
        notesService.archiveNoteById(id, false);
        return "redirect:/archive";
    }


}