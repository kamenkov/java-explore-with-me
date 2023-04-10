package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Event;
import ru.practicum.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRequesterIdAndId(Long id, Long id1);

    Optional<Request> findByRequesterIdAndEventId(Long id, Long id1);

    List<Request> findByRequesterId(Long requesterId);

    List<Request> findByEvent(Event event);

    List<Request> findRequestsByIdIn(List<Long> ids);

}
