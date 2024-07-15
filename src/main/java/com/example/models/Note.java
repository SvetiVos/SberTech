package com.example.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name = "notesList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "dateTime")
    private String dateTime;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private Boolean status = false;

    @Column(name = "archived")
    private Boolean archived = false;

}
