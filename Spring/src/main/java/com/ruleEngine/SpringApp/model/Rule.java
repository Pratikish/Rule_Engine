package com.ruleEngine.SpringApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "rules")
public class Rule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expression", nullable = false)
    private String expression;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default constructor
    public Rule() {
        this.createdAt = LocalDateTime.now(); // Set created_at to current time
    }

    // Constructor with parameters
    public Rule(String expression) {
        this.expression = expression;
        this.createdAt = LocalDateTime.now(); // Set created_at to current time
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
