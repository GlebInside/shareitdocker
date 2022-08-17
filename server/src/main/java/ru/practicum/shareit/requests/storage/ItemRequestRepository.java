package ru.practicum.shareit.requests.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.requests.model.ItemRequest;

import java.util.Collection;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Integer> {
    Collection<ItemRequest> findByRequesterId(int requesterId);


    @Query("select r from ItemRequest r where r.requester.id != :requesterId")
    Page<ItemRequest> findForNotRequester(int requesterId, Pageable pageable);

    @Query("select r from ItemRequest r where r.requester.id != :requesterId")
    Collection<ItemRequest> findForNotRequester(int requesterId);
}

