package my.demo.usersmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * dto пользователя для запроса на сервер
 * Используется при добавлениии, редактировании
 * и поиске по логину и паролю
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    /**
     * Логин
     */
    private String login;

    /**
     * Пароль
     */
    private String password;

}
