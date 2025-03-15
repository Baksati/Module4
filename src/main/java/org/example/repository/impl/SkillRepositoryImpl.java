package org.example.repository.impl;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {

    @Override
    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM Skill";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) skills.add(mapRowToSkill(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public Skill getById(Long id) {
        String sql = "SELECT * FROM Skill WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return mapRowToSkill(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Skill();
    }

    @Override
    public void save(Skill skill) {
        String sql = "INSERT INTO Skill (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement
                     (sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, skill.getName());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Новый навык разработчику успешно добавлен");
            } else {
                System.out.println("Не удалось добавить новый навык разработчику");
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        skill.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Skill skill) {
        String sql = "UPDATE Skill SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
                 stmt.setString(1, skill.getName());
                 stmt.setLong(2, skill.getId());
                 int rowsUpdated = stmt.executeUpdate();
                 if (rowsUpdated > 0) {
                     System.out.println("Данные навыка разработчика успешно облновлены");
                 } else {
                     System.out.println("Не удалось обновить навык разработчика");
                 }
        } catch (SQLException e) {
                 e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Skill WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Навык разработчика успешно удален");
            } else {
                System.out.println("Не удалось удалить навык разработчика");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Skill mapRowToSkill (ResultSet resultSet) throws SQLException {
        Skill skill = new Skill();
        skill.setId(resultSet.getLong("id"));
        skill.setName(resultSet.getString("name"));
        return skill;
    }

}
