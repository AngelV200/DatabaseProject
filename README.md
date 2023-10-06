# DatabaseProject

Angel:
I was in charge of making the database so that it handles all of the logic within he program. It adds users to the table and can login based on what is on the table. I also created a user connection that restricts some privledges.
The database handles any duplicates and alerts the user if a username already exists. It also handles the case so that users are not allowed to attack the dagtabase through sql injection.

Josue:
I was responsible for making the register menu. It allows a user to sign up with email, username, first name, last name, and password. It will not let a user sign up if the user does not fill in all of the fields. It also uses the database connection instance
so that duplicate users are not registered.

Troy:
I made the login menu which uses alerts when a user has successfully or wrongly done something. If a user logins successfully, it brings the user back to the register menu. The user must fill in all of the fields in order to login. It uses a database connection
to see if a username exists and if a password is associated with it.
