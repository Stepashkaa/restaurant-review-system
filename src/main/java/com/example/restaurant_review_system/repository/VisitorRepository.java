package com.example.restaurant_review_system.repository;

import com.example.restaurant_review_system.entity.Visitor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class VisitorRepository {

    private final List<Visitor> visitors = new ArrayList<>();

    public void save(Visitor visitor){
        visitors.add(visitor);
    }

    public void remove(Visitor visitor) {
        visitors.removeIf(v -> v.getId().equals(visitor.getId()));
    }

    public List<Visitor> findAll() {
        return Collections.unmodifiableList(visitors);
    }
}
