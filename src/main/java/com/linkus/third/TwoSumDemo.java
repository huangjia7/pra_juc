package com.linkus.third;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 *
 *
 */
public class TwoSumDemo {
    // 暴力破解
    static int[] twoSum1(int[] nums, int target) {
        int count = nums.length;
        for(int i = 0 ;i< count; ++i){
            for(int j=i+1;j<count;++j){
                if (target-nums[i]==nums[j]){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }
    // hash算法
    static int[] twoSum2(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap();//key 值 value 索引
        for (int i = 0;i < nums.length; ++i){
            int temp = target - nums[i];
            if(map.containsKey(temp)){//说明存在
                return new int[]{map.get(temp), i};
            }
            map.put(nums[i] , i);
        }
        //map K值  V下标
        //     2     i
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,7,11,15};
        int target = 22;
        //int [] myIndex = twoSum1(nums, target);
        int [] myIndex = twoSum2(nums, target);

        for(int ele : myIndex){
            System.out.print(ele+"-");
        }
    }

}
