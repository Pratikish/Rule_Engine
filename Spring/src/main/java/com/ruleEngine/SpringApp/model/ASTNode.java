package com.ruleEngine.SpringApp.model;

public class ASTNode {
    private String type; // "operator" or "operand"
    private String operator; // For operator nodes 
    private ASTNode left; // Left child
    private ASTNode right; // Right child
    private String value; 

    
     public ASTNode() {
    }
    
    public ASTNode(String type, String operator, ASTNode left, ASTNode right, String value) {
        this.type = type;
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.value = value;
    }
    
    
    //serialize data
    public String getExpression() {
        if ("operand".equals(type)) {
            return value;  
        } else {
            // For operator nodes, build the expression recursively
            String leftExpr = (left != null) ? left.getExpression() : "";
            String rightExpr = (right != null) ? right.getExpression() : "";
            return "(" + leftExpr + " " + operator + " " + rightExpr + ")";
        }
    }

    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public ASTNode getLeft() { return left; }
    public void setLeft(ASTNode left) { this.left = left; }

    public ASTNode getRight() { return right; }
    public void setRight(ASTNode right) { this.right = right; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
