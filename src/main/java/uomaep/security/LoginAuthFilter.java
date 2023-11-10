package uomaep.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uomaep.dto.LoginDTO;
import uomaep.utils.ResponseJsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class LoginAuthFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoginAuthFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/user/login", "POST"));
        this.setAuthenticationManager(authenticationManager);

        this.setAuthenticationSuccessHandler((request, response, authentication) -> {
            LoginAuthToken authResult = (LoginAuthToken) authentication;

            var output = new HashMap<String, Object>();
            output.put("success", true);
            output.put("accessToken", authResult.getPrincipal());
            output.put("refreshToken", authResult.getCredentials());

            response.setStatus(HttpServletResponse.SC_OK);
            ResponseJsonWriter.writeJSON(response, output);
        });

        this.setAuthenticationFailureHandler((request, response, exception) -> {
            var output = new HashMap<String, Object>();
            output.put("success", false);
            output.put("message", exception.getMessage() != null ? exception.getMessage() : "unexpected error");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ResponseJsonWriter.writeJSON(response, output);
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        String body = sb.toString();

        LoginDTO loginReq = objectMapper.readValue(body, LoginDTO.class);

        String email = Optional.ofNullable(loginReq.getEmail())
                .orElseThrow(() -> new BadCredentialsException("no email field"));

        String password = Optional.ofNullable(loginReq.getPassword())
                .orElseThrow(() -> new BadCredentialsException("no password field"));

        LoginAuthToken authReq = new LoginAuthToken(email, password);

        return this.getAuthenticationManager().authenticate(authReq);
    }
}
