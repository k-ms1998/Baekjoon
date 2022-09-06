package Gold.Basic;

import java.io.*;
import java.util.*;

/**
 * Gold 4
 *
 * https://www.acmicpc.net/problem/1806
 *
 * Solution: 누적합, 두 포인터
 */
public class Prob1806 {

    static int n;
    static int s;

    static int[] sum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        sum = new int[n];

        /**
         * sum 에 idx 0~i 까지 더한 값을 저장
         */
        sum[0] = Integer.parseInt(st.nextToken());
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + Integer.parseInt(st.nextToken());
        }

        /**
         * a~b 구간의 합 = 0~b 의 합 - 0~a 의 합 (b > a)
         * lastIdx 에는 a 의 idx 값 저장
         * c > b 일때, a~b 구간의 합 >= s 이면 무조건 a~c 구간의 합 >= s
         * -> 그러므로, a~c < s 될때까지 a의 인덱스 값을 1 증가시켜줌
         *  -> 이때, a~c 의 차이(길이)가 가장 짧은 값을 구함
         *      -> Answer
         */
        int lastIdx = -1;
        int ans = n + 1;
        for (int i = 0; i < n; i++) {
            if (sum[i] >= s) {
                while (sum[i] - sum[lastIdx + 1] >= s) {
                    lastIdx++;
                }
                ans = Math.min(ans, i - lastIdx);
            }
        }

        System.out.println(ans > n ? 0 : ans);
    }
}
/*
10 15
5 1 3 7 4 9 2 8 5 10
Ans: 2

10 10
10 1 1 1 1 1 1 1 1 1
Ans: 1

10 10
1 1 1 1 1 1 1 1 1 1
Ans: 10

10 11
1 1 1 1 1 1 1 1 1 1
Ans: 0

10 11
1 1 1 1 1 1 1 1 1 2
Ans: 10
 */
