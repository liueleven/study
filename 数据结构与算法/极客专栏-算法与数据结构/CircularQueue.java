package com.pmzhongguo.ex;

/**
 * 数组循环队列
 */
public class CircularQueue {

    //队列的长度
    private int n = 0;

    //队列中的元素
    private String[] items;

    //队头元素下标
    private int head = 0;

    //队尾元素下标
    private int tail = 0;

    /**
     * 初始化队列
     * @param n 该队列的长度
     */
    public CircularQueue(int n) {
        this.n = n;
        this.items = new String[n];
    }

    /**
     * 入队
     * @param item 入队的元素
     * @return
     */
    public boolean enqueue(String item) {
        // 队列满了
        if ((tail + 1) % n == head) return false;
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    /**
     * 出队
     * @return 出队的元素
     */
    public String dequeue() {
        // 如果 head == tail 表示队列为空
        if (head == tail) return null;
        String ret = items[head];
        head = (head + 1) % n;
        return ret;
    }

    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(3);
        // 入队前 head: 0 tail: 0 n: 3
        System.out.println("入队前 head: "+queue.head+" tail: "+queue.tail+" n: "+queue.n);
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        // 入队后 head: 0 tail: 2 n: 3
        System.out.println("入队后 head: "+queue.head+" tail: "+queue.tail+" n: "+queue.n);
        String dequeue = queue.dequeue();
        // 出队元素：a
        System.out.println("出队元素："+dequeue);
        // 出队后 head: 1 tail: 2 n: 3
        System.out.println("出队后 head: "+queue.head+" tail: "+queue.tail+" n: "+queue.n);
    }

}
