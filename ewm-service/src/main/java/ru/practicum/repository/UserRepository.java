package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByIdIn(Collection<Long> id, Pageable pageable);
}
