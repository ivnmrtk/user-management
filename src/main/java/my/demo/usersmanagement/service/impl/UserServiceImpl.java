package my.demo.usersmanagement.service.impl;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserResponseDto;
import my.demo.usersmanagement.exception.UserValidateException;
import my.demo.usersmanagement.repository.UserRepository;
import my.demo.usersmanagement.service.UserService;
import my.demo.usersmanagement.util.UserToUserResponseDtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Реализация сервиса пользователя
 * Документацию к методам см. в {@link UserRepository}
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserToUserResponseDtoConverter userToUserResponseDtoConverter;

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserToUserResponseDtoConverter userToUserResponseDtoConverter) {
        this.userRepository = userRepository;
        this.userToUserResponseDtoConverter = userToUserResponseDtoConverter;
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) throws UserValidateException {
        if (login==null || login.isEmpty()){
            throw new UserValidateException("Login is empty!");
        }

        if (password==null || password.isEmpty()){
            throw new UserValidateException("Password is empty!");
        }


        Optional<User> optionalUser = userRepository.findByLoginAndPassword(login, password);
        return optionalUser.orElseThrow(() -> new UserValidateException("Can't find user by selected login and password"));
    }

    @Override
    public User addUser(String login, String password) throws UserValidateException {
        commonUserRequestDtoValidation(login, password);

        if (userRepository.existsByLogin(login)){
            throw new UserValidateException("User with selected login is already exists!");
        }

        User userToSave = new User();
        userToSave.setLogin(login);
        userToSave.setPassword(password);

        log.info("Trying to add user " + userToSave);

        return userRepository.save(userToSave);
    }

    @Override
    public User updateUser(Long userId, String login, String password) throws UserValidateException {
        if (userId == null) {
            throw new UserValidateException("User identifier is empty!");
        }
        commonUserRequestDtoValidation(login, password);

        Optional <User> persistedUserById = userRepository.findById(userId);

        if (!persistedUserById.isPresent()){
            throw new UserValidateException("Can't find user by id = " + userId.toString());
        }

        User userToSave = persistedUserById.get();

        Optional <User> persistedUserByRequestedLogin = userRepository.findByLogin(login);

        persistedUserByRequestedLogin.ifPresent(user -> {
            if(!user.getLogin().equals(userToSave.getLogin()))
            {
                throw new UserValidateException("User with selected login is already exists!");
            }
        });


        userToSave.setLogin(login);
        userToSave.setPassword(password);

        log.info("Trying to update user " + userToSave);

        return userRepository.save(userToSave);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User blockUser(Long userId) throws UserValidateException {
        if (userId == null) {
            throw new UserValidateException("User identifier is empty!");
        }

        Optional <User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()){
            throw new UserValidateException("Can't find user by id = " + userId.toString());
        }

        User user = optionalUser.get();
        if (!user.getBlocked()){
            user.setBlocked(true);
            log.info("Trying to block user " + user);
            return userRepository.save(user);
        }

        else {
            throw new UserValidateException("Selected user is already blocked!");
        }

    }

    private void commonUserRequestDtoValidation(String login,  String password){

        if (login==null || login.isEmpty()){
            throw new UserValidateException("Login is empty!");
        }

        loginLengthValidation(login);

        if (password==null || password.isEmpty()){
            throw new UserValidateException("Password is empty!");
        }

        passwordLengthValidate(password);

    }

    private void loginLengthValidation(String login){
        if (login.length() > 20) {
            throw new UserValidateException("Login length is more than 20 symbols!");
        }
    }

    private void passwordLengthValidate(String password){
        if(password.length() > 10){
            throw new UserValidateException("Password length is more than 10 symbols!");
        }
    }

}
