package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.ParticipationRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipationRequestMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "requester", expression = "java(participationRequest.getRequester().getId())")
    @Mapping(target = "event", expression = "java(participationRequest.getEvent().getId())")
    @Mapping(target = "status", source = "participationRequest.status")
    @Mapping(target = "created", source = "participationRequest.created")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);

    List<ParticipationRequestDto> toParticipationRequestDtoList(List<ParticipationRequest> requests);
}
