package ru.practicum.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;

import java.util.List;
import java.util.Set;

import static ru.practicum.handler.exception.NotFoundException.notFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> search(Set<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (ids == null) {
            return userRepository.findAll(pageable).getContent();
        }
        return userRepository.findByIdIn(ids, pageable).getContent();
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(notFoundException("User not found {0}", id));
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
