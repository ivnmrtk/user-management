package my.demo.usersmanagement.service.impl;

import my.demo.usersmanagement.domain.User;
import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
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
    public UserResponseDto findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(this::formUserResponseDto).orElse(null);
    }

    @Override
    public UserResponseDto findUserByLoginAndPassword(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLoginAndPassword(login, password);
        return optionalUser.map(this::formUserResponseDto).orElse(null);
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User userToSave = new User();
        userToSave.setLogin(userRequestDto.getLogin());
        userToSave.setPassword(userRequestDto.getPassword());
        return formUserResponseDto(userRepository.save(userToSave));
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserRequestDto userRequestDto) {
        Optional <User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            User userToSave = optionalUser.get();
            userToSave.setLogin(userRequestDto.getLogin());
            userToSave.setPassword(userRequestDto.getPassword());
            return formUserResponseDto(userRepository.save(userToSave));
        }
        else {
            return null;
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto blockUser(Long id) {
        Optional <User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if (!user.getBlocked()){
                user.setBlocked(true);
                return formUserResponseDto(userRepository.save(user));
            }
            else {
                //TODO отправить ошибку
                return null;
            }
        }
        else{
            //TODO отправить ошибку
            return null;
        }
    }


    private UserResponseDto formUserResponseDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setLogin(user.getLogin());
        userResponseDto.setBlocked(user.getBlocked());
        return userResponseDto;
    }
}
