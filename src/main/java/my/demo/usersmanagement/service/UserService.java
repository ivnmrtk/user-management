package my.demo.usersmanagement.service;

import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;

/**
 * Сервис пользователя
 */
public interface UserService {

    /**
     * Поиск пользователя по логину и паролю
     * @param userRequestDto объект, содержащий логин и пароль
     * @return dto ответа искомого пользователя
     */
    UserResponseDto findUserByLoginAndPassword(UserRequestDto userRequestDto);

    /**
     * Добавить пользователя
     * @param userRequestDto объект, содержащий логин и пароль
     * @return dto ответа добавленного пользователя
     */
    UserResponseDto addUser(UserRequestDto userRequestDto);

    /**
     * Обновить информацию пользователя
     * @param userRequestDto объект, содержащий логин и пароль
     * @return dto ответа добавленного пользователя
     */

    UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto);

    UserResponseDto blockUser(Long id);

}