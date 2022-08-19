package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoIn;
import ru.practicum.shareit.booking.dto.Status;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exception.InternalServerErrorException;

import java.util.Collection;


@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    private BookingDto createNew(@RequestBody BookingDtoIn dto, @RequestHeader("X-Sharer-User-Id") int bookerId) {
        return BookingMapper.toDto(bookingService.createNew(dto, bookerId));
    }

    @PatchMapping("/{bookingId}")
    private BookingDto update(@PathVariable int bookingId, @RequestHeader("X-Sharer-User-Id") int ownerId, Boolean approved) {
        return BookingMapper.toDto(bookingService.update(bookingId, ownerId, approved));
    }

    @GetMapping("/{bookingId}")
    private BookingDto get(@PathVariable int bookingId, @RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("Get booking {}, userId={}", bookingId, userId);

        return BookingMapper.toDto(bookingService.getById(bookingId, userId));
    }

    @GetMapping
    private Collection<BookingDto> get(@RequestParam(defaultValue = "ALL", name = "state") String statusString, @RequestHeader("X-Sharer-User-Id") int bookerId) {
        var status = Status.ALL;
        try {
            status = Status.valueOf(statusString);
        } catch (IllegalArgumentException ex) {
            throw new InternalServerErrorException("Unknown state: " + statusString);
        }
        return bookingService.getAllByBookerAndStatus(bookerId, status);
    }


    @GetMapping("/owner")
    private Collection<BookingDto> getByOwner(@RequestParam(defaultValue = "ALL", name = "state") String statusString, @RequestHeader("X-Sharer-User-Id") int ownerId) {
        var status = Status.ALL;
        try {
            status = Status.valueOf(statusString);
        } catch (IllegalArgumentException ex) {
            throw new InternalServerErrorException("Unknown state: " + statusString);
        }
        return bookingService.getAllByOwnerAndStatus(ownerId, status);
    }
}

