package com.example.huang.high_security_password_input;


import android.view.ViewDebug;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by huang on 11/28/2018.
 */

public class ExpNode {
    public static int index = 0;
    public static int localKey = 0;
    public static int localPin = 0;
    public static int key;
    public static int pin;

    public Character node;
    public int value = 0;
    public String formula;
    public String maskFormula;
    public ExpNode leftNode;
    public ExpNode rightNode;

    ExpNode(int numOfOperators){
        // 建立表达式的树状结构
        if(numOfOperators > 0) {
            int numOfLeft = RandomUntil.getNum(100) % numOfOperators;
            int numOfRight = numOfOperators - numOfLeft - 1; // 注意这里要减去自己的这个1
            this.leftNode = new ExpNode(numOfLeft);
            this.rightNode = new ExpNode(numOfRight);
            return;
        }
        else{
            // 这是递归的出口, 即到了最后的叶子结点
            this.leftNode = null;
            this.rightNode = null;
            return;
        }
    }

    public void fillNode(int maxNum, String operators){
        if(this.leftNode == null || this.rightNode == null){
            // 这个是index = ExpNode.index 的叶子结点， 就是运算数
            this.node = null;
            if(ExpNode.index == ExpNode.localKey){
                // 这个叶子结点被选为了key
                this.maskFormula = "Key";
                this.value = ExpNode.key;
                this.formula = String.valueOf(ExpNode.key);
            }
            else if (ExpNode.index == ExpNode.localPin){
                // 这个叶子结点被选为了pin
                this.maskFormula = "Pin";
                this.value = ExpNode.pin;
                this.formula = String.valueOf(ExpNode.pin);
            }
            else {
                this.value = RandomUntil.getNum(0, maxNum + 1);
                this.formula = String.valueOf(this.value);
                this.maskFormula = this.formula;
            }
            ExpNode.index += 1;
        }
        else{
            // 是操作符节点
            this.node = operators.charAt(RandomUntil.getNum(0, 3));
            this.leftNode.fillNode(maxNum, operators);
            this.rightNode.fillNode(maxNum, operators);
            this.formula = this.leftNode.formula + this.node + this.rightNode.formula;
            this.maskFormula = this.leftNode.maskFormula + this.node + this.rightNode.maskFormula;
            this.value = Calculator.calc(this.formula);
        }
    }

    public void insertX(int numOfOperators, int key, int pin, int maxNUm, String operators){
        // 遍历一遍叶子结点， 并随机的将其中的两个叶子结点变成 key 和 pin
        int numOfOperands = numOfOperators + 1;
        int localKey = 0;
        int localPin = 0;
        while(localKey == localPin) {
            localKey = RandomUntil.getNum(0, numOfOperands); // 最多可以取到操作数的数量减1，因为这个是index
            localPin = RandomUntil.getNum(0, numOfOperands);
        }
        ExpNode.index = 0;
        ExpNode.key = key;
        ExpNode.pin = pin;
        ExpNode.localKey = localKey;
        ExpNode.localPin = localPin;

        fillNode(maxNUm, operators);

        /*
        // 后面使用的是广度优先遍历
        int index = 0; // 遍历到叶子结点的序号
        Queue<ExpNode> queue = new LinkedList<ExpNode>();
        queue.add(this);
        while(!queue.isEmpty()){
            ExpNode currentNode = queue.poll();
            if(currentNode.leftNode == null || currentNode.rightNode == null){
                // 遍历到了叶子结点
                if(index == localKey){
                    // 这个叶子结点需要被替换成为key
                    currentNode.maskFormula = "key";
                }
            }
        }
        */

    }

}
