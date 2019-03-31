package my.demo.usersmanagement.service;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;

/**
 * Сервис пользователя
 */
public interface UserService {

    /**
     * Поиск пользователя по логину и паролю
     * @param login логин
     * @param password пароль
     * @return dto ответа, содержащее данные искомого пользователя
     */
    User findUserByLoginAndPassword(String login, String password);

    /**
     * Добавить пользователя
     * @param login логин
     * @param password пароль
     * @return dto ответа, содержащее данные добавленного пользователя
     */
    User addUser(String login, String password);

    /**
     * Обновить информацию пользователя
     * @param login логин
     * @param password пароль
     * @param userId идентификатор обновляющ
     * @return dto ответа, содержащее данные  добавленного пользователя
     */

    User updateUser(Long userId, String login, String password);

    /**
     * Сервис блокировки пользователя
     * @param userId идентификатор блокируемого пользователя
     * @return dro ответа, содержащее данные заблокированного пользователя
     */
    User blockUser(Long userId);

}