package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.User.model.User;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventNewDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(
            target = "state",
            expression = "java(ru.practicum.event.model.EventState.PENDING)"
    )
    @Mapping(target = "initiator", source = "initiator")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "confirmedRequests", constant = "0")
    @Mapping(target = "annotation", source = "eventRequestDto.annotation")
    @Mapping(target = "description", source = "eventRequestDto.description")
    @Mapping(target = "eventDate", source = "eventRequestDto.eventDate")
    @Mapping(target = "location", source = "eventRequestDto.location")
    @Mapping(target = "paid", source = "eventRequestDto.paid")
    @Mapping(target = "participantLimit", source = "eventRequestDto.participantLimit")
    @Mapping(target = "requestModeration", source = "eventRequestDto.requestModeration")
    @Mapping(target = "title", source = "eventRequestDto.title")
    @Mapping(
            target = "createdOn",
            expression = "java(java.time.LocalDateTime.now())"
    )
    Event toEvent(EventNewDto eventRequestDto, User initiator, Category category);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "annotation", source = "event.annotation")
    @Mapping(
            target = "category",
            expression = "java(new ru.practicum.category.dto.CategoryOutputDto(event.getCategory().getId(), event.getCategory().getName()))"
    )
    @Mapping(target = "confirmedRequests", source = "event.confirmedRequests")
    @Mapping(target = "eventDate", source = "event.eventDate")
    @Mapping(
            target = "initiator",
            expression = "java(new ru.practicum.User.dto.UserDtoShort(event.getInitiator().getId(), event.getInitiator().getName()))"
    )
    @Mapping(target = "paid", source = "event.paid")
    @Mapping(target = "title", source = "event.title")
    @Mapping(target = "views", expression = "java(0)")
    EventShortDto toEventShortDto(Event event);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "annotation", source = "event.annotation")
    @Mapping(
            target = "category",
            expression = "java(new ru.practicum.category.dto.CategoryOutputDto(event.getCategory().getId(), event.getCategory().getName()))"
    )
    @Mapping(target = "confirmedRequests", source = "event.confirmedRequests")
    @Mapping(target = "createdOn", source = "event.createdOn")
    @Mapping(target = "description", source = "event.description")
    @Mapping(target = "eventDate", source = "event.eventDate")
    @Mapping(
            target = "initiator",
            expression = "java(new ru.practicum.User.dto.UserDtoShort(event.getInitiator().getId(), event.getInitiator().getName()))"
    )
    @Mapping(target = "location", source = "event.location")
    @Mapping(target = "paid", source = "event.paid")
    @Mapping(target = "participantLimit", source = "event.participantLimit")
    @Mapping(target = "publishedOn", source = "event.publishedOn")
    @Mapping(target = "requestModeration", source = "event.requestModeration")
    @Mapping(target = "state", source = "event.state")
    @Mapping(target = "title", source = "event.title")
    @Mapping(target = "views", source = "event.views")
    EventFullDto toEventFullDto(Event event);

    List<EventShortDto> toEventShortDtoList(List<Event> events);

    List<EventFullDto> toEventFullDtoList(List<Event> events);
}