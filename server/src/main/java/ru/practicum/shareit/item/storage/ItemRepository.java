package ru.practicum.shareit.item.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("select i from Item i where i.request.id = :requestId")
    Collection<Item> findItemsByRequest(int requestId);
}

