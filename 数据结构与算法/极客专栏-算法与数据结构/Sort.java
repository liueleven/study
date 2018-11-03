package com.pmzhongguo.ex;

import java.util.Arrays;
import java.util.Random;

/**
 * 数组随机、冒泡排序和插入排序
 */
public class Sort {

    public static void main(String[] args) {
        int[] arr = {1,5,2,3,6,4,7,8,9};
        //冒泡
//        bubbleSort(arr);
        //插入
//        insertionSort(arr);
        //随机
//        shuffle(arr);
        mergeSort(arr,0,arr.length-1);
    }

    /**
     * 冒泡排序思想：相邻数字两两比较，大的就上浮
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for(int i=0; i<arr.length; i++) {
            //用来判断是否有替换
            boolean flag = false;
            for(int j=0; j<arr.length - i  -1 ; j++) {
                if(arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    flag = true;
                }
            }
            if(!flag) {
                break;
            }
        }
        System.out.println(String.format("冒泡排序：%s",Arrays.toString(arr)));
    }

    /**
     * 插入排序思路：从下标1开始，和前面的比较，如果前面的大于当前的，就替换值
     * 虽然有需要遍历比较，但是不需要交换位置，而是替换
     * @param arr
     */
    public static void insertionSort(int[] arr) {
        for (int i=1; i<arr.length; i++) {
            //i从下标1开始
            int value = arr[i];
            int j = i - 1;
            for(; j>=0; --j) {
                // 这个的元素是否比当前i大，如果是，就把这个元素下标进1
                if(arr[j] > value) {
                    arr[j+1] = arr[j];
                } else {
                    break;
                }
            }
            //前面的哪个元素不大于i，就把i插到这个位置，那么这个i就被插到这个位置，
            arr[j+1] = value;
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 数组随机思路：利用random函数每次随机生成一个小于i的随机数作为下标，进行替换
     * @param arr
     */
    public static void shuffle(int arr[]) {
        Random random = new Random();
        for(int i=arr.length; i>1; i--) {
            swap(arr,i-1,random.nextInt(i));

        }
        System.out.println(Arrays.toString(arr));
    }
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    /**
     * 归并排序，先拆分，后合并。
     * @param L 数组的第一个元素
     * @param R 数组的最后一个元素
     *
     * */
//    public static void mergeSort(int[] arrays, int L, int R) {
//
//        // 递归结束，如果拆到一个就不拆了
//        if(L == R) {
//            return;
//        }
//        //每次获取中的数，进行拆分
//        int M = (L + R) / 2;
//        // 先拆分左边的,左边的以L为第一个元素，拆分的M为最后一个元素[1,2,3,4,5]
//        mergeSort(arrays,L,M);
//
//        //再拆分右边的，右边以M+1为第一个元素，R为最后一个元素[6,7,8,9,10]
//        mergeSort(arrays,M+1,R);
//        //开始合并
//        merge(arrays,L,M+1,R);
//    }
//
//
//    /**
//     *
//     * @param arrays
//     * @param L  指向数组第一个元素
//     * @param M
//     * @param R
//     */
//    public static void merge(int[] arrays,int L, int M, int R) {
//        System.out.println(L+"-"+"-"+M+"-"+R);
//        //左边的数组的大小
//        int[] leftArray = new int[M - L];
//
//        //右边的数组大小
//        int[] rightArray = new int[R - M + 1];
//
//        //往这两个数组填充数据
//        for (int i = L; i < M; i++) {
//            leftArray[i - L] = arrays[i];
//        }
//        for (int i = M; i <= R; i++) {
//            rightArray[i - M] = arrays[i];
//        }
//
//
//        int i = 0, j = 0;
//        // arrays数组的第一个元素
//        int  k = L;
//
//
//        //比较这两个数组的值，哪个小，就往数组上放
//        while (i < leftArray.length && j < rightArray.length) {
//
//            //谁比较小，谁将元素放入大数组中,移动指针，继续比较下一个
//            if (leftArray[i] < rightArray[j]) {
//                arrays[k] = leftArray[i];
//
//                i++;
//                k++;
//            } else {
//                arrays[k] = rightArray[j];
//                j++;
//                k++;
//            }
//        }
//
//        //如果左边的数组还没比较完，右边的数都已经完了，那么将左边的数抄到大数组中(剩下的都是大数字)
//        while (i < leftArray.length) {
//            arrays[k] = leftArray[i];
//
//            i++;
//            k++;
//        }
//        //如果右边的数组还没比较完，左边的数都已经完了，那么将右边的数抄到大数组中(剩下的都是大数字)
//        while (j < rightArray.length) {
//            arrays[k] = rightArray[j];
//
//            k++;
//            j++;
//        }
//    }




   /* *//**
     * 递归调用函数
     * 归并排序思想，先将数组分解，再合并，分解到不能再分解，然后合并排序，分治思想
     *//*
    private static void mergeSortInternally(int[] a, int p, int r) {
        // 递归终止条件
        if (p >= r) return;

        // 取p到r之间的中间位置q,防止（p+r）的和超过int类型最大值
        // 1.p=0,r=9;  2.p=0,r=4;  3.p=0,r=2;  4.p=0,r=1
        int q = p + (r - p)/2;
        // 分治递归，
        // 1.p=0,r=9,q=4;  2.p=0,r=4,q=2;  3.p=0,r=2,q=1;  4.p=0,r=1,q=0
        mergeSortInternally(a, p, q);
        // 1.p=0,r=1,q=0;  2.p=0,r=2,q=1;  3.
        mergeSortInternally(a, q+1, r);

        // 将A[p...q]和A[q+1...r]合并为A[p...r]

        merge(a, p, q, r);
        System.out.println(Arrays.toString(a));
    }

    private static void merge(int[] a, int p, int q, int r) {
        //1.p=0,r=1,q=0;  2.p=0,q=1,r=2
        int i = p;
        int j = q+1;
        // 初始化变量i, j, k
        int k = 0;
        // 申请一个大小跟a[p...r]一样的临时数组
        int[] tmp = new int[r-p+1];
        while (i<=q && j<=r) {
            if (a[i] <= a[j]) {
                // i++等于i:=i+1
                tmp[k++] = a[i++];
            } else {
                tmp[k++] = a[j++];
            }
        }

        // 判断哪个子数组中有剩余的数据
        int start = i;
        int end = q;
        if (j <= r) {
            start = j;
            end = r;
        }

        // 将剩余的数据拷贝到临时数组tmp
        while (start <= end) {
            tmp[k++] = a[start++];
        }

        // 将tmp中的数组拷贝回a[p...r]
        for (i = 0; i <= r-p; ++i) {
            a[p+i] = tmp[i];
        }
    }*/

    public static void mergeSort(int[] arr,int r,int left) {

        Arrays.sort(arr);
        if(r == left) return;
        int mid = (r+left) / 2;
        System.out.println(String.format("left----------: 生成子序列：%s,%s",r,mid));
        mergeSort(arr,r,mid);
        System.out.println(String.format("right: 生成子序列：%s,%s",r,mid));
        mergeSort(arr,mid+1,left);
//        System.out.println(String.format("%%%%%%%%%%%%%%%%开始合并：%s,%s，%s",left,mid,r));
    }
}
