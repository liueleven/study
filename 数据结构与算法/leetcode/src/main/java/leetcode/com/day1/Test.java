package leetcode.com.day1;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        createNewLinkendList();
//        SumOfNum();
    }

    /**
     * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
     * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     */
    public static void SumOfNum() {
        int[] nums = {2, 7, 11, 15};
        int target = 22;
        Map map = new HashMap();

        for(int i=0; i<nums.length; i++) {
            map.put(nums[i],i);
        }
        for(int i=0; i<nums.length; i++) {
            if(map.get(target - nums[i]) != null) {
                System.out.println("target：9，目标值的数组下标是：" + i + " 和 " + map.get(target - nums[i]));
                break;
            }
        }
    }

    /**
     * 给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。
     * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
    public static void createNewLinkendList() {
        int[] linkendOne = {2,4,3};
        int[] linkendTwo = {5,6,4};
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for(int i=0; i<linkendOne.length; i++) {
            sb1.append(linkendOne[i]);
            sb2.append(linkendTwo[i]);
        }
        int num1 = Integer.valueOf(sb1.toString());
        int num2 = Integer.valueOf(sb2.toString());
        System.out.println(num1 + num2);
    }
}
