package Gold.DP.LIS;

import java.io.*;
import java.util.*;

/**
 * Gold 2(꼬인 전깃줄)
 *
 * https://www.acmicpc.net/problem/1365
 *
 * Solution: DP(LIS) + Binary Search:
 * Prob2352와 사실상 동일한 문제
 */
public class Prob1365 {

    static int n;
    static int[] num;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        num = new int[n + 1];
        dp = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }

        int length = 1;
        dp[1] = num[1];
        for (int i = 2; i < n + 1; i++) {
            if (num[i] > dp[length]) {
                length++;
                dp[length] = num[i];
            }else{
                binarySearch(length, num[i]);
            }
        }

        /**
         * 제거해야되는 전깃줄의 수 == 전체 전깃줄의 수 - 최대로 꼬이지 않고 연결할수 있는 전길줄의 수
         */
        System.out.println(n - length);
    }

    public static void binarySearch(int length, int curNum) {
        int left = 1;
        int right = length;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (dp[mid] < curNum) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }

        dp[left] = curNum;
    }
}
