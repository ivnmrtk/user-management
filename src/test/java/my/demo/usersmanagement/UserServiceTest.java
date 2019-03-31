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
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){

    }

    @Test(expected = UserValidateException.class)
    public void findByEmptyLoginTestNegative(){
        userService.findUserByLoginAndPassword(" ", "pass");
    }

    @Test
    public void findByLoginAndPasswordTest(){

        User userFirst = new User();
        userFirst.setId(1L);
        userFirst.setLogin("user");
        userFirst.setPassword("passw");
        userFirst.setBlocked(false);

        when(userRepository.findByLoginAndPassword("user", "passw")).thenReturn(Optional.of(userFirst));

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLogin("user");
        userRequestDto.setPassword("passw");

        User foundUser = userService.findUserByLoginAndPassword("user", "passw");

        assertNotNull(foundUser);
        assertEquals("user", foundUser.getLogin());
        assertEquals(1L, (long)foundUser.getId());
        assertEquals(false, foundUser.getBlocked());

        verify(userRepository, times(1)).findByLoginAndPassword("user", "passw");
    }


    @Test
    public void saveUserTest(){

        UserRequestDto newUserRequestDto = new UserRequestDto();
        newUserRequestDto.setLogin("newuser");
        newUserRequestDto.setPassword("newpassw");


        User savedUserForMocking = new User();
        savedUserForMocking.setId(2L);
        savedUserForMocking.setLogin("newuser");
        savedUserForMocking.setPassword("newpassw");
        savedUserForMocking.setBlocked(false);

        when(userRepository.save(isA(User.class))).thenReturn(savedUserForMocking);

        when(userRepository.existsByLogin("newuser")).thenReturn(false);

        User returnedSavedUser = userService.addUser("newuser", "newpassw");

        verify(userRepository, times(1)).save(isA(User.class));

        assertNotNull(returnedSavedUser);
        assertNotNull(returnedSavedUser.getId());
        assertEquals("newuser", returnedSavedUser.getLogin());
        assertEquals(false, returnedSavedUser.getBlocked());
    }


}
