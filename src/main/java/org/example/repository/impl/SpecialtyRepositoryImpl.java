package org.example.repository.impl;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                specialties.add(mapRowToSkill(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }

    @Override
    public Specialty getById(Long id) {
        return new Specialty();
    }

    @Override
    public void save(Specialty specialty) {

    }

    @Override
    public void update(Specialty specialty) {

    }

    @Override
    public void delete(Long id) {

    }

    private Specialty mapRowToSkill (ResultSet resultSet) throws SQLException {
        Specialty specialty = new Specialty();
        specialty.setId(resultSet.getLong("id"));
        specialty.setName(resultSet.getString("name"));
        return specialty;
    }
}
