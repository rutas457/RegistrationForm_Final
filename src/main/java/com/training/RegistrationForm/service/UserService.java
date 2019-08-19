package com.training.RegistrationForm.service;

import com.training.RegistrationForm.dto.UserDTO;
import com.training.RegistrationForm.dto.UsersDTO;
import com.training.RegistrationForm.entity.Role;
import com.training.RegistrationForm.entity.User;
import com.training.RegistrationForm.repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Getter
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UsersDTO getAllUsers() {
        return new UsersDTO(userRepository.findAll());
    }

    public Optional<User> findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email);
    }

    public void saveNewUser(UserDTO userDTO) {
        try {
            User user = User.builder().email(userDTO.getEmail())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .enabled(true)
                    .firstName(userDTO.getName())
                    .lastName(userDTO.getSurname())
                    .roles(Arrays.asList(new Role("ROLE_USER"))).build();
            userRepository.save(user);
        } catch (Exception ex) {
            log.info("{Почтовый адрес уже существует}");
        }
    }

    public void updatePassword(UserDTO userDTO){
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("user " + userDTO.getEmail() + " was not found!"));
            userRepository.updatePasswordById(passwordEncoder.encode(userDTO.getPassword()), user.getId());
        }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        log.info("Hi" + username);
        User user = findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("user " + username + " was not found!"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}