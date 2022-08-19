package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    @PositiveOrZero
    private int id;

    @Size(min = 1)
    private String name;
    @Size(min = 1)
    private String description;
    private Boolean available;

    private int ownerId;
    private ItemBookingDto lastBooking;
    private ItemBookingDto nextBooking;
    private Collection<CommentDto> comments;
    private Integer requestId;
}
