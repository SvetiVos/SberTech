package com.example.repositories;

import com.example.models.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitle(String title);
}
