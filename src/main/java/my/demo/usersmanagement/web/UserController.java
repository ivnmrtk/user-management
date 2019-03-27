package my.demo.usersmanagement.web;

import my.demo.usersmanagement.dto.UserRequestDto;
import my.demo.usersmanagement.dto.UserResponseDto;
import my.demo.usersmanagement.exception.NullRequestParamException;
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

    @GetMapping(path = "/getUser")
    public UserResponseDto findUserByLoginAndPassword(@ModelAttribute("user") UserRequestDto userRequestDto){
        try {
            return userService.findUserByLoginAndPassword(userRequestDto);
        }
        catch (NullRequestParamException nullEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nullEx.getMessage(), nullEx);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.CONFLICT, validEx.getMessage(), validEx);
        }

    }

    @PostMapping(path = "/addUser")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto){
        try{
            return userService.addUser(userRequestDto);
        }
        catch (NullRequestParamException nullEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nullEx.getMessage(), nullEx);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.CONFLICT, validEx.getMessage(), validEx);
        }

    }

    @PutMapping(path = "/updateUser")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto updateUser(@RequestParam Long userId, @RequestBody UserRequestDto userRequestDto){
        try{
            return userService.updateUser(userId, userRequestDto);
        }
        catch (NullRequestParamException nullEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nullEx.getMessage(), nullEx);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.CONFLICT, validEx.getMessage(), validEx);
        }
    }

    @PutMapping(path = "/blockUser")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto blockUser(@RequestParam Long userId){
        try{
            return userService.blockUser(userId);
        }
        catch (NullRequestParamException nullEx){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nullEx.getMessage(), nullEx);
        }
        catch (UserValidateException validEx){
            throw new ResponseStatusException(HttpStatus.CONFLICT, validEx.getMessage(), validEx);
        }
    }
}
