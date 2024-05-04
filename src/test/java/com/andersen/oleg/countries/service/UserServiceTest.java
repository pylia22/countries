package com.andersen.oleg.countries.service;

import com.andersen.oleg.countries.entity.User;
import com.andersen.oleg.countries.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    private static final String USER_NAME = "user";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void whenUserByNameFoundThenReturnUserDetails() {
        User expectedUser = new User();
        expectedUser.setName(USER_NAME);
        Optional<User> optionalUser = Optional.of(expectedUser);

        when(userRepository.findByName(USER_NAME)).thenReturn(optionalUser);

        UserDetails userDetails = userService.loadUserByUsername(USER_NAME);

        assertEquals(expectedUser.getName(), userDetails.getUsername());
    }

    @Test
    void whenUserByNameNotFoundThenThrow() {
        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USER_NAME));
    }
}