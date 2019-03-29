package my.demo.usersmanagement.exception;

/**
 * Класс исключения
 * для валидации данных пользователя
 */
public class UserValidateException extends RuntimeException {

    public UserValidateException(String message) {
        super(message);
    }
}
