package com.andres.springcloud.msvc.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import com.andres.springcloud.msvc.oauth.rest.RestUsersAdapter;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.andres.springcloud.msvc.oauth.models.User;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final RestUsersAdapter restUsersAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = restUsersAdapter.getUserByUsername(username);

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.isEnabled(),
                    true, true, true, roles);
        } catch (FeignException e) {
            throw new UsernameNotFoundException(
                    "Error en el login, no existe el users '" + username + "' en el sistema");
        }

    }

}
