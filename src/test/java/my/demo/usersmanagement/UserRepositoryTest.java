package my.demo.usersmanagement;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
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

    //FIXME crushes when running after controller test
    @Test
    public void updateUserTest(){
        User user = new User();
        user.setId(3L);
        user.setLogin("updatedUser");
        user.setPassword("updPass");
        user.setBlocked(true);
        userRepository.save(user);
        Optional <User> persistedUserOptional = userRepository.findById(3L);
        assertTrue(persistedUserOptional.isPresent());
        assertEquals("updatedUser", persistedUserOptional.get().getLogin());
        assertEquals("updPass", persistedUserOptional.get().getPassword());
        assertTrue(persistedUserOptional.get().getBlocked());
    }
}
