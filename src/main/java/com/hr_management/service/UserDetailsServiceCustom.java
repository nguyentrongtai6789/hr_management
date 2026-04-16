package com.hr_management.service;

import com.hr_management.config.security.UserDetailsCustom;
import com.hr_management.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsCustom loadUserByUsername(String username) throws UsernameNotFoundException {
        var userData = userRepository.findFirstByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        Set<GrantedAuthority> roles = userData.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());
        return new UserDetailsCustom(userData.getId(), userData.getNhanSuId(), userData.getUserName(), userData.getPassword(), roles);
    }
}

