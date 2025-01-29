package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.User.model.User;
import ru.practicum.User.repository.UserRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.ParticipationRequestMapper;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.ParticipationRequestStatus;
import ru.practicum.request.repository.ParticipationRequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipationRequestMapper participationRequestMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAllParticipationRequests(Long userId) {
        List<ParticipationRequest> requests = participationRequestRepository.findByRequesterId(userId);
        if (requests.isEmpty()) {
            log.info("Заявок на участие в мероприятии, у пользователя с id {} пока нет.", userId);
            return new ArrayList<>();
        }
        log.info("Получение списка всех заявок участия пользователя с id {}.", userId);
        return participationRequestMapper.toParticipationRequestDtoList(requests);
    }

    @Override
    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("События с id = {} не существует." + eventId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} не существует." + userId));
        ParticipationRequest requestValid = participationRequestRepository.findByRequesterIdAndEventId(userId, eventId);
        if (Objects.nonNull(requestValid) || event.getInitiator().getId().equals(user.getId())) {
            throw new ConflictException("Пользователь является инициатором события или уже подал заявку на участие в событии.");
        }
        if (event.getParticipantLimit().equals(event.getConfirmedRequests()) && event.getParticipantLimit() != 0) {
            throw new ConflictException("На данное мероприятие больше нет мест");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Событие еще не было опубликовано.");
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setEvent(event);
        request.setRequester(user);
        request.setCreated(LocalDateTime.now());
        if (event.getParticipantLimit() == 0 ||
                (!event.getRequestModeration() && event.getParticipantLimit() > event.getConfirmedRequests())) {
            request.setStatus(ParticipationRequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
            ParticipationRequest newRequest = participationRequestRepository.save(request);
            log.info("Сохранение заявки на участие со статусом <ПОДТВЕРЖДЕНА>.");
            return participationRequestMapper.toParticipationRequestDto(newRequest);
        }
        if (!event.getRequestModeration() && event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            request.setStatus(ParticipationRequestStatus.REJECTED);
            ParticipationRequest newRequest = participationRequestRepository.save(request);
            log.info("Сохранение заявки на участие со статусом <ОТМЕНЕНА>, в связи с превышением лимита.");
            return participationRequestMapper.toParticipationRequestDto(newRequest);
        }
        request.setStatus(ParticipationRequestStatus.PENDING);
        ParticipationRequest newRequest = participationRequestRepository.save(request);
        log.info("Сохранение заявки на участие со статусом <В ОЖИДАНИИ>.");
        return participationRequestMapper.toParticipationRequestDto(newRequest);
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} не существует." + userId));
        ParticipationRequest request = participationRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Заявки с id = {} не существует." + requestId));
        if (!request.getRequester().equals(user)) {
            throw new ConflictException("Отменить заявку может только пользователь иницировавший её.");
        }
        request.setStatus(ParticipationRequestStatus.CANCELED);
        ParticipationRequest requestCancel = participationRequestRepository.save(request);
        log.info("Заявка на участие с id = {} отменена.", requestId);

        Event event = request.getEvent();
        if (event.getRequestModeration()) {
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
            log.info("Появилось свободное место у события с id = {}.", event.getId());
        }
        return participationRequestMapper.toParticipationRequestDto(requestCancel);
    }
}