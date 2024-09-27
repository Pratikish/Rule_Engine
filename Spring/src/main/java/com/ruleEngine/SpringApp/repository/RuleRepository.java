package com.ruleEngine.SpringApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruleEngine.SpringApp.model.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
