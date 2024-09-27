package com.ruleEngine.SpringApp.service;

import java.util.Map;

import com.ruleEngine.SpringApp.model.ASTNode;

public class EvaluateRequestDTO {

	private Map<String, Object> data;
    private ASTNode rule;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public ASTNode getRule() {
        return rule;
    }

    public void setRule(ASTNode rule) {
        this.rule = rule;
    }
}
