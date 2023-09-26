package com.project.databaseproject;

import java.sql.*;

public class Database {
    private Connection connection;

    Database() {
        try {
            String url = "jdbc:mysql://localhost:3306/comp_440";
            String username = "USER";
            String password = "userlogin";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertUserInfo(String username, String password, String firstName, String lastName, String email) {
        try {
            String checkUsernameQuery = "SELECT COUNT(*) FROM user WHERE username = ?";
            PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery);
            checkUsernameStatement.setString(1, username);
            ResultSet usernameResultSet = checkUsernameStatement.executeQuery();

            if (usernameResultSet.next() && usernameResultSet.getInt(1) > 0) { // Username exists in the db
                /* The first condition selects the first row of the result set and if there is at least one row
                   in the result, then next() returns true.
                */
                /* The second condition returns the first columns value in the current row of the result set, which is the count of rows
                   where the username matches. If the count is greater than 0, it means that the username already exists in the database.
                */
                checkUsernameStatement.close();
                return 1;
            }
            checkUsernameStatement.close();


            String checkEmailQuery = "SELECT COUNT(*) FROM user WHERE email = ?";
            PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailQuery);
            checkEmailStatement.setString(1, email); // Sets the email to the first parameter which is the first question mark
            ResultSet emailResultSet = checkEmailStatement.executeQuery();

            if (emailResultSet.next() && emailResultSet.getInt(1) > 0) { // Email exists in the db
                checkEmailStatement.close();
                return 2;
            }
            checkEmailStatement.close();


            // Insert the new user if username and email are not duplicates
            String insertQuery = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, email);

            int updatedRows = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (updatedRows == 1) { // User was successfully registered
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 3; // One possible reason for an SQL statement not being able to successfully execute is because one
                  // of the statements exceeded the maximum number of characters for a given variable.
    }

}

/*
Uses paramertized statements to prevent things like
'b';
DELETE FROM user WHERE email = 'b';

so it treats this as an input to an email.
 */
