package my.demo.usersmanagement.web;

import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
import my.demo.usersmanagement.exception.UserValidateException;
import my.demo.usersmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    public UserResponseDto findUserByLoginAndPassword(@ModelAttribute("user") UserRequestDto userRequestDto){
        try {
            return userService.findUserByLoginAndPassword(userRequestDto);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }

    }

    @PostMapping(path = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto){
        try{
            return userService.addUser(userRequestDto);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }

    }

    @PutMapping(path = "/update")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto updateUser(@RequestParam Long userId, @RequestBody UserRequestDto userRequestDto){
        try{
            return userService.updateUser(userId, userRequestDto);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }
    }

    @PutMapping(path = "/block")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto blockUser(@RequestParam Long userId){
        try{
            return userService.blockUser(userId);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, validEx.getMessage(), validEx);
        }
    }
}
