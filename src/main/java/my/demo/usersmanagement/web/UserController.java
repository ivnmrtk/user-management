package my.demo.usersmanagement.web;

import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
import my.demo.usersmanagement.exception.UserValidateException;
import my.demo.usersmanagement.service.UserService;
import my.demo.usersmanagement.util.UserToUserResponseDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

    private final UserService userService;

    private final UserToUserResponseDtoConverter userToUserResponseDtoConverter;

    @Autowired
    public UserController(UserService userService, UserToUserResponseDtoConverter userToUserResponseDtoConverter) {
        this.userService = userService;
        this.userToUserResponseDtoConverter = userToUserResponseDtoConverter;
    }

    /**
     * Найти пользователя по логину и паролю для авторизации
     * @param userRequestDto логин и пароль искомого пользователя
     * @return данные искомого пользователя
     */
    @GetMapping(path = "")
    public UserResponseDto findUserByLoginAndPassword(@ModelAttribute("user") UserRequestDto userRequestDto){
        try {
            return userToUserResponseDtoConverter.convert(userService.findUserByLoginAndPassword(userRequestDto.getLogin(), userRequestDto.getPassword()));
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }
    }

    /**
     * Создать пользователя
     * @param userRequestDto данные создаваемого пользователя (логин, пароль)
     * @return данные созданного пользователя
     */
    @PostMapping(path = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto){
        try{
            return userToUserResponseDtoConverter.convert(userService.addUser(userRequestDto.getLogin(), userRequestDto.getPassword()));
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }

    }

    /**
     * Обновление информации (логина или пароля) пользователя
     * @param userId идентификатор обновляемого пользователя
     * @param userRequestDto данные обновляемого пользователя (логин, пароль)
     * @return данные обновленного пользователя
     */
    @PutMapping(path = "/update")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto updateUser(@RequestParam Long userId, @RequestBody UserRequestDto userRequestDto){
        try{
            return userToUserResponseDtoConverter.convert(userService.updateUser(userId, userRequestDto.getLogin(), userRequestDto.getPassword()));
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }
    }

    /**
     * Заблокировать пользователя
     * @param userId идентификатор блокируемого пользователя
     * @return данные заблокированного пользователя
     */
    @PutMapping(path = "/block")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto blockUser(@RequestParam Long userId){
        try{
            return userToUserResponseDtoConverter.convert(userService.blockUser(userId));
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }
    }
}
