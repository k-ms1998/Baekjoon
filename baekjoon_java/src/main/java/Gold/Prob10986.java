package Gold;

import java.io.*;
import java.util.*;

/**
 * Gold 3(나머지 합)
 * 
 * https://www.acmicpc.net/problem/10986
 *
 * Solution: 수학 + 누적 합
 */
public class Prob10986 {

    static int n, m;
    static int[] sum;
    static long[] remainder;

    static long ans = 0;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        /**
         * 구간 i~j의 합 == sum[j] - sum[i]
         * 구간 i~j의 합이 m과 나누어 떨어짐 -> (sum[j] - sum[i]) % m == 0
         *  => 즉, sum[j] % m == sum[i] % m 이면 구간 i~j의 합이 m으로 나누어 떨어짐
         * 그러므로, sum % m 의 값의 빈도를 파악 ( sum % m은 무조건 0이상, m-1 이하이다)
         * 각 sum % m 의 값 중에서, 2개를 골라서 조합해야함 -> nC2 == (n * (n-1)) / 2
         */
        sum = new int[n + 1];
        remainder = new long[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i < n + 1; i++) {
            int cur = Integer.parseInt(st.nextToken());
            sum[i] = (cur + sum[i - 1]) % m;
            remainder[sum[i]]++;
        }
        remainder[0]++;

        for (int i = 0; i < m; i++) {
            long cnt = remainder[i];
            /*
            nC2 = (n)*(n-1)/2
             */
            ans += (cnt * (cnt - 1)) / 2;
        }

        System.out.println(ans);
    }
}
