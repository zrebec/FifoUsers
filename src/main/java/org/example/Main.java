package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // database init
            DatabaseInitializer.initalize();
            DatabaseManager dbManager = new DatabaseManager();

            // add users
            dbManager.addUser(1, "a1", "Robert");
            dbManager.addUser(2, "a2", "Martin");
            dbManager.addUser(3, "a3", "Joseph");

            // print existing user (Joseph)
            SUser user = dbManager.getUser(3);
            if (user != null) {
                System.out.println("You selected user with ID " + user.getUserId() + " and name " + user.getUserName());
            }

            // delete user Joseph and try print it again
            dbManager.deleteUser(3);
            user = dbManager.getUser(3);
            if(user == null) {
                System.out.println("The user doesn't exists");
            }

            // print all existing users
            dbManager.printAll();

            // delete all users
            dbManager.deleteAll();

            // print all remaining users
            dbManager.printAll();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}