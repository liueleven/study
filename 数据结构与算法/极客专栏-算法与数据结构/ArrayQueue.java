package com.pmzhongguo.ex.business;

/**
 * 数组顺序队列
 */
public class ArrayQueue {

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
    public ArrayQueue(int n) {
        this.n = n;
        this.items = new String[n];
    }

    /**
     * 入队
     * @param item 入队的元素
     * @return
     */
    public boolean enqueue(String item) {
        // 队尾下标=队列，表示该队列没有空间了
        if(n == tail) {
            // 该队列都填满了
            if(head == 0) return false;
            //该队列队首下标前面还有空闲位置,将对列数据整体往前移动
            for(int i=head; i<tail; i++) {
                items[i-head] = items[i];
            }
            // 移动完数据更新队首、队尾下标
            tail -= head;
            head = 0;
        }
        items[tail] = item;
        // 每次一个元素入队，队尾下标tail都要向后移动一位
        ++ tail;
        return true;
    }

    /**
     * 出队
     * @return 出队的元素
     */
    public String dequeue() {
        if(head == tail) return null;
        String item = items[head];
        // 每次一个元素出队，队首下标head都要向后移动一位
        ++ head;
        return item;
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(8);
        // 入队前 head: 0 tail: 0 n: 8
        System.out.println("入队前 head: "+queue.head+" tail: "+queue.tail+" n: "+queue.n);
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        // 入队后 head: 0 tail: 3 n: 8
        System.out.println("入队后 head: "+queue.head+" tail: "+queue.tail+" n: "+queue.n);
        String dequeue = queue.dequeue();
        // 出队元素：a
        System.out.println("出队元素："+dequeue);
        // 出队后 head: 1 tail: 3 n: 8
        System.out.println("出队后 head: "+queue.head+" tail: "+queue.tail+" n: "+queue.n);
    }

}
