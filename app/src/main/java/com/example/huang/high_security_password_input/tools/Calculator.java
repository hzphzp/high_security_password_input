package com.example.huang.high_security_password_input.tools;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by huang on 10/4/2018.
 */


public class Calculator {

    public static int calc(String s, int modulo) {
        Expression calc = new ExpressionBuilder(s).build();
        int result = (int) calc.evaluate();
        return result % modulo;
    }

    public static int calc(String s) {
        Expression calc = new ExpressionBuilder(s).build();
        int result = (int) calc.evaluate();
        return result;
    }
}
