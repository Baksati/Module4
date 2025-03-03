package org.example.repository.impl;

import org.example.model.Developer;
import org.example.repository.DeveloperRepository;
import org.example.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public List<Developer> getAll() {
        List<Developer> developers = new ArrayList<>();
        String sql = "SELECT * FROM Developer";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                developers.add(mapRowToDeveloper(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    @Override
    public Developer getById(Long id) {
        String sql = "SELECT * FROM Developer WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapRowToDeveloper(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Developer();
    }

    @Override
    public void save(Developer developer) {
        String sql = "INSERT INTO Developer (firstName, lastName) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement
                (sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        developer.setId(generatedKeys.getLong(1));
                    }
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Developer developer) {

    }

    @Override
    public void delete(Long id) {

    }
    private Developer mapRowToDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer = new Developer();
        developer.setId(resultSet.getLong("id"));
        developer.setFirstName(resultSet.getString("firstName"));
        developer.setLastName(resultSet.getString("lastName"));
        return developer;
    }
}
