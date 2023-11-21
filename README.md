# DatabaseProject Phase 1

Angel:
I was in charge of making the database so that it handles all of the logic within he program. It adds users to the table and can login based on what is on the table. I also created a user connection that restricts some privledges.
The database handles any duplicates and alerts the user if a username already exists. It also handles the case so that users are not allowed to attack the dagtabase through sql injection.

Josue:
I was responsible for making the register menu. It allows a user to sign up with email, username, first name, last name, and password. It will not let a user sign up if the user does not fill in all of the fields. It also uses the database connection instance
so that duplicate users are not registered.

Troy:
I made the login menu which uses alerts when a user has successfully or wrongly done something. If a user logins successfully, it brings the user back to the register menu. The user must fill in all of the fields in order to login. It uses a database connection
to see if a username exists and if a password is associated with it.


Youtube Video:
[https://www.youtube.com/watch?v=kUIstlY_u4s](https://www.youtube.com/watch?v=LHu9YkPq2UU)https://www.youtube.com/watch?v=LHu9YkPq2UU


# DatabaseProject Phase 2

Angel:
I made the interface that is able to retrieve items by catgory and so that a user who logged in can insert an item into the tables.

Josue:
I was responsible for allowing the users to review an item if an item was purchased by a user.

Troy:
I made the initializeDatabase method for the menu which populates information in each table with 5 tuples.


All:
We all worked on the database class whuch is the backend of this project which runs querys to make this projject possible. Everything is handled through the mySQL.

Youtube Video:
https://youtu.be/fs2OXgXI2-s


Angel:
I did the first 4 parts of the project.
1. List the most expensive items in each category. 
2. List the users who posted at least two items that were posted on the same day, one has a category 
of X, and another has a category of Y. In terms of the user interface, you will implement two 
text fields so that you can input one category into each text field, and the search will return the 
user (or users) who (the same user) posted two different items on the same day, such that one 
item has a category in the first text field and the other has a category in the second text field.
3. List all the items posted by user X, such that all the comments are "Excellent" or "good" for 
these items (in other words, these items must have comments, but these items don't have any 
other kinds of comments, such as "bad" or "fair" comments). User X is arbitrary and will be 
determined by the instructor. 
4. List the users who posted the most number of items on a specific date like 5/1/2023; if there is 
a tie, list all the users who have a tie. The specific date can be hard coded into your SQL select 
query or given by the user. 


Josue:
I was responsible for the next 3 parts of the project.
5. List the other users who are favorited by both users X, and Y. Usernames X and Y will be 
selected from dropdown menus by the instructor. In other words, the user (or users) C are the 
favorite for both X and Y. 
6. Display all the users who never posted any "excellent" items: an item is excellent if at least 
three reviews are excellent. 
7. Display all the users who never posted a "poor" review. 


Troy:
I made the last three parts of th project.
8. Display all the users who posted some reviews, but each of them is "poor". 
9. Display those users such that each item they posted so far never received any "poor" reviews. 
In other words, these users must have posted some items; however, these items have never 
received any poor reviews or have not received any reviews at all.
10. List a user pair (A, B) such that they always gave each other "excellent" reviews for every single 
item they posted.

All:
We all worked on the database class whuch is the backend of this project which runs querys to make this projject possible. Everything is handled through the mySQL. We also refactoredthe code and added comments to make it
more readable.
# DatabaseProject Phase 2
