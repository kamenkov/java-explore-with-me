package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
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

    Page<Event> findByInitiatorIdInAndStateInAndCategoryIdInAndEventDateBetween(
            Set<Long> users, Set<EventState> states, Set<Long> categories,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("select e from Event e " +
            "where (upper(e.annotation) like upper(concat('%', :text, '%')) " +
            "or upper(e.description) like upper(concat('%', :text, '%'))) " +
            "and e.paid = :paid and e.eventDate between :rangeStart and :rangeEnd " +
            "and e.state = :state and e.category.id in :categories " +
            "and (:onlyAvailable = true " +
            "and (SELECT COUNT(*) FROM Request r WHERE r.event = e AND r.status = 'CONFIRMED') < e.participantLimit " +
            "or :onlyAvailable = false)")
    Page<Event> publicEventSearch(@Param("text") @Nullable String text,
                                  @Param("categories") @Nullable Collection<Long> categories,
                                  @Param("paid") @Nullable boolean paid,
                                  @Param("rangeStart") @Nullable LocalDateTime rangeStart,
                                  @Param("rangeEnd") @Nullable LocalDateTime rangeEnd,
                                  @Param("onlyAvailable") @Nullable boolean onlyAvailable,
                                  @Param("state") @Nullable EventState state,
                                  Pageable pageable);

    Optional<Event> findEventByIdAndInitiator(Long id, User initiator);
}
