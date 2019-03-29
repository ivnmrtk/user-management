package my.demo.usersmanagement.repository;

import my.demo.usersmanagement.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Интерфейс репозитория пользователя
 */
@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    /**
     * Найти пользователя по логину и паролю
     * @param login логин
     * @param password пароль
     * @return искомый пользователь
     */
    Optional <User> findByLoginAndPassword(String login, String password);

    /**
     * Проверка на наличие пользователя с заданным логином
     * @param login логин
     * @return true при наличии, иначе false
     */
    boolean existsByLogin(String login);

}
