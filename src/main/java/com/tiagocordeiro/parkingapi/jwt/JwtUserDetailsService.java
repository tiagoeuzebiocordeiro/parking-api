package com.tiagocordeiro.parkingapi.jwt;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService  implements UserDetailsService {

    private final UserService userService;

    /*Query by user username, if it exists, we'll return this user, but in UserDetails format.*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new JwtUserDetails(user);
    }
    /*Auth action, if success, send it and auth*/
    public JwtToken getTokenAuthenticated(String username) {
        User.Role role = userService.findRoleByUsername(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }

}
