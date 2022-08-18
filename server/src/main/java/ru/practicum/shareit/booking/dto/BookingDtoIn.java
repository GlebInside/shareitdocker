package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDtoIn {
    @JsonProperty("start")
    private LocalDateTime startDateTime;
    @JsonProperty("end")
    private LocalDateTime endDateTime;
    private int itemId;
}
