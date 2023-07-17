package Gold.Greedy;

import java.io.*;
import java.util.*;

/**
 * Gold 4(수 묶기)
 *
 * https://www.acmicpc.net/problem/1744
 * 
 * Solution: 정렬 + 그리디
 * 1. 최대 값을 만들려면 값이 큰 숫자 끼리 묶어야 됨 -> 정렬
 * 2. 이때, 1이상의 값들끼리만 보고, 그리고 0이하의 값들 끼리만 본다
 *  -> 음수는 음수들 끼리 묶어서 곱해야 최대 값을 만들 수 있음
 *
 */
public class Prob1744 {

    static int n;
    static Integer[] nums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        nums = new Integer[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(nums, Collections.reverseOrder()); // 내림차순으로 정렬

        int answer = 0;
        int idx = 0;
        for(int i = 0; i < n - 1; i++){ // 1이상인 값들 탐색
            int a = nums[i];
            int b = nums[i + 1];
            idx = i + 1;
            if (a <= 0 || b <= 0) { // 0이하 값을 찾으면 종료
                idx = a <= 0 ? i : i + 1;
                break;
            }
            if (a * b > a + b) {
                nums[i] = a * b;
                nums[i + 1] = 0;
                i++;
            }
        }
        for(int i = n - 1; i > idx; i--){ // 0이하인 값들 탐색
            int a = nums[i];
            int b = nums[i - 1];

            if (a * b > a + b) {
                nums[i] = a * b;
                nums[i - 1] = 0;
                i--;
            }
        }
        for(int i = 0; i < n; i++){
            answer += nums[i];
        }
        System.out.println(answer);

    }
}
/*
4
-5
-1
0
1
-> answer = 6
 */