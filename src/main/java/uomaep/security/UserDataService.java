package uomaep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uomaep.dto.UserDTO;
import uomaep.mapper.UserDataMapper;

@Configuration
public class UserDataService implements UserDetailsService {
    @Autowired UserDataMapper userDataMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userDataMapper.findUserByEmail(username);
        if(user == null) throw new UsernameNotFoundException("Not found");
        return new UserData(username, user.getPassword());
    }
}
