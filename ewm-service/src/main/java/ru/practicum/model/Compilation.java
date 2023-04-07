package ru.practicum.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilation")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "compilation_event")
    private List<Event> events;

    private boolean pinned;

    private String title;
}
