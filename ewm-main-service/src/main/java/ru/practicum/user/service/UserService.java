package ru.practicum.user.service;

import ru.practicum.user.dto.UserDtoInput;
import ru.practicum.user.dto.UserDtoOutput;

import java.util.List;

public interface UserService {
    List<UserDtoOutput> getAllUsers(List<Long> ids, int from, int size);

    UserDtoOutput createUser(UserDtoInput userDtoInput);

    void deleteUser(Long userId);
}
