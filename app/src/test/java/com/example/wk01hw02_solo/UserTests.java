package com.example.wk01hw02_solo;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class UserTests {
    @Test
    public void verifyUsername() {
        List<Users> testUsers = Users.getUsersList();

        // bad username
        assertEquals(null, Users.getUserbyUsername("baduser", testUsers));
        // good username
        assertEquals(testUsers.get(0), Users.getUserbyUsername("admin", testUsers));
    }

    @Test
    public void verifyPassword() {
        List<Users> testUsers = Users.getUsersList();
        Users user = Users.getUserbyUsername("admin", testUsers);

        // bad password
        assertEquals(false, Users.verifyPassword("badpass", user));
        // good password
        assertEquals(true, Users.verifyPassword("admin_pass", user));
    }
}
