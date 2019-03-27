package my.demo.usersmanagement.dto;

/**
 * dto пользователя для ответа
 * с сервера (исключает пароль)
 */
public class UserResponseDto {

    private Long id;

    private String login;

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
