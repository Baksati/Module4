package org.example.repository.impl;

import org.example.model.Developer;
import org.example.model.Status;
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
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                developers.add(mapRowToDeveloper(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

        @Override
        public Developer getById (Long id){
            String sql = "SELECT * FROM Developer WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return mapRowToDeveloper(resultSet);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return new Developer();
        }

        @Override
        public void save (Developer developer){
            String sql = "INSERT INTO Developer (firstName, lastName, status_id) VALUES (?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement
                         (sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, developer.getFirstName());
                stmt.setString(2, developer.getLastName());
                stmt.setString(3, developer.getStatus().name());
                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Новый разработчик успешно добавлен");
                } else {
                    System.out.println("Не удалось добавить нового разработчика");
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
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
        public void update (Developer developer) {
        String sql = "UPDATE Developer SET firstName = ?, lastName = ?, status_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, developer.getFirstName());
            stmt.setString(2, developer.getLastName());
            stmt.setString(3, developer.getStatus().name());
            stmt.setLong(4, developer.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Данные разработчика успешно обновлены");
            } else {
                System.out.println("Не удалось обновить данные разработчика");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        @Override
        public void delete (Long id) {
            String sql = "DELETE FROM Developer WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Разработчик успешно удален");
                } else {
                    System.out.println("Не удалось удалить разработчика");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private Developer mapRowToDeveloper (ResultSet resultSet) throws SQLException {
            Developer developer = new Developer();
            developer.setId(resultSet.getLong("id"));
            developer.setFirstName(resultSet.getString("firstName"));
            developer.setLastName(resultSet.getString("lastName"));
            developer.setStatus(Status.valueOf(resultSet.getString("status_id")));
            return developer;
        }
    }
