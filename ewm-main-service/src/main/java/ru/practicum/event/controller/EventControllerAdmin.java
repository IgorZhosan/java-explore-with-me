package ru.practicum.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventUpdateDto;
import ru.practicum.event.service.EventService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventControllerAdmin {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllByAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {
        return eventService.getAllByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto approveEventByAdmin(@PathVariable @Positive Long eventId,
                                            @RequestBody @Valid EventUpdateDto eventUpdateDto) {
        return eventService.approveEventByAdmin(eventId, eventUpdateDto);
    }
}