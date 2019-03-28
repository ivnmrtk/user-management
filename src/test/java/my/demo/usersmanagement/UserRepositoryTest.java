package my.demo.usersmanagement;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Sql("classpath:test_data.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByLoginAndPassword(){
        Optional <User> persistedUser =  userRepository.findByLoginAndPassword("larisa", "qwerty");
        assertTrue(persistedUser.isPresent());
        assertEquals(persistedUser.get().getLogin(), "larisa");
        assertEquals(persistedUser.get().getPassword(), "qwerty");
        assertEquals(persistedUser.get().getBlocked(), false);
    }

    @Test
    public void addUserTest(){
        User newUser = new User();
        newUser.setLogin("test1");
        newUser.setPassword("test1");
        userRepository.save(newUser);
        Optional <User> persistedUser = userRepository.findById(newUser.getId());
        assertTrue(persistedUser.isPresent());
        assertEquals("test1", persistedUser.get().getLogin());
        assertEquals("test1", persistedUser.get().getPassword());
        assertFalse(persistedUser.get().getBlocked());
    }

    @Test
    public void updateUserTest(){
        User user = new User();
        user.setId(1L);
        user.setLogin("updatedUser");
        user.setPassword("updPass");
        user.setBlocked(true);
        //debug
        userRepository.findAll().forEach(u -> System.out.println(u));
        userRepository.save(user);
        Optional <User> persistedUserOptional = userRepository.findById(1L);
        assertTrue(persistedUserOptional.isPresent());
        assertEquals("updatedUser", persistedUserOptional.get().getLogin());
        assertEquals("updPass", persistedUserOptional.get().getPassword());
        assertTrue(persistedUserOptional.get().getBlocked());
    }
}
