       package com.ruleEngine.SpringApp.service;

import com.ruleEngine.SpringApp.model.ASTNode;
import com.ruleEngine.SpringApp.util.RuleParser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RuleService {

	public enum ComparisonOperator {
        GREATER_THAN,
        LESS_THAN,
        EQUALS,
        NOT_EQUALS
    }

    // Create rule from rule string
    public ASTNode createRule(String ruleString) {
        return RuleParser.parseRule(ruleString);
    }

    // Combine two rules into a single AST using AND operator
    public ASTNode combineRules(ASTNode rule1, ASTNode rule2) {
        return new ASTNode("operator", "AND", rule1, rule2, null);
    }

    // Main evaluation method that processes the AST recursively
    public boolean evaluateRule(ASTNode rule, Map<String, Object> data) {
        if (rule == null) {
            throw new IllegalArgumentException("Rule cannot be null");
        }

        // Traverse based on the node type (operator or operand)
        switch (rule.getType()) {
            case "operator":
                return evaluateOperator(rule, data);
            case "operand":
                return evaluateOperand(rule, data);
            default:
                throw new IllegalArgumentException("Unsupported node type: " + rule.getType());
        }
    }

    // Method to handle operators like AND, OR, >, <
    private boolean evaluateOperator(ASTNode node, Map<String, Object> data) {
        String operator = node.getOperator();

        // Evaluate based on the operator type
        switch (operator) {
            case "AND":
                return evaluateRule(node.getLeft(), data) && evaluateRule(node.getRight(), data);
            case "OR":
                return evaluateRule(node.getLeft(), data) || evaluateRule(node.getRight(), data);
            case ">":
                return evaluateComparison(node, data, (a, b) -> ((Number) a).doubleValue() > ((Number) b).doubleValue());
            case "<":
                return evaluateComparison(node, data, (a, b) -> ((Number) a).doubleValue() < ((Number) b).doubleValue());
            case "=":
                return evaluateComparison(node, data, Object::equals);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    // Method to handle operand evaluation (fetching values from the data map)
    private boolean evaluateOperand(ASTNode node, Map<String, Object> data) {
        Object value = data.get(node.getValue());
        return value != null;  // Check if the value exists in the data map
    }

    // Method to handle comparisons like >, <, = using the provided functional interface
    private boolean evaluateComparison(ASTNode node, Map<String, Object> data, ComparatorFunction comparator) {
        Object leftValue = data.get(node.getLeft().getValue());
        Object rightValue = node.getRight().getValue();

        // Ensure the right value is cast to the correct type if necessary
        if (leftValue instanceof Number && rightValue instanceof String) {
            rightValue = Double.parseDouble((String) rightValue); // Parse right side as a number if it's a string
        }

        if (leftValue instanceof Number && rightValue instanceof Number) {
            // Handle numeric comparison
            return comparator.compare(leftValue, rightValue);
        } else if (leftValue instanceof String && rightValue instanceof String) {
            // Handle string comparison (equality or inequality)
            return comparator.compare(leftValue, rightValue);
        }

        throw new IllegalArgumentException("Incompatible types for comparison: " + leftValue + " and " + rightValue);
    }

    // Functional interface for comparing two values
    @FunctionalInterface
    private interface ComparatorFunction {
        boolean compare(Object a, Object b);
    }
}