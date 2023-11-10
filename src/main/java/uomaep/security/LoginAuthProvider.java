package uomaep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthProvider implements AuthenticationProvider {
    @Autowired UserDataService userDataService;
    @Autowired PasswordEncoderSHA512 passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginAuthToken auth = (LoginAuthToken) authentication;
        UserData userData = (UserData) userDataService.loadUserByUsername(auth.getEmail());

        if(!passwordEncoder.matches(auth.getEmail().concat(auth.getPassword()), userData.getPassword())) {
            throw new BadCredentialsException("Invalid credential");
        }

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        // encoded refreshToken save to db
        userDataService.saveRefreshTokenToDB(userData.getUserId(), passwordEncoder.encode(refreshToken));

        return new LoginAuthToken(accessToken, refreshToken, userData);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        if(authentication == null) return false;

        return authentication == LoginAuthToken.class;
    }
}
