package com.example.huang.high_security_password_input;

import com.example.huang.high_security_password_input.tools.ExpNode;
import com.example.huang.high_security_password_input.tools.RandomUntil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by huang on 11/28/2018.
 */
public class ExpNodeTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fillNode() throws Exception {
        int key = 5;
        int pin = 2;
        String operators = "+-*";
        int maxNum = 9;
        int numOfOperators = RandomUntil.getNum(3, 7);
        System.out.println(numOfOperators);
        for(int i = 0; i < 10; i++) {
            ExpNode expNode = new ExpNode(numOfOperators);
            expNode.fillNode(maxNum, operators);
            System.out.println(expNode.formula);
            System.out.println(expNode.value);
        }
    }

    @Test
    public void insertX() throws Exception {
        int key = 5;
        int pin = 2;
        String operators = "+-*";
        int maxNum = 9;
        int numOfOperators = RandomUntil.getNum(3, 7);
        System.out.println(numOfOperators);
        for(int i = 0; i < 100; i++) {
            ExpNode expNode = new ExpNode(numOfOperators);
            expNode.insertX(numOfOperators,key, pin, maxNum, operators);
            System.out.println(expNode.formula);
            System.out.println(expNode.maskFormula);
            System.out.println(expNode.value);
        }
    }


}