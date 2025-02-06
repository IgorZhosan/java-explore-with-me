package ru.practicum.event.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.User.model.User;
import ru.practicum.category.model.Category;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer confirmedRequests;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "annotation")
    private String annotation;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EventState state;

    @NotNull
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @NotNull
    private Boolean paid;

    @NotNull
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Transient
    private Integer views;
}