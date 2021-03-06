package com.revature.ers.data;

import com.revature.ers.ServiceLocator;
import com.revature.ers.beans.ReimbursementType;
import junit.framework.TestCase;

import java.sql.Connection;
import java.util.List;


public class ReimbursementTypeDAOTest extends TestCase {
    Connection connection;
    public void setUp() throws Exception {
        super.setUp();
        connection = ServiceLocator.getERSDatabase().getConnection();

    }

    public void tearDown() throws Exception {

    }

    public void testInsert() throws Exception {
        new ReimbursementTypeDAO(connection).insert(new ReimbursementType(434,"Test type"));

    }

    public void testQueryAll() throws Exception {
        List<ReimbursementType> reimbursementTypes = new ReimbursementTypeDAO(connection).queryAll();
        for (ReimbursementType reimbursementType:reimbursementTypes) {
            System.out.println(reimbursementType);
        }
    }

    public void testQueryById() throws Exception {
        System.out.println(new ReimbursementTypeDAO(connection).queryById(3));
    }

    public void testGetId() throws Exception {
        System.out.println(new ReimbursementTypeDAO(connection).getId());
    }

}