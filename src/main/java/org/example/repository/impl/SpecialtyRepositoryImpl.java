package org.example.repository.impl;

import org.example.model.Specialty;
import org.example.model.Status;
import org.example.repository.SpecialtyRepository;
import org.example.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public List<Specialty> getAll() {
        List<Specialty> specialties = new ArrayList<>();
        String sql = "SELECT * FROM Specialty";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                specialties.add(mapRowToSpecialty(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }

    @Override
    public Specialty getById(Long id) {
        String sql = "SELECT * FROM Specialty WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapRowToSpecialty(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Specialty();
    }

    @Override
    public void save(Specialty specialty) {
        String sql = "INSERT INTO Specialty (name, status) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement
                     (sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, specialty.getName());
            stmt.setString(2, specialty.getStatus().name());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Новая специальность разработчику успешно добавлена");
            } else {
                System.out.println("Не удалось добавить новую специальность разработчику");
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        specialty.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
                 e.printStackTrace();
        }
    }

    @Override
    public void update(Specialty specialty) {
        String sql = "UPDATE Specialty SET name = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, specialty.getName());
            stmt.setString(2, specialty.getStatus().name());
            stmt.setLong(3, specialty.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Данные специальности разработчика успешно обновлены");
            } else {
                System.out.println("Не удалось обновить специальность разработчика");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Specialty WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Специальность разработчика успешно удалена");
            } else {
                System.out.println("Не удалось удалить специальность разработчика");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Specialty mapRowToSpecialty (ResultSet resultSet) throws SQLException {
        Specialty specialty = new Specialty();
        specialty.setId(resultSet.getLong("id"));
        specialty.setName(resultSet.getString("name"));
        specialty.setStatus(Status.valueOf(resultSet.getString("status")));
        return specialty;
    }
}
