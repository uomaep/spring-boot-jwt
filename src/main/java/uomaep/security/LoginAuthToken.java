package uomaep.security;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class LoginAuthToken extends AbstractAuthenticationToken {
    @NonNull private final String email;
    @NonNull private final String password;
    private UserData userData;

    public LoginAuthToken(String email, String password) {
        super(null);
        this.email = email;
        this.password = password;
    }

    public LoginAuthToken(String email, String password, UserData userData) {
        super(null);
        this.email = email;
        this.password = password;
        this.userData = userData;
        super.setAuthenticated(true);
    }

    public LoginAuthToken(String email, String password, UserData userData, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.password = password;
        this.userData = userData;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}
