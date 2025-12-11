package com.example.restaurant_review_system.repository;

import com.example.restaurant_review_system.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

}
