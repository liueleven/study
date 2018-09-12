package leetcode.com.day1;


import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        SumOfNum();
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
}
