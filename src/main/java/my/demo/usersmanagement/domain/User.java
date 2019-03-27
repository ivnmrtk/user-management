package my.demo.usersmanagement.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user")
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="user_pk_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Логин пользователя
     */
    @Column(name = "login", length = 20, nullable = false, unique = true)
    @NotNull(message = "Login can't be null")
    private String login;

    /**
     * Пароль пользователя
     */
    @Column(name = "password", length = 10, nullable = false, unique = true)
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
