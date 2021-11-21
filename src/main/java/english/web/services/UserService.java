package english.web.services;

import english.web.api.dto.AuthInfoDto;
import english.web.api.dto.AuthSuccessDto;
import english.web.api.dto.UserCreateDto;
import english.web.api.dto.UserInfoDto;
import english.web.exceptions.UserAlreadyExistsException;
import english.web.models.User;
import english.web.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public UserInfoDto get(User user) {
        return modelMapper.map(user, UserInfoDto.class);
    }

    public AuthSuccessDto login(AuthInfoDto authInfoDto) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authInfoDto.getLogin(), authInfoDto.getPassword()));
        User user = (User) authenticate.getPrincipal();

        return authenticate(user);
    }

    public AuthSuccessDto create(UserCreateDto userCreateDto) throws UserAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findByLogin(userCreateDto.getLogin());

        if (optionalUser.isPresent())
            throw new UserAlreadyExistsException();

        User user = new User(userCreateDto.getLogin(), passwordEncoder.encode(userCreateDto.getPassword()));
        userRepository.save(user);
        return authenticate(user);
    }

    private AuthSuccessDto authenticate(User user) {

        UserInfoDto userInfoDto = modelMapper.map(user, UserInfoDto.class);
        String token = jwtService.generateAccessToken(user);
        AuthSuccessDto authSuccessDto = new AuthSuccessDto(userInfoDto, token);
        return authSuccessDto;
    }
}
