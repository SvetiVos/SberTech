package com.example.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import com.example.Repeatable;

@Entity
@Table(name = "notesList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private Boolean status = false;

    @Column(name = "archived")
    private Boolean archived = false;

    @Column(name = "repeatable")
    private Repeatable repeatable;

    @Column(name = "startDate")
    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
