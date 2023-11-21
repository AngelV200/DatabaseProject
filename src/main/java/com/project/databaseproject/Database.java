package com.project.databaseproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {
    private Connection connection;

    Database() {
        try {
            String url = "jdbc:mysql://localhost:3306/comp_440";
            String username = "USER";
            String password = "userlogin";
            connection = DriverManager.getConnection(url, username, password); // Estalishes a connection to the database using the credentials above.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertUserInfo(String username, String password, String firstName, String lastName, String email) {
        try {
            String checkUsernameQuery = "SELECT COUNT(*) FROM user WHERE username = ?"; // A query to check if a username exists
            PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery); // Establishes the query to the connection
            checkUsernameStatement.setString(1, username); // Sets the username into the 1st question mark
            ResultSet usernameResultSet = checkUsernameStatement.executeQuery(); // Returns the data table produced by the query

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


            String checkEmailQuery = "SELECT COUNT(*) FROM user WHERE email = ?"; // counts the emails
            PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailQuery);
            checkEmailStatement.setString(1, email); // Sets the email to the first parameter which is the first question mark
            ResultSet emailResultSet = checkEmailStatement.executeQuery(); // Executes the query

            if (emailResultSet.next() && emailResultSet.getInt(1) > 0) { // Email exists in the db
                checkEmailStatement.close();
                return 2;
            }
            checkEmailStatement.close(); // Does the same thing as above but with an email


            // Insert the new user if username and email are not duplicates
            String insertQuery = "INSERT INTO user (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)"; // QUERY that inserts these values into the question marks, user is the name of the table that holds these values
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username); // sets it to 1st question mark
            preparedStatement.setString(2, password); // sets it to 2nd question mark
            preparedStatement.setString(3, firstName); // sets it to 3rd question mark... etc...
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, email);

            int updatedRows = preparedStatement.executeUpdate(); // Updates the inserted values
            preparedStatement.close();

            if (updatedRows == 1) { // User was successfully registered
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // One possible reason for an SQL statement not being able to successfully execute is because one
                  // of the statements exceeded the maximum number of characters for a given variable.
    }

    public int login(String username, String password) {
        String checkUsernameQuery = "SELECT COUNT(*) FROM user WHERE username = ?";
        String checkPasswordQuery = "SELECT password FROM user WHERE username = ?";

        try {
            PreparedStatement usernameStatement = connection.prepareStatement(checkUsernameQuery);
            usernameStatement.setString(1, username);
            ResultSet usernameResult = usernameStatement.executeQuery();

            if (usernameResult.next()) {
                int usernameCount = usernameResult.getInt(1);

                if (usernameCount > 0) {
                    PreparedStatement passwordStatement = connection.prepareStatement(checkPasswordQuery);
                    passwordStatement.setString(1, username);
                    ResultSet passwordResult = passwordStatement.executeQuery();

                    if (passwordResult.next()) {
                        String storedPassword = passwordResult.getString("password");

                        if (storedPassword.equals(password)) {
                            return 50;
                        } else {
                            return 2;
                        }
                    }
                } else {
                    return 1;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int insertItem(String title, String description, String category, int price, String username) {
        // Add some logic to check if the user has posted 3 items today


        String insertQuery = "INSERT INTO item (title, description, category, price, user_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, price);
            preparedStatement.setString(5, username);
            int updatedRows = preparedStatement.executeUpdate();
            preparedStatement.close();

            if (updatedRows == 1) // Item was successfully inserted
                return 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Some error occurred
    }

    public List<String> getAllItemsByCategory(String category) {
        List<String> itemList = new ArrayList<>();
        String selectQuery = "SELECT title, description, category, price FROM item WHERE category = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, category); // Set the selected category as a parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");

                String itemString = title + "-" + description + "-" + price;
                itemList.add(itemString);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }


    public void insertReview(Date reportDate, String rating, String description, int itemId, String userId) {
        try {
            String insertReviewSQL = "INSERT INTO review (report_date, rating, description, item_id, user_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertReviewSQL)) {
                preparedStatement.setDate(1, reportDate);
                preparedStatement.setString(2, rating);
                preparedStatement.setString(3, description);
                preparedStatement.setInt(4, itemId);
                preparedStatement.setString(5, userId);

                System.out.println(reportDate + "-" + rating + "-" + description + "-" + itemId + "-" + userId);

                preparedStatement.executeUpdate();
                System.out.println("Review inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting review: " + e.getMessage());
        }
    }


    public int getItemIdByAttributes(String title, String description, String category, int price, String userId) {
        int itemId = -1;

        String query = "SELECT item_id FROM item WHERE title = ? AND description = ? AND category = ? AND price = ? AND user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, category);
            preparedStatement.setInt(4, price);
            preparedStatement.setString(5, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                itemId = resultSet.getInt("item_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemId;
    }


    public void drop() {
        try {
            // Drop the 'favorites' table if it exists and remove foreign key constraints
            String dropTableFavoritesSQL = "DROP TABLE IF EXISTS favorites";
            Statement dropFavoritesStatement = connection.createStatement();
            dropFavoritesStatement.execute(dropTableFavoritesSQL);
            dropFavoritesStatement.close();

            // Drop the 'review' table if it exists and remove foreign key constraints
            String dropTableReviewSQL = "DROP TABLE IF EXISTS review";
            Statement dropTableReviewStatement = connection.createStatement();
            dropTableReviewStatement.execute(dropTableReviewSQL);
            dropTableReviewStatement.close();

            // Drop the 'item' table if it exists and remove foreign key constraint
            String dropTableItemSQL = "DROP TABLE IF EXISTS item";
            Statement dropItemStatement = connection.createStatement();
            dropItemStatement.execute(dropTableItemSQL);
            dropItemStatement.close();

            // Drop the 'user' table if it exists
            String dropTableUserSQL = "DROP TABLE IF EXISTS user";
            Statement dropUserStatement = connection.createStatement();
            dropUserStatement.execute(dropTableUserSQL);
            dropUserStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void initializeDatabase() {
        try {
            drop();

            // Create the 'user' table
            String createTableUserSQL = "CREATE TABLE user ("
                    + "username VARCHAR(255) NOT NULL PRIMARY KEY,"
                    + "password VARCHAR(255),"
                    + "firstName VARCHAR(255),"
                    + "lastName VARCHAR(255),"
                    + "email VARCHAR(255) UNIQUE"
                    + ")";
            Statement createUserStatement = connection.createStatement();
            createUserStatement.execute(createTableUserSQL);
            createUserStatement.close();

            // Recreate the 'item' table with the foreign key constraint
            String createTableItemSQL = "CREATE TABLE item ("
                    + "item_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "title VARCHAR(255) NOT NULL,"
                    + "description TEXT,"
                    + "category VARCHAR(255),"
                    + "price INT,"
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "posted_date DATE NOT NULL,"
                    + "FOREIGN KEY (user_id) REFERENCES user(username)"
                    + ")";
            Statement createItemStatement = connection.createStatement();
            createItemStatement.execute(createTableItemSQL);
            createItemStatement.close();

            // Recreate the 'review' table with the foreign key constraint
            String createTableReviewSQL = "CREATE TABLE review ("
                    + "review_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "report_date DATE,"
                    + "rating ENUM('excellent', 'good', 'fair', 'poor'),"
                    + "description TEXT,"
                    + "item_id INT,"
                    + "user_id VARCHAR(255),"
                    + "FOREIGN KEY (item_id) REFERENCES item(item_id),"
                    + "FOREIGN KEY (user_id) REFERENCES user(username)"
                    + ")";
            Statement createReviewStatement = connection.createStatement();
            createReviewStatement.execute(createTableReviewSQL);
            createReviewStatement.close();

            // Create the 'favorites' table
            String createTableFavoritesSQL = "CREATE TABLE favorites ("
                    + "favorite_id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "user_id_x VARCHAR(255) NOT NULL,"
                    + "user_id_y VARCHAR(255) NOT NULL,"
                    + "favorite_user_id VARCHAR(255) NOT NULL,"
                    + "FOREIGN KEY (user_id_x) REFERENCES user(username),"
                    + "FOREIGN KEY (user_id_y) REFERENCES user(username),"
                    + "FOREIGN KEY (favorite_user_id) REFERENCES user(username),"
                    + "UNIQUE KEY unique_favorite (user_id_x, user_id_y, favorite_user_id)"
                    + ")";
            Statement createFavoritesStatement = connection.createStatement();
            createFavoritesStatement.execute(createTableFavoritesSQL);
            createFavoritesStatement.close();

            // Insert sample data into the tables
            insertSampleUserData(20);
            insertSampleItemData(20);
            insertSampleReviewData(20);
            insertSampleFavoritesData(20);

            System.out.println("Database initialized.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSampleFavoritesData(int numTuples) throws SQLException {
        int maxUserId = numTuples;

        for (int i = 1; i <= numTuples; i++) {
            String userX = "user" + (i % maxUserId + 1);
            String userY = "user" + ((i + 1) % maxUserId + 1);
            String favoriteUser = "user" + ((i + 2) % maxUserId + 1);

            String insertFavoritesSQL = "INSERT INTO favorites (user_id_x, user_id_y, favorite_user_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertFavoritesSQL)) {
                preparedStatement.setString(1, userX);
                preparedStatement.setString(2, userY);
                preparedStatement.setString(3, favoriteUser);
                preparedStatement.executeUpdate();
            }
        }
    }


    private void insertSampleUserData(int numTuples) {
        StringBuilder insertUserSQL = new StringBuilder("INSERT INTO user (username, password, firstName, lastName, email) VALUES ");

        for (int i = 1; i <= numTuples; i++) {
            insertUserSQL.append(String.format(
                    "('user%d', 'password%d', 'FirstName%d', 'LastName%d', 'user%d@example.com')",
                    i, i, i, i, i
            ));

            if (i < numTuples) {
                insertUserSQL.append(", ");
            }
        }

        executeSQLStatement(insertUserSQL.toString());
    }


    private void insertSampleItemData(int numTuples) {
        StringBuilder insertItemSQL = new StringBuilder("INSERT INTO item (title, description, category, price, user_id, posted_date) VALUES ");

        Random random = new Random();

        for (int i = 1; i <= numTuples; i++) {
            // Ensure that the category is within the range 1-5
            int category = (i % 5) + 1;
            // Ensure that the day is within the range 1-3
            int day = (i % 3) + 1;

            // Create a pattern for the price every 3 iterations
            int price = ((i - 1) / 3) * 100 + 100;

            // Use the same user for each item
            String userId = "user" + i;

            // Introduce randomness for creating a duplicate entry
            boolean createDuplicate = random.nextBoolean();

            insertItemSQL.append(String.format(
                    "('Item%d', 'Description%d', 'Category%d', %d, '%s', '2023-11-%02d')",
                    i, i, category, price, userId, day
            ));

            if (createDuplicate && i < numTuples) {
                // Add a duplicate entry for the same user on the same day
                insertItemSQL.append(", ");
                insertItemSQL.append(String.format(
                        "('Item%d', 'Description%d_duplicate', 'Category%d', %d, '%s', '2023-11-%02d')",
                        i, i, category, price, userId, day
                ));
            }

            if (i < numTuples) {
                insertItemSQL.append(", ");
            }
        }

        executeSQLStatement(insertItemSQL.toString());
    }



    private void insertSampleReviewData(int numTuples) {
        StringBuilder insertReviewSQL = new StringBuilder("INSERT INTO review (report_date, rating, description, item_id, user_id) VALUES ");

        for (int i = 1; i <= numTuples; i++) {
            insertReviewSQL.append(String.format(
                    "('2023-11-%02d', '%s', 'Review%d', %d, 'user%d')",
                    i, (i % 4 == 0) ? "poor" : ((i % 4 == 1) ? "excellent" : ((i % 4 == 2) ? "good" : "fair")), i, i, i
            ));

            if (i < numTuples) {
                insertReviewSQL.append(", ");
            }
        }

        executeSQLStatement(insertReviewSQL.toString());
    }



    // Part3
    // One
    // Method to retrieve the most expensive items in each category as a List<String>
    /*
    Grouping items by category, calculating the max price for each category, and returning all items under each category that have the maximum price.
    */
    public List<String> getMostExpensiveItemsByCategory() {
        List<String> mostExpensiveItems = new ArrayList<>();

        try {
            String sql = "SELECT category, MAX(price) AS max_price "
                    + "FROM item "
                    + "GROUP BY category";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String category = resultSet.getString("category");
                    int maxPrice = resultSet.getInt("max_price");

                    // Retrieve all items under the category with the max price
                    List<String> categoryItems = getCategoryItemsWithMaxPrice(category, maxPrice);

                    // Append the category and its items to the result
                    StringBuilder categoryInfo = new StringBuilder();
                    categoryInfo.append("Category: ").append(category).append(", ");
                    categoryInfo.append("Max Price: ").append(maxPrice).append(", ");
                    categoryInfo.append("Items: ").append(String.join(", ", categoryItems));
                    mostExpensiveItems.add(categoryInfo.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mostExpensiveItems;
    }

    // Helper method to retrieve items in a specific category with the max price
    private List<String> getCategoryItemsWithMaxPrice(String category, int maxPrice) {
        List<String> categoryItems = new ArrayList<>();

        try {
            String sql = "SELECT * FROM item WHERE category = ? AND price = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, category);
                preparedStatement.setInt(2, maxPrice);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Build the item information and add it to the list
                        StringBuilder itemInfo = new StringBuilder();
                        itemInfo.append("Title: ").append(resultSet.getString("title")).append(", ");
                        itemInfo.append("Description: ").append(resultSet.getString("description")).append(", ");
                        itemInfo.append("Price: ").append(resultSet.getInt("price")).append(", ");
                        itemInfo.append("User ID: ").append(resultSet.getString("user_id"));
                        categoryItems.add(itemInfo.toString());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryItems;
    }

    // Two
    // Method to retrieve users who posted at least two items on the same day with specified categories
    public List<String> getUsersWithMultipleItemsOnSameDay(String categoryX, String categoryY) {
        List<String> usersWithMultipleItems = new ArrayList<>();

        try {
            String sql = "SELECT user_id "
                    + "FROM item "
                    + "WHERE (category = ? OR category = ?) "
                    + "GROUP BY user_id, DATE_FORMAT(posted_date, '%Y-%m-%d') "
                    + "HAVING COUNT(DISTINCT category) = 2";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, categoryX);
                preparedStatement.setString(2, categoryY);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String userId = resultSet.getString("user_id");
                        usersWithMultipleItems.add(userId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersWithMultipleItems;
    }

    // Part 3 End


    private void executeSQLStatement(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

/*
Uses paramertized statements to prevent things like
'b';
DELETE FROM user WHERE email = 'b';

so it treats this as an input to an email.
 */


/***

 Set Up mySql With App

 1.) Create a database connection with root user using local host
     2) On root, make this table so just paste in on a query and run it
     -- Create the "comp_440" schema
     CREATE SCHEMA `comp_440`;

     -- Use the "comp_440" schema
     USE `comp_440`;

     -- Create a table for user information
     CREATE TABLE user (
         username VARCHAR(255) NOT NULL PRIMARY KEY,
         password VARCHAR(255),
         firstName VARCHAR(255),
         lastName VARCHAR(255),
         email VARCHAR(255) UNIQUE
     );

     3) On root, create a user and grant it all priveledges:
     -- Create a user 'USER' identified by 'userlogin'
     CREATE USER 'user'@'localhost' IDENTIFIED BY 'userlogin';

     -- Grant all privileges on the 'Arcade Warp Zone' schema to 'awz'
     GRANT ALL PRIVILEGES ON `comp_440`.* TO 'user'@'localhost';

     -- Reload the privileges
     FLUSH PRIVILEGES;

    4) Logout of root and login to user using userlogin

 5) Have these tables:
     CREATE TABLE user (
         username VARCHAR(255) NOT NULL PRIMARY KEY,
         password VARCHAR(255),
         firstName VARCHAR(255),
         lastName VARCHAR(255),
         email VARCHAR(255) UNIQUE
     );

     CREATE TABLE item (
         item_id INT AUTO_INCREMENT PRIMARY KEY,
         title VARCHAR(255) NOT NULL,
         description TEXT,
         category VARCHAR(255),
         price INT,
         user_id VARCHAR(255) NOT NULL,
         posted_date DATE NOT NULL,  -- Add the posted_date column
         FOREIGN KEY (user_id) REFERENCES user(username)
     );


     CREATE TABLE review (
         review_id INT AUTO_INCREMENT PRIMARY KEY,
         report_date DATE, -- Use DATE data type for report date
         rating ENUM('excellent', 'good', 'fair', 'poor'), -- Rating should be ENUM
         description TEXT,
         item_id INT,
         user_id VARCHAR(255),
         FOREIGN KEY (item_id) REFERENCES item(item_id),
         FOREIGN KEY (user_id) REFERENCES user(username)
     );

     CREATE TABLE favorites (
         favorite_id INT AUTO_INCREMENT PRIMARY KEY,
         user_id_x VARCHAR(255) NOT NULL,
         user_id_y VARCHAR(255) NOT NULL,
         favorite_user_id VARCHAR(255) NOT NULL,
         FOREIGN KEY (user_id_x) REFERENCES user(username),
         FOREIGN KEY (user_id_y) REFERENCES user(username),
         FOREIGN KEY (favorite_user_id) REFERENCES user(username),
         UNIQUE KEY unique_favorite (user_id_x, user_id_y, favorite_user_id)
     );


 6) Populated items:


 ***/
