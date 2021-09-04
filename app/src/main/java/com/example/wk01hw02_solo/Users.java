package com.example.wk01hw02_solo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private String username;
    private String password;
    private int userID;

    private static final List<Users> usersList = new ArrayList<>();

    private static void dummy_accounts() {
        usersList.add(new Users("admin", "admin_pass"));
        usersList.add(new Users("user1", "password1"));
        usersList.add(new Users("user2", "password2"));
        usersList.add(new Users("user3", "password3"));
        usersList.add(new Users("user4", "password4"));
        usersList.add(new Users("user5", "password5"));
        usersList.add(new Users("user6", "password6"));
        usersList.add(new Users("user7", "password7"));
        usersList.add(new Users("user8", "password8"));
        usersList.add(new Users("user9", "password9"));
    }

    public static List<Users> getUsersList() {
        if(usersList.isEmpty()) {
            dummy_accounts();
        }

        return usersList;
    }

    private static int id_iter = 1;
    public Users(String username, String password) {
        this.userID = id_iter++;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static Users getUserbyUsername(String username, List<Users> usersList) {
        for(Users user: usersList) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static boolean verifyPassword(String pass, Users user) {
        if(!user.getPassword().equals(pass)) {
            return false;
        }
        return true;
    }
}
