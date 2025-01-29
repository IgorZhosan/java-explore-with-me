package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.request.dto.ConfirmedRequest;
import ru.practicum.request.model.ParticipationRequest;
import ru.practicum.request.model.ParticipationRequestStatus;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    ParticipationRequest findByRequesterIdAndEventId(final Long requesterId,
                                                     final Long eventId);

    List<ParticipationRequest> findByRequesterId(final Long requesterId);

    List<ParticipationRequest> findByEventId(final Long eventId);

    List<ParticipationRequest> findRequestByIdIn(final List<Long> requestsId);

    @Query(value = "SELECT new ru.practicum.request.dto.ConfirmedRequest(r.event.id, COUNT(r.id)) " +
            "FROM ParticipationRequest r " +
            "WHERE r.event.id IN (:eventIds) AND r.status = :status " +
            "GROUP BY r.id, r.event.id " +
            "ORDER BY r.id, r.event.id")
    List<ConfirmedRequest> getConfirmedRequestsByStatus(@Param("eventIds") List<Long> eventIds, @Param("status") ParticipationRequestStatus status);
}
