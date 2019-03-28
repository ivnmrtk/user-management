package my.demo.usersmanagement.service.impl;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
import my.demo.usersmanagement.exception.UserValidateException;
import my.demo.usersmanagement.repository.UserRepository;
import my.demo.usersmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto findUserByLoginAndPassword(UserRequestDto userRequestDto) throws UserValidateException {
        if (userRequestDto.getLogin()==null || userRequestDto.getLogin().isEmpty()){
            throw new UserValidateException("Login is empty!");
        }

        if (userRequestDto.getPassword()==null || userRequestDto.getPassword().isEmpty()){
            throw new UserValidateException("Password is empty!");
        }


        Optional<User> optionalUser = userRepository.findByLoginAndPassword(userRequestDto.getLogin(), userRequestDto.getPassword());
        return optionalUser.map(this::formUserResponseDto)
                .orElseThrow(() -> new UserValidateException("Can't find user by selected login and password"));
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) throws UserValidateException {
        validateSaveUserRequestDto(userRequestDto);

        User userToSave = new User();
        userToSave.setLogin(userRequestDto.getLogin());
        userToSave.setPassword(userRequestDto.getPassword());

        log.debug("Trying to add user " + userToSave);

        return formUserResponseDto(userRepository.save(userToSave));
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) throws UserValidateException {
        if (userId == null) {
            throw new UserValidateException("User identifier is empty!");
        }
        validateSaveUserRequestDto(userRequestDto);

        Optional <User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User userToSave = optionalUser.get();
            userToSave.setLogin(userRequestDto.getLogin());
            userToSave.setPassword(userRequestDto.getPassword());

            log.debug("Trying to update user " + userToSave);

            return formUserResponseDto(userRepository.save(userToSave));
        }
        else {
            throw new UserValidateException("Can't find user by id = " + userId.toString());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto blockUser(Long userId) throws UserValidateException {
        if (userId == null) {
            throw new UserValidateException("User identifier is empty!");
        }

        Optional <User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if (!user.getBlocked()){
                user.setBlocked(true);
                log.debug("Trying to block user " + user);
                return formUserResponseDto(userRepository.save(user));
            }
            else {
                throw new UserValidateException("Selected user is already blocked!");
            }
        }
        else{
            throw new UserValidateException("Can't find user by id = " + userId.toString());
        }
    }

    private UserResponseDto formUserResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setLogin(user.getLogin());
        userResponseDto.setBlocked(user.getBlocked());
        return userResponseDto;
    }

    private void validateSaveUserRequestDto(UserRequestDto userRequestDto){

        if (userRequestDto.getLogin()==null || userRequestDto.getLogin().isEmpty()){
            throw new UserValidateException("Login is empty!");
        }

        if(userRequestDto.getLogin().length() > 20){
            throw new UserValidateException("Login length is more than 20 symbols!");
        }

        if (userRequestDto.getPassword()==null || userRequestDto.getPassword().isEmpty()){
            throw new UserValidateException("Password is empty!");
        }

        if(userRequestDto.getPassword().length() > 10){
            throw new UserValidateException("Password length is more than 10 symbols!");
        }

        if (userRepository.existsByLogin(userRequestDto.getLogin())){
            throw new UserValidateException("User with selected login is already exists!");
        }
    }

}
