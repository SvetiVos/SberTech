package com.example.repositories;

import com.example.models.Note;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import java.time.LocalDateTime;
import com.example.models.User;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitleAndUserAndArchivedFalse(String title, User user);

    List<Note> findByTitleAndUserAndArchivedTrue(String title, User user);

    List<Note> findByDateTimeAndUserAndArchivedFalse(LocalDateTime dateTime, User user, Sort sort);

    List<Note> findByDateTimeAndUserAndArchivedTrue(LocalDateTime dateTime, User user, Sort sort);

    List<Note> findByCategoryAndUserAndArchivedFalse(String category, User user, Sort sort);

    List<Note> findByStatusAndUserAndArchivedFalse(Boolean status, User user);

    List<Note> findByUserAndArchivedFalse(User user, Sort sort);

    List<Note> findByUserAndArchivedTrue(User user, Sort sort);

    List<Note> findByStartDate(LocalDateTime startDate);
}

