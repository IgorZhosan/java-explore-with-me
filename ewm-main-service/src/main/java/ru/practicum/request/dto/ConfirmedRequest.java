package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmedRequest {
    private long eventId;
    private long confirmedCountRequests;
}