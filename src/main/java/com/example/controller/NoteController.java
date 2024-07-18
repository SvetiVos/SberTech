package com.example.controller;

import com.example.models.Note;
import com.example.services.NotesService;
import com.example.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.format.DateTimeFormatter;
import com.example.Repeatable;
import java.security.Principal;
import com.example.models.User;


@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NotesService notesService;

    @GetMapping("/")
    public String getNotesPage(@RequestParam(name = "title", required = false) String title,
                               @RequestParam(name = "dateTime", required = false) LocalDateTime dateTime,
                               Model model, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        List<Note> notesList;
        if (dateTime != null) {
            notesList = notesService.searchNotesByDeadline(dateTime, user);
        } else {
            notesList = (title != null && !title.isEmpty()) ? notesService.listNotes(title, user) : notesService.listNotes(null, user);
        }
        model.addAttribute("notesList", notesList);
        model.addAttribute("note", new Note());
        return "notesList";
    }

    @GetMapping("/archive")
    public String getArchive(@RequestParam(name = "title", required = false) String title,
                             @RequestParam(name = "dateTime", required = false) LocalDateTime dateTime,
                             Model model, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        List<Note> archivedNotesList;
        if (dateTime != null) {
            archivedNotesList = notesService.searchNotesByDeadlineArchived(dateTime, user);
        } else {
            archivedNotesList = (title != null && !title.isEmpty()) ? notesService.listArchivedNotes(title, user) : notesService.listArchivedNotes(null, user);
        }
        model.addAttribute("archivedNotesList", archivedNotesList);
        model.addAttribute("note", new Note());
        return "archivedNotesList";
    }

    @GetMapping("/filter")
    public String getNotesPageStatus(Boolean status, Model model, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        List<Note> notesList = notesService.filterNotesByStatus(status, user);
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
    public String createNote(Note note, @RequestParam Repeatable repeatable, @RequestParam LocalDateTime startDate, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        if (repeatable != Repeatable.NONE) {
            note.setRepeatable(repeatable);
            note.setDateTime(startDate);
        }
        notesService.saveNote(note, user);
        return "redirect:/";
    }

    @PostMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        notesService.deleteNote(id);
        return "redirect:/";
    }

    @PostMapping("/note/change/status/{id}")
    public String changeStatus(@PathVariable Long id, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        notesService.changeStatusById(id, user);
        return "redirect:/";
    }

    @PostMapping("/note/update")
    public String updateNote(Note updatedNote, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        notesService.updateNote(updatedNote, user);
        return "redirect:/";
    }

    @PostMapping("/note/archive/{id}")
    public String archiveNote(@PathVariable Long id, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        notesService.archiveNoteById(id, true, user);
        return "redirect:/";
    }

    @PostMapping("/note/unarchive/{id}")
    public String unarchiveNote(@PathVariable Long id, Principal principal) {
        User user = notesService.getUserByPrincipal(principal);
        notesService.archiveNoteById(id, false, user);
        return "redirect:/archive";
    }
}