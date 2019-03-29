package my.demo.usersmanagement.dto;

/**
 * dto пользователя для ответа
 * клиенту (включает в себя все данные о пользователе,
 * за исключением пароля)
 */
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

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
