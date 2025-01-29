package ru.practicum.event.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.User.model.User;
import ru.practicum.category.model.Category;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator; // Инициатор события

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // Категория события

    @Column(name = "confirmed_requests")
    Integer confirmedRequests; //Количество одобренных заявок на участие в данном событии

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; // место проведения события

    private String title; // Название события

    private String annotation;  // Аннотация события

    private String description; // Описание события

    @Enumerated(EnumType.STRING)
    private EventState state; // Состояние события

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate; // Дата и время события

    @Column(name = "created_on")
    private LocalDateTime createdOn; // Дата и время создания события

    @Column(name = "published_on")
    private LocalDateTime publishedOn; // Дата и время публикации события

    @Column(name = "participant_limit")
    private Integer participantLimit; // Лимит участников

    private Boolean paid; // Платное событие

    @Column(name = "request_moderation")
    private Boolean requestModeration; // Модерация заявок

    @Transient
    Integer views; // Количество просмотров события
}
