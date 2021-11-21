package english.web.api;

import english.web.models.User;
import english.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder,
                                        UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<User> userOptional = userRepository.findByLogin(authentication.getName());

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        boolean passwordIsMatched = passwordEncoder
                .matches((String) authentication.getCredentials(), user.getEncodedPassword());

        if (passwordIsMatched) {
            return new UsernamePasswordAuthenticationToken(user,
                    authentication.getCredentials(), new ArrayList<>());
        }

        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
