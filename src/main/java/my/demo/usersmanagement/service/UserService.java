package my.demo.usersmanagement.service;

import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;

public interface UserService {

    UserResponseDto findUserById(Long id);

    UserResponseDto findUserByLoginAndPassword(UserRequestDto userRequestDto);

    UserResponseDto addUser(UserRequestDto user);

    UserResponseDto updateUser(Long userId, UserRequestDto user);

    UserResponseDto blockUser(Long id);

}