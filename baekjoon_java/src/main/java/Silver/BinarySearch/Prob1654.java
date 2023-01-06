package Silver.BinarySearch;

import java.io.*;
import java.util.*;

/**
 * Silver 2(랜선 자르기)
 *
 * https://www.acmicpc.net/problem/1654
 *
 * Solution: Binary Search(Upper Bound)
 */
public class Prob1654 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        long[] lines = new long[n];
        long maxLen = 0;
        for (int i = 0; i < n; i++) {
            lines[i] = Integer.parseInt(br.readLine());
            maxLen = Math.max(maxLen, lines[i]);
        }

        /**
         * 최대 길이, 즉 최댓값을 구하기 때문에 upper bound
         */
        long left = 1;
        long right = maxLen;
        while (left <= right) {
            long mid = (left + right) / 2;

            /**
             * 각 랜선 길이를 mid 값으로 했을때, 총 만들수 있는 랜선의 갯수 구하기
             * 총 만들수 있는 랜선의 갯구 = 각 랜선 / mid
             */
            int linesCnt = 0;
            for(int i = 0; i < n; i++){
                linesCnt += (lines[i] / mid);
            }

            if (linesCnt >= k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(right);
    }
}
