package com.example.controller;

import com.example.models.Note;
import com.example.services.NotesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NotesService notesService;

    @GetMapping("/")
    public String getNotesPage(@RequestParam(name = "title", required = false) String title,
                               @RequestParam(name = "dateTime", required = false) String dateTime,
                               @RequestParam(name = "status", required = false) Boolean status,
                               Model model) {
        List<Note> notesList;
        if (dateTime != null && !dateTime.isEmpty()) {
            notesList = notesService.searchNotesByDeadline(dateTime);
        } else {
            notesList = notesService.listNotes(title);
        }
        model.addAttribute("notesList", notesList);
        model.addAttribute("note", new Note());
        return "notesList";
    }

    @GetMapping("/filter")
    public String getNotesPageStatus(Boolean status) {
        notesService.filterNotesByStatus(status);
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
        return "redirect:/";
    }

    @PostMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        notesService.deleteNote(id);
        return "redirect:/";
    }

    @PostMapping("/note/update")
    public String updateNote(Note updatedNote) {
        notesService.updateNote(updatedNote);
        return "redirect:/";
    }

}