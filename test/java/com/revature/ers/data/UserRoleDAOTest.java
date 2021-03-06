package com.revature.ers.data;

import com.revature.ers.ServiceLocator;
import com.revature.ers.beans.UserRole;
import junit.framework.TestCase;

import java.sql.Connection;
import java.util.List;


public class UserRoleDAOTest extends TestCase {
    Connection connection;
    public void setUp() throws Exception {
        super.setUp();
        connection = ServiceLocator.getERSDatabase().getConnection();
    }

    public void tearDown() throws Exception {

    }

    public void testInsert() throws Exception {
        new UserRoleDAO(connection).insert(new UserRole(434,"Test role"));
    }

    public void testQueryAll() throws Exception {
        List<UserRole> userRoles = new UserRoleDAO(connection).queryAll();
        for (UserRole userRole:userRoles) {
            System.out.println(userRole);
        }
    }

    public void testQueryById() throws Exception {
        System.out.println(new UserRoleDAO(connection).queryById(6));
    }

    public void testGetId() throws Exception {
        System.out.println(new UserRoleDAO(connection).getId());
    }

}