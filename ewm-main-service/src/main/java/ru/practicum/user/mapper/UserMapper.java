package ru.practicum.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.user.dto.UserDtoInput;
import ru.practicum.user.dto.UserDtoOutput;
import ru.practicum.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User toUser(UserDtoInput userDtoInput);

    UserDtoOutput toUserDto(User user);

    List<UserDtoOutput> toListDto(List<User> users);
}