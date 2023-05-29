package com.revature.app;

import com.revature.app.daos.UserDAO;
import com.revature.app.models.Role;
import com.revature.app.models.User;
import com.revature.app.services.RoleService;
import com.revature.app.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    // Mock the dependencies
    @Mock
    private UserDAO userDAO;
    @Mock
    private RoleService roleService;

    // Service class under test
    private UserService userService;

    @Before
    public void setUp() {

        // initialize the Mockito framework
        MockitoAnnotations.openMocks(this);

        // create an instance of the Service class under test
        userService = new UserService(userDAO, roleService);
    }

    @Test
    public void registerTest() {
        // define the test input values
        String username = "tester01";
        String password = "Passw0rd";
        Role role = new Role("ddb72ba6-7a52-4899-aa7d-4c332a8ffc6d", "USER");
        User user = new User(username, BCrypt.hashpw(password, BCrypt.gensalt()), role.getId());

        // mock the behavior of the roleService and userDao objects
        when(roleService.findByName("USER")).thenReturn(role);
        doNothing().when(userDAO).save(any(User.class));

        // Call the register method of the userService object with the test input values
        User result = userService.register(username, password);

        // Verify that the userDao.save method was called once with any User object an argument
        verify(userDAO, times(1)).save(any(User.class));

        // Verify the result object has the correct username
        assertEquals(username, result.getUsername());

    }

    @Test
    public void isValidUsernameTest() {
        String validUsername = "validUser";
        String invalidUsername = "";

        assertTrue(userService.isValidUsername(validUsername));
        assertFalse(userService.isValidUsername(invalidUsername));

    }

    @Test
    public void isUniqueUsernameTest() {
        String existingUsername = "existingUser";
        String newUser = "newUser";

        when(userDAO.findByUsername(existingUsername)).thenReturn(Optional.of(new User()));
        when(userDAO.findByUsername(newUser)).thenReturn(Optional.empty());

        // todo revisit logic
        assertFalse(userService.isValidUsername(newUser));
        assertTrue(userService.isValidUsername(existingUsername));

    }

    @Test
    public void isValidPasswordTest() {
        String validPassword = "validPassw0rd";
        String invalidPassword = "invalid";

        assertTrue(userService.isValidPassword(validPassword));
        assertFalse(userService.isValidPassword(invalidPassword));
    }

    @Test
    public void isSamePasswordTest() {
        String password = "Passw0rd";
        String confirmPassword = "Passw0rd";
        String differentPassword = "DifferentPass0rd";

        assertTrue(userService.isSamePassword(password, confirmPassword));
        assertFalse(userService.isSamePassword(password, differentPassword));

    }





}
