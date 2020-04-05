package edu.upenn.cis350.cis350finalproject.data;

import android.util.Log;

import edu.upenn.cis350.cis350finalproject.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            boolean correctLogin = DataSource.isCorrectPassword(username, password);
            if (correctLogin) {
                LoggedInUser user =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(user);
            } else {
                return new Result.Error(new Exception("Incorrect username or password"));
            }

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
