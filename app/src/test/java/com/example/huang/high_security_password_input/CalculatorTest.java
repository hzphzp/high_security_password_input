package com.example.huang.high_security_password_input;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by huang on 10/4/2018.
 */
public class CalculatorTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void calc() throws Exception {
        System.out.println(String.valueOf(Calculator.calc("1+*1", 0)));
    }

}