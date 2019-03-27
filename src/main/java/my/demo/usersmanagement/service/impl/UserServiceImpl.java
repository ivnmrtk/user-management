package my.demo.usersmanagement.service.impl;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
import my.demo.usersmanagement.exception.NullRequestParamException;
import my.demo.usersmanagement.exception.UserValidateException;
import my.demo.usersmanagement.repository.UserRepository;
import my.demo.usersmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Deprecated //TODO remove this shit(
    public UserResponseDto findUserById(Long userId) throws NullRequestParamException, UserValidateException {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(this::formUserResponseDto)
                .orElseThrow(() -> new UserValidateException("Can't find user by id" + userId.toString()));
    }

    @Override
    public UserResponseDto findUserByLoginAndPassword(UserRequestDto userRequestDto) throws NullRequestParamException, UserValidateException {
        validateUserRequestDto(userRequestDto);
        Optional<User> optionalUser = userRepository.findByLoginAndPassword(userRequestDto.getLogin(), userRequestDto.getPassword());
        return optionalUser.map(this::formUserResponseDto)
                .orElseThrow(() -> new UserValidateException("Can't find user by selected login and password"));
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) throws NullRequestParamException, UserValidateException {
        validateUserRequestDto(userRequestDto);

        User userToSave = new User();
        userToSave.setLogin(userRequestDto.getLogin());
        userToSave.setPassword(userRequestDto.getPassword());

        return formUserResponseDto(userRepository.save(userToSave));
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) throws NullRequestParamException, UserValidateException {
        if (userId == null) {
            throw new NullRequestParamException("User identifier is empty!");
        }
        validateUserRequestDto(userRequestDto);

        Optional <User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User userToSave = optionalUser.get();
            userToSave.setLogin(userRequestDto.getLogin());
            userToSave.setPassword(userRequestDto.getPassword());

            return formUserResponseDto(userRepository.save(userToSave));
        }
        else {
            throw new UserValidateException("Can't find user by id = " + userId.toString());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto blockUser(Long userId) throws NullRequestParamException, UserValidateException {
        if (userId == null) {
            throw new NullRequestParamException("User identifier is empty!");
        }

        Optional <User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if (!user.getBlocked()){
                user.setBlocked(true);
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

    private void validateUserRequestDto(UserRequestDto userRequestDto){
        if (userRequestDto.getLogin()==null || userRequestDto.getLogin().isEmpty()){
            throw new NullRequestParamException("Login is empty!");
        }
        if (userRequestDto.getPassword()==null || userRequestDto.getPassword().isEmpty()){
            throw new NullRequestParamException("Password is empty!");
        }
    }
}
