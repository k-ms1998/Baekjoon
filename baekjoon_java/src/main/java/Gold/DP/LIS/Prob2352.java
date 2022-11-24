package Gold.DP.LIS;

import java.io.*;
import java.util.*;

/**
 * Gold 2(반도체 설계)
 *
 * https://www.acmicpc.net/problem/2352
 *
 * Solution: DP(LIS: Longest Increasing Sequence) + Binary Search
 */
public class Prob2352 {

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
            dp[i] = 1;
        }

        /**
         * dp 에는 현재까지 나온 숫자들로 이루어진 가장 긴 증가하는 부분 수열 저장
         * dp[1]에는 가장 긴 증가하는 부분 수열에서 첫번째 숫자 저장
         * dp[2]에는 가장 긴 증가하는 부분 수열에서 두번째 숫자 저장
         * dp[3]에는 가장 긴 증가하는 부분 수열에서 세번째 숫자 저장
         * ...
         * 
         * length 는 현재까지 나온 숫자들로 만들어진 가장 긴 증가하는 부분 수열의 길이 저장
         */
        int length = 1;
        dp[length] = num[1];
        for (int i = 1; i < n + 1; i++) {
            /**
             * dp[length] == 현재까지 나온 숫자들로 만들어진 가장 긴 증가하는 부분 수열의 마지막 원소 => 즉, 현재까지 나온 숫자들 중에서 가장 큰 숫자
             * num[i] > dp[length] 이면 가장 긴 증가하는 부분 수열에 num[i]를 마지막에 추가해서 수열의 길이를 더 늘릴 수 있음
             * 그러므로, num[i]를 추가해줌
             */
            if (num[i] > dp[length]) {
                length++;
                dp[length] = num[i];
            }else{
                /**
                 * num[i] <= length 이면 num[i]를 추가해서 수열의 길이를 증가 시켜주지는 못함
                 * 그러므로, 현재 나온 숫자들 중에서, num[i]의 위치를 찾아서 저장해주면됨
                 * num[i]의 위치는 현재까지 나온 숫자들 중에서 자기 자신보다 처음으로 큰 숫자의 위치
                 * (dp는 애초에 오름차순으로 값들이 저장되어 있기 떄문에 자리를 찾기 쉽다)
                 * num[i]의 위치를 찾아주는 이유는, 가장 낮은 숫자들로 증가하는 수열을 만들어야 가장 긴 증가하는 수열을 구할 수 있기 때문이다.
                 *
                 * 1 3 5 2 3 5 이라는 숫자들이 주어졌을때,
                 * 5까지 탐색했을때 dp는 {1, 3, 5} 이다.
                 * 2까지 탐색: dp == {1, 2, 5}
                 * 3까지 탐색: dp == {1, 2, 3}
                 * 5까지 탐색: dp == {1, 2, 3, 5}
                 * 
                 * 이때, 자리를 빨리 찾아주기 위해서 이분 탐색(Binary Search) 사용
                 */
                binarySearch(length, num[i]);
            }
//            printDp(length);
        }
        System.out.println(length);
    }

    /**
     * 이분 탐색으로 현재 탐색 값의 위치 찾아주기:
     * 현재 값(curNum)보다 처음으로 큰 값을 위치로 잡아줌
     *
     * ex: dp = {1, 2, 5, 6}, curNum = 3
     * left = 1, right = 4, mid = 2, dp[mid] = 2
     * left = 3, right = 4, mid = 3, dp[mid] = 3
     * left = 3, right = 2 => BREAK
     * curNum 의 위치는 3
     * => dp = {1, 2, 3, 6}
     */
    public static void binarySearch(int length, int curNum) {
        int left = 1;
        int right = length;
        while (left < right) {
            int mid = (left + right) / 2;
            if (dp[mid] < curNum) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        dp[left] = curNum;
    }

    public static void printDp(int length) {
        for(int i = 1; i <= length; i++){
            System.out.print(dp[i] + " ");
        }
        System.out.println();
    }
}
/*
6
1 3 5 2 3 5
 */


