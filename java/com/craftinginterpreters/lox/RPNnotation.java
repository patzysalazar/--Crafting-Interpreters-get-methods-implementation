package com.craftinginterpreters.lox;

public class RPNnotation implements Expr.Visitor<String> {
    String print(Expr expr){//print method
        return expr.accept(this);//take expression and accept the visitor
    }

    @Override
    public String visitFunctExpr(Expr.Funct expr){
        return "";
    }

    @Override
    public String visitLiteralExpr( Expr.Literal lit){//visitor method will return the strings
        return String.valueOf(lit.value);// converts the object value into a string
       
    }
    @Override
    public String visitBinaryExpr(Expr.Binary expression){//+-*/
        Expr left = expression.left;//get left field from binary expression and make variable
        Expr right = expression.right;//get right field from binary expression and make variable
        Token operator = expression.operator;//operator stored as token

        String leftString = left.accept(this);//visits left expression and returns string, same for the other one
        String rightString = right.accept(this);

        return leftString + " " + rightString + " " + operator.lexeme;//RPN notation in total string form

    }
    @Override
    public String visitConditionalExpr(Expr.Conditional express){
        return print(express.condition) + "? " + print(express.trueValue) + " : " +print(express.falseValue);

    }
    @Override
    public String visitGroupingExpr(Expr.Grouping express){//grouping by parentheses
        return express.expression.accept(this);//what is inside parenthesis take to RPN visitor

    }
    @Override
    public String visitUnaryExpr(Expr.Unary express){
        Expr right = express.right;
        Token op = express.operator;

        String rightString = right.accept(this);
        return rightString + " " + op.lexeme;



    }

    public static void main(String[] args){
        Expr leftSideParen = new Expr.Grouping(new Expr.Binary(new Expr.Literal(1), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(2)));
        Expr rightSideParen = new Expr.Grouping(new Expr.Binary(new Expr.Literal(4), new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(3)));
        Expr TotalExpr = new Expr.Binary(leftSideParen, new Token(TokenType.STAR, "*", null, 1), rightSideParen);
        RPNnotation RPN = new RPNnotation();
        System.out.println(RPN.print(TotalExpr));

    }

    @Override
    public String visitVariableExpr( Expr.Variable expr){//visitor method will return the strings
        return expr.name.lexeme;// converts the object value into a string

    }

    @Override
    public String visitThisExpr( Expr.This expr){//visitor method will return the strings
        return expr.keyword.lexeme;// converts the object value into a string

    }

    @Override
    public String visitSuperExpr( Expr.Super expr){//visitor method will return the strings
        return expr.keyword.lexeme;// converts the object value into a string

    }

    @Override
    public String visitSetExpr( Expr.Set expr){//visitor method will return the strings
        return expr.name.lexeme;// converts the object value into a string

    }
    @Override
    public String visitLogicalExpr( Expr.Logical expr){//visitor method will return the strings
        return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;// converts the object value into a string

    }

    @Override
    public String visitGetExpr( Expr.Get expr){//visitor method will return the strings
        return expr.object.accept(this) + " " + expr.name.lexeme;

    }
    @Override
    public String visitCallExpr( Expr.Call expr){//visitor method will return the strings
        return expr.callee.accept(this);
    }

    @Override
    public String visitAssignExpr( Expr.Assign expr){//visitor method will return the strings
        return expr.value.accept(this);
    }
    

}
