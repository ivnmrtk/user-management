package my.demo.usersmanagement.util;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserToUserResponseDtoConverter {

    public UserResponseDto convert (User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .blocked(user.getBlocked())
                .build();
    }
}
