package com.example.repositories;

import com.example.models.Note;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleOrContent(String title, String content);

    List<Note> findByTitleAndArchivedFalse(String title);

    List<Note> findByTitleAndArchivedTrue(String title);

    List<Note> findByDateTimeAndArchivedFalse(LocalDateTime dateTime, Sort sort);

    List<Note> findByDateTimeAndArchivedTrue (LocalDateTime dateTime, Sort sort);

    List<Note> findByCategoryAndArchivedFalse(String category, Sort sort);

    List<Note> findByStatusAndArchivedFalse(Boolean status);

    List<Note> findByArchivedFalse(Sort sort);

    List<Note> findByArchivedTrue(Sort sort);

    List<Note> findByDateTimeBeforeAndStatus(LocalDateTime now, boolean status);

}
