package com.revature.app.daos;

import com.revature.app.models.Role;
import com.revature.app.utils.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoleDAO implements ICrudDAO<Role> {

    @Override
    public void save(Role obj) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Role role) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Optional<Role> findById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Role> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public Optional<Role> findByName(String name) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM roles WHERE name = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Role role = new Role();
                        role.setId(rs.getString("id"));
                        role.setName(rs.getString("name"));
                        return Optional.of(role);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find application.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }

        return Optional.empty();
    }
}
