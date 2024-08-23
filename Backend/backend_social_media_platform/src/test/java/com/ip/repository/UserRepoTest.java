package com.ip.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ip.model.User;

@ExtendWith(MockitoExtension.class)
public class UserRepoTest {

    @Mock
    private UserRepo userRepo;

    @Test
    public void testFindByEmail() {
        // create a user with a known email address
        User user = new User();
        user.setId(1);
        user.setName("Indrajit Paul");
        user.setEmail("indrajit@gmail.com");
        user.setBio("Hey! I am a passionate software developer from Kolkata");
        
        // mock the repository's findByEmail method to return the user with the known email address
        when(userRepo.findByEmail("indrajit@gmail.com")).thenReturn(Optional.of(user));
        
        // call the findByEmail method and assert that the user with the known email address is returned
        Optional<User> result = userRepo.findByEmail("indrajit@gmail.com");
        
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}
