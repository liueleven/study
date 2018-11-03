package com.pmzhongguo.ex.business;

public class ArrayStack {

    //栈的大小
    private int n;

    //栈中的元素个数
    private int count;

    //数组
    private String[] items;

    /**
     * 初始化栈
     * @param n 栈的深度
     */
    public ArrayStack(int n) {
        this.n = n;
        this.count = 0;
        this.items = new String[n];
    }

    /**
     * 入栈
     * @param item 入栈的元素
     * @return
     */
    public boolean push(String item) {
        if(count == n) return false;
        items[count] = item;
        ++ count;
        return true;
    }

    /**
     * 出栈
     * @return 出栈的元素
     */
    public String pop() {
        if (count == 0) return null;
        String item = items[count - 1];
        -- count;
        return item;
    }


    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(3);
        stack.push("I love Java");
        stack.push("then use on the work");
        String pop = stack.pop();
        System.out.println(pop);
    }

}
