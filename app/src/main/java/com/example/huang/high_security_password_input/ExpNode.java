package com.example.huang.high_security_password_input;


/**
 * Created by huang on 11/28/2018.
 */

public class ExpNode {
    public Character node;
    public int value;
    public String formula;
    public ExpNode leftNode;
    public ExpNode rightNode;

    ExpNode(int numOfOperators){
        // 建立表达式的树状结构
        if(numOfOperators > 0) {
            int numOfLeft = RandomUntil.getNum(100) % numOfOperators;
            int numOfRight = numOfOperators - numOfLeft - 1; // 注意这里要减去自己的这个1
            this.leftNode = new ExpNode(numOfLeft);
            this.rightNode = new ExpNode(numOfRight);
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
            // 这个是叶子结点， 就是运算数
            this.node = null;
            this.value = RandomUntil.getNum(0, maxNum + 1);
            this.formula = String.valueOf(this.value);
        }
        else{
            // 是操作符节点
            this.node = operators.charAt(RandomUntil.getNum(0, 3));
            this.leftNode.fillNode(maxNum, operators);
            this.rightNode.fillNode(maxNum, operators);
            this.formula = this.leftNode.formula + this.node + this.rightNode.formula;
            this.value = Calculator.calc(this.formula);
        }
    }
}
