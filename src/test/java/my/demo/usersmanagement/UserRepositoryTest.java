package my.demo.usersmanagement;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.repository.UserRepository;
import org.junit.Before;
import org.junit.Ignore;
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

    @Before
    public void startUp(){

    }

    @Test
    public void findByLoginAndPassword(){
        Optional <User> persistedUser =  userRepository.findByLoginAndPassword("larisa", "qwerty");
        assertTrue(persistedUser.isPresent());
        assertEquals(persistedUser.get().getLogin(), "larisa");
        assertEquals(persistedUser.get().getPassword(), "qwerty");
        assertEquals(persistedUser.get().getBlocked(), false);
    }

    @Test
    @Ignore
    public void addUserTest(){
        User newUser = new User();
        newUser.setLogin("test1");
        newUser.setPassword("test1");
        userRepository.save(newUser);
    }

}
