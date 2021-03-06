package com.revature.ers.data;

import com.revature.ers.beans.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class ReimbursementDAO implements DAO<Reimbursement> {
    private Connection connection;

    ReimbursementDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Reimbursement object) throws SQLException {
        String sql = "INSERT INTO " + constants.getString("reimbursementTableName") +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, getId());
        preparedStatement.setDouble(2, object.getReimbAmount());
        preparedStatement.setTimestamp(3, object.getReimbSubmitted());
        preparedStatement.setTimestamp(4, object.getReimbResolved());
        preparedStatement.setString(5, object.getReimbDescription());
        preparedStatement.setBlob(6, object.getReimbReceipt());
        preparedStatement.setInt(7, object.getReimbAuthor().getId());
        if (object.getReimbResolver() != null)
            preparedStatement.setInt(8, object.getReimbResolver().getId());
        preparedStatement.setNull(8, Types.VARCHAR);
        preparedStatement.setInt(9, object.getReimbStatus().getId());
        preparedStatement.setInt(10, object.getReimbType().getId());
        preparedStatement.executeUpdate();
    }

    public List<Reimbursement> queryAll() throws SQLException {
        List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
        PreparedStatement preparedStatement = connection.prepareStatement(getAllReimbursements);
        ResultSet resultSet = preparedStatement.executeQuery();
        mapRows(resultSet, reimbursementList);
        return reimbursementList;
    }

    /**
     * Method for querying Reimbursements by author or resolver
     *
     * @param user User object
     * @param type 1-Author 2-Resolver
     * @return List of Reimbursements for that Author/Resolver
     * @throws SQLException
     */
    public List<Reimbursement> queryByUser(User user, int type) throws SQLException {
        List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
        String sql;
        switch (type) {
            case 1:
                sql = getAllReimbursements + " WHERE " + reimbursementAuthor + "=" + user.getId();
                break;
            case 2:
                sql = getAllReimbursements + " WHERE " + reimbursementResolver + "=" + user.getId();
                break;
            default:
                throw new SQLException();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        mapRows(resultSet, reimbursementList);
        return reimbursementList;
    }

    public void approve(Reimbursement reimbursement, boolean approve) throws SQLException {
        //TODO finish this to update other fields
        String sql;
        if (approve)
            sql = "UPDATE " + reimbursementTableName +
                    " SET " + reimbStatusId + "= 1, " +
                    reimbursementResolved+"= ?, " +
                    reimbursementResolver+"= ? " +
                    " WHERE " + reimbursementId + "= ?";
        else sql = "UPDATE " + reimbursementTableName +
                " SET " + reimbStatusId + "= 3, " +
                        reimbursementResolved+"= ?, " +
                        reimbursementResolver+"= ? " +
                " WHERE " + reimbursementId + "= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, reimbursement.getReimbResolved());
        preparedStatement.setInt(2, reimbursement.getReimbResolver().getId());
        preparedStatement.setInt(3, reimbursement.getId());
        preparedStatement.executeUpdate();
    }

    public List<Reimbursement> queryPending() throws SQLException {
        List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
        String sql = getAllReimbursements + " WHERE reimbursement." + reimbStatusId + " = 2";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        mapRows(resultSet, reimbursementList);
        return reimbursementList;
    }

    public Reimbursement queryById(int id) throws SQLException {
        List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
        String sql = getAllReimbursements + " WHERE " + reimbursementId + "= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        mapRows(resultSet, reimbursementList);
        return reimbursementList.get(0);
    }

    public int getId() throws SQLException {
        String sql = "SELECT MAX(" + reimbursementId + ") FROM " + reimbursementTableName;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) return resultSet.getInt(1) + 1;
        else throw new SQLException();
    }

    public void mapRows(ResultSet resultSet, List<Reimbursement> list) throws SQLException {
        while (resultSet.next()) {
            Reimbursement reimbursement = new Reimbursement(
                    resultSet.getInt(reimbursementId),
                    resultSet.getDouble(reimbursementAmount),
                    resultSet.getTimestamp(reimbursementSubmitted),
                    resultSet.getTimestamp(reimbursementResolved),
                    resultSet.getString(reimbursementDescription),
                    resultSet.getBinaryStream(reimbursementReceipt),
                    new User(
                            resultSet.getInt("author_id"),
                            resultSet.getString("author_un"),
                            resultSet.getString("author_pw"),
                            resultSet.getString("author_fn"),
                            resultSet.getString("author_ln"),
                            resultSet.getString("author_em"),
                            new UserRole(
                                    resultSet.getInt("auth_role_id"),
                                    resultSet.getString("auth_role")
                            )
                    ),
                    new User(
                            resultSet.getInt("resolver_id"),
                            resultSet.getString("resolver_un"),
                            resultSet.getString("resolver_pw"),
                            resultSet.getString("resolver_fn"),
                            resultSet.getString("resolver_ln"),
                            resultSet.getString("resolver_em"),
                            new UserRole(
                                    resultSet.getInt("res_role_id"),
                                    resultSet.getString("res_role")
                            )
                    ),
                    new ReimbursementStatus(
                            resultSet.getInt("status_id"),
                            resultSet.getString("status")
                    ),
                    new ReimbursementType(
                            resultSet.getInt("type_id"),
                            resultSet.getString("type")
                    )
            );
            list.add(reimbursement);
        }
    }

    public List<Reimbursement> queryAccepted(User user) throws SQLException {
        List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
        String sql = getAllReimbursements + " WHERE reimbursement." + reimbStatusId + " = 1 " +
                "AND " + reimbursementResolver + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        mapRows(resultSet, reimbursementList);
        return reimbursementList;
    }

    public List<Reimbursement> queryDeclined(User user) throws SQLException {
        List<Reimbursement> reimbursementList = new ArrayList<Reimbursement>();
        String sql = getAllReimbursements + " WHERE reimbursement." + reimbStatusId + " = 3 " +
                "AND " + reimbursementResolver + " = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        mapRows(resultSet, reimbursementList);
        return reimbursementList;
    }
}
