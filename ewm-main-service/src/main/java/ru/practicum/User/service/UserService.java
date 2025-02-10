package ru.practicum.User.service;

import ru.practicum.User.dto.UserDtoInput;
import ru.practicum.User.dto.UserDtoOutput;

import java.util.List;

public interface UserService {
    List<UserDtoOutput> getAllUsers(List<Long> ids, int from, int size);

    UserDtoOutput createUser(UserDtoInput userDtoInput);

    void deleteUser(Long userId);
}
