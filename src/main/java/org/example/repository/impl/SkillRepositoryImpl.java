package org.example.repository.impl;

import org.example.model.Skill;
import org.example.repository.SkillRepository;

import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {
    @Override
    public Skill getById(Long aLong) {
        return null;
    }

    @Override
    public List<Skill> getAll() {
        return List.of();
    }

    @Override
    public void save(Skill entity) {

    }

    @Override
    public void update(Skill entity) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
