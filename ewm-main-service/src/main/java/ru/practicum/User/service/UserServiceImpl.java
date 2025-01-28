package ru.practicum.User.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.User.dto.UserDtoInput;
import ru.practicum.User.dto.UserDtoOutput;
import ru.practicum.User.mapper.UserMapper;
import ru.practicum.User.model.User;
import ru.practicum.User.repository.UserRepository;
import ru.practicum.exception.DuplicatedDataException;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDtoOutput> getAllUsers(List<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        log.info("Запрос на получение списка пользователей");
        List<User> users;
        if (ids == null || ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).getContent();
            log.info("Получен список всех пользователей");
        } else {
            users = userRepository.findByIdIn(ids, pageRequest);
            log.info("Получен список пользователей по заданным id");
        }
        return userMapper.toListDto(users);
    }

    @Override
    public UserDtoOutput createUser(UserDtoInput userDtoInput) {
        User userCandidate = userMapper.toUser(userDtoInput);

        // Допустимая простая проверка дублей
        if (userRepository.findAll().contains(userCandidate)) {
            log.warn("Пользователь с id = {} уже добавлен в список.", userDtoInput.getId());
            throw new DuplicatedDataException("Этот пользователь уже существует.");
        }

        User user = userRepository.save(userCandidate);
        log.info("Пользователь с id = {} добавлен.", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
        log.info("Пользователь с id  = {} удален.", userId);
    }
}