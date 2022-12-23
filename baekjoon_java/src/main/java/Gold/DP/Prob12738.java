package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 12738(가장 긴 증가하는 부분 수열 3)
 *
 * https://www.acmicpc.net/problem/12738
 *
 * Solution: 가장 긴 증가하는 부분 수열(LIS) + BinarySearch
 */
public class Prob12738 {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        int n = Integer.parseInt(br.readLine());
        int[] nums = new int[n + 1];
        int seqLen = 0;
        int[] seq = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
            if(i == 1){
                seq[0] = nums[1];
            }else{
                if(nums[i] > seq[seqLen]){
                    ++seqLen;
                    seq[seqLen] = nums[i];
                }else{
                    binarySearch(0, seqLen, seq, nums[i]);
                }
            }
        }

        System.out.println(seqLen + 1);
    }

    public static void binarySearch(int start, int end, int[]seq, int num){
        int left = start;
        int right = end;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (seq[mid] < num) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        seq[left] = num;
    }
}
