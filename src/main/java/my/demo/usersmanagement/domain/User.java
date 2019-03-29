package my.demo.usersmanagement.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Основной класс пользователя
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @SequenceGenerator(name = "userSequence", sequenceName = "user_pk_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "userSequence")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    /**
     * Логин пользователя
     */
    @Column(name = "login", length = 20, nullable = false, unique = true)
    @NotNull(message = "Login can't be null")
    @Length
    private String login;

    /**
     * Пароль пользователя
     */
    @Column(name = "password", length = 10, nullable = false, unique = true)
    @NotNull(message = "Password can't be null")
    private String password;

    /**
     * Приизнак: пользователь заблокирован
     */
    @Column(name = "blocked", nullable = false)
    private Boolean blocked = Boolean.FALSE;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
