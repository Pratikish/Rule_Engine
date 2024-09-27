package com.ruleEngine.SpringApp.util;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruleEngine.SpringApp.model.ASTNode;

public class RuleParser {

    //  parse rule strings into ASTNodes
    public static ASTNode parseRule(String ruleString) {
        Stack<ASTNode> stack = new Stack<>();
        Stack<String> operators = new Stack<>();

        // pattern of rule
        Pattern pattern = Pattern.compile("\\(|\\)|AND|OR|=|>|<|'[^']*'|\\w+");
        Matcher matcher = pattern.matcher(ruleString);

        
        
        while (matcher.find()) {
            String token = matcher.group();

            if (token.equalsIgnoreCase("AND") || token.equalsIgnoreCase("OR") ||
                token.equals(">") || token.equals("<") || token.equals("=")) {
                // Process operators (logical and comparison)
                while (!operators.isEmpty() && isHigherPrecedence(operators.peek(), token)) {
                    String operator = operators.pop();
                    createOperatorNode(stack, operator);
                }
                operators.push(token); // Push the current operator onto the stack
            } else if (token.equals("(")) {
               
                operators.push(token);
            } else if (token.equals(")")) {
               
            	
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    String operator = operators.pop();
                    createOperatorNode(stack, operator);
                }
                if (!operators.isEmpty()) {
                    operators.pop(); // Pop the '('
                }
            } else {
                // Handle operands
                ASTNode operandNode = new ASTNode();
                operandNode.setType("operand");
                operandNode.setOperator(null); // No operator for operands
                operandNode.setValue(token);
                stack.push(operandNode);
            }
        }

        //  remaining operators in stack
        while (!operators.isEmpty()) {
            String operator = operators.pop();
            createOperatorNode(stack, operator);
        }

        // The final AST root should be the last remaining element in the stack
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid rule: mismatched operators and operands");
        }

        return stack.pop();
    } 

    // Method to create an operator node and push it to the stack
    private static void createOperatorNode(Stack<ASTNode> stack, String operator) {
        if (stack.size() < 2) {
            throw new IllegalArgumentException("Invalid rule: mismatched operators and operands");
        }

        ASTNode rightChild = stack.pop();
        ASTNode leftChild = stack.pop();

        ASTNode operatorNode = new ASTNode();
        operatorNode.setType("operator");
        operatorNode.setOperator(operator);
        operatorNode.setLeft(leftChild);
        operatorNode.setRight(rightChild);
        operatorNode.setValue(operator);

        stack.push(operatorNode);
    }

    // Method to determine the precedence of operators
    private static boolean isHigherPrecedence(String op1, String op2) {
        // Define operator precedence: >, <, = have higher precedence than AND/OR
        if (op1.equals("AND") || op1.equals("OR")) {
            return false;
        }
        return true; // >, <, = have higher precedence
    }
}
