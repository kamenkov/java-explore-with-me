package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Event;
import ru.practicum.model.EventState;
import ru.practicum.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByStateAndId(EventState state, Long id);

    Page<Event> findEventsByInitiator(User initiator, Pageable pageable);

    @Query("select e from Event e " +
            "where (:users IS NULL or e.initiator.id IN :users) " +
            "and (:states IS NULL or e.state IN :states) " +
            "and (:categories IS NULL or e.category.id IN :categories) " +
            "and (e.eventDate BETWEEN :rangeStart AND :rangeEnd)")
    Page<Event> adminEventSearch(
            @Param("users") Set<Long> users,
            @Param("states") Set<EventState> states,
            @Param("categories") Set<Long> categories,
            @Param("rangeStart") LocalDateTime rangeStart,
            @Param("rangeEnd") LocalDateTime rangeEnd,
            Pageable pageable);

    @Query("select e from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and (:paid IS NULL OR e.paid = :paid) " +
            "and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.state = :state " +
            "and (:categories IS NULL OR e.category.id IN :categories) " +
            "and (:onlyAvailable IS NULL OR (:onlyAvailable = true " +
            "and (SELECT COUNT(*) FROM Request r WHERE r.event = e AND r.status = 'CONFIRMED') < e.participantLimit) " +
            "or :onlyAvailable = false)")
    Page<Event> publicEventSearch(@Param("text") String text,
                                  @Param("categories") Collection<Long> categories,
                                  @Param("paid") Boolean paid,
                                  @Param("rangeStart") LocalDateTime rangeStart,
                                  @Param("rangeEnd") LocalDateTime rangeEnd,
                                  @Param("onlyAvailable") Boolean onlyAvailable,
                                  @Param("state") EventState state,
                                  Pageable pageable);

    Optional<Event> findEventByIdAndInitiator(Long id, User initiator);
}
