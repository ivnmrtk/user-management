package my.demo.usersmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * dto пользователя для ответа
 * клиенту (включает в себя все данные о пользователе,
 * за исключением пароля)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    /**
     * Идентификатор пользователя
     */
    private Long id;

    /**
     * Логин
     */
    private String login;

    /**
     * Признак: пользователь заблокирован
     */
    private Boolean blocked;

}
