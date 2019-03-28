package my.demo.usersmanagement;


import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.exception.UserValidateException;
import my.demo.usersmanagement.repository.UserRepository;
import my.demo.usersmanagement.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        User userFirst = new User();
        userFirst.setId(1L);
        userFirst.setLogin("user");
        userFirst.setPassword("passw");
        userFirst.setBlocked(false);

        when(userRepository.findByLoginAndPassword("user", "passw")).thenReturn(Optional.of(userFirst));
    }

    @Test(expected = UserValidateException.class)
    public void findByEmptyLoginTestNegative(){
        userService.findUserByLoginAndPassword(new UserRequestDto());
    }

    public void findByEmptyLoginTest(){
        userService.findUserByLoginAndPassword()
    }




}
