package com.revature.app.services;

import com.revature.app.daos.UserDAO;
import com.revature.app.models.Role;
import com.revature.app.models.User;
import com.revature.app.utils.custom_exceptions.InvalidCredentialException;
import com.revature.app.utils.custom_exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@AllArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final RoleService roleService;

    public User register(String username, String password) {
        // retrieve the role to assign the user being registered
        Role foundRole = roleService.findByName("USER");

        // encrypt the password
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        // instantiate a User object with the hashed password, and a valid role id
        User newUser = new User(username, hashed, foundRole.getId());

        // save / persist the user
        userDAO.save(newUser);

        // return the new user
        return newUser;
    }

    public User login(String username, String password) throws UserNotFoundException, InvalidCredentialException {
         User user = userDAO.findByUsername(username)
                 .orElseThrow(UserNotFoundException::new);

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new InvalidCredentialException();
        };

        return user;
    }

    public boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    public boolean isUniqueUsername(String username) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        return userOpt.isEmpty();
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    public boolean isSamePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

}
