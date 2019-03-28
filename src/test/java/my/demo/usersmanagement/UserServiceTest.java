package my.demo.usersmanagement;


import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
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
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private UserRequestDto newUserRequestDto;

    private User newUser;

    @Before
    public void setUp(){

        newUserRequestDto = new UserRequestDto();
        newUserRequestDto.setLogin("newuser");
        newUserRequestDto.setPassword("newpassw");

        newUser = new User();
        newUser.setLogin(newUserRequestDto.getLogin());
        newUser.setLogin(newUserRequestDto.getPassword());

        User savedUser = new User();
        savedUser.setId(2L);
        savedUser.setLogin("newuser");
        savedUser.setPassword("newpassw");
        savedUser.setBlocked(false);

        User userFirst = new User();
        userFirst.setId(1L);
        userFirst.setLogin("user");
        userFirst.setPassword("passw");
        userFirst.setBlocked(false);

        when(userRepository.findByLoginAndPassword("user", "passw")).thenReturn(Optional.of(userFirst));

        when(userRepository.save(newUser)).thenReturn(savedUser);

    }

    @Test(expected = UserValidateException.class)
    public void findByEmptyLoginTestNegative(){
        userService.findUserByLoginAndPassword(new UserRequestDto());
    }

    @Test
    public void findByEmptyLoginTest(){

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLogin("user");
        userRequestDto.setPassword("passw");

        UserResponseDto userResponseDto = userService.findUserByLoginAndPassword(userRequestDto);

        assertNotNull(userResponseDto);
        assertEquals("user", userResponseDto.getLogin());
        assertEquals(1L, (long)userResponseDto.getId());
        assertEquals(false, userResponseDto.getBlocked());

        verify(userRepository, times(1)).findByLoginAndPassword("user", "passw");
    }

    //FIXME
    @Test
    public void addUserTest(){
        when(userRepository.existsByLogin("newuser")).thenReturn(false);

        UserResponseDto userResponseDto = userService.addUser(newUserRequestDto);

        verify(userRepository, times(1)).save(newUser);

        assertNotNull(userResponseDto);
        assertNotNull(userResponseDto.getId());
        assertEquals("newUser", userResponseDto.getLogin());
        assertEquals(false, userResponseDto.getBlocked());
    }




}
