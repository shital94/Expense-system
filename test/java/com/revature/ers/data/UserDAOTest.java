package com.revature.ers.data;

import com.revature.ers.ServiceLocator;
import com.revature.ers.beans.User;
import com.revature.ers.beans.UserRole;
import junit.framework.TestCase;

import java.sql.Connection;
import java.util.List;


public class UserDAOTest extends TestCase {
    Connection connection;
    public void setUp() throws Exception {
        super.setUp();
        connection = ServiceLocator.getERSDatabase().getConnection();
    }

    public void tearDown() throws Exception {

    }

    public void testInsert() throws Exception {
        UserRole role = new UserRole(4,"TestRole");
        User user = new User(23,"mnikolovski","skdjfsdf","Martino","Nikolovski","mnikolovski@testm.com",role);
        new UserDAO(connection).insert(user);
    }

    public void testQueryAll() throws Exception {
        List<User> users = new UserDAO(connection).queryAll();
        for (User user:users) {
            System.out.println(user);
        }
    }

    public void testQueryByUsernameOrEmail() throws Exception {
        System.out.println(new UserDAO(connection).queryByUsername("bclarkm"));
        System.out.println(new UserDAO(connection).queryByEmail("mnikolovski@testm.com"));
    }

    public void testQueryById() throws Exception {
        System.out.println(new UserDAO(connection).queryById(6));
        System.out.println(new UserDAO(connection).queryById(15));
    }

    public void testGetId() throws Exception {
        System.out.println(new UserDAO(connection).getId());
    }

}