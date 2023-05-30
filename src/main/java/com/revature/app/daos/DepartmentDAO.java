package com.revature.app.daos;

import com.revature.app.models.Department;
import com.revature.app.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentDAO implements ICrudDAO<Department> {

    @Override
    public void save(Department department) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Department department) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Optional<Department> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    // TODO implement this method to retrieve all the departments
    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM DEPARTMENTS";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Department dept = new Department(
                                rs.getString("id"),
                                rs.getString("name")
                        );
                        departments.add(dept);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to the database", e);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load JDBC driver", e);
        }

        return departments;
    }

}
