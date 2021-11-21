package english.web.api.controllers;

import english.web.api.dto.AuthInfoDto;
import english.web.api.dto.AuthSuccessDto;
import english.web.api.dto.UserCreateDto;
import english.web.api.dto.UserInfoDto;
import english.web.exceptions.UserAlreadyExistsException;
import english.web.models.User;
import english.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserInfoDto> get(@AuthenticationPrincipal User user) {
        UserInfoDto userDto = userService.get(user);
        return ResponseEntity.ok(userDto);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuthSuccessDto> create(@RequestBody UserCreateDto userCreateDto) throws UserAlreadyExistsException {
        AuthSuccessDto authSuccessDto = userService.create(userCreateDto);
        return ResponseEntity.ok(authSuccessDto);
    }

    @RequestMapping(method = RequestMethod.POST, path = "login")
    public ResponseEntity<AuthSuccessDto> login(@RequestBody AuthInfoDto authInfoDto) {
        AuthSuccessDto authSuccessDto = userService.login(authInfoDto);
        return ResponseEntity.ok(authSuccessDto);
    }
}
