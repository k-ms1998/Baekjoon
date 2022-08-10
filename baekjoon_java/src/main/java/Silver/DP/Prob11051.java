package Silver.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Silver 2
 *
 * 문제
 * 자연수 \(N\)과 정수 \(K\)가 주어졌을 때 이항 계수
 * \(\binom{N}{K}\)를 10,007로 나눈 나머지를 구하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 \(N\)과 \(K\)가 주어진다. (1 ≤ \(N\) ≤ 1,000, 0 ≤ \(K\) ≤ \(N\))
 *
 * 출력
 * \(\binom{N}{K}\)를 10,007로 나눈 나머지를 출력한다.
 *
 * Solution: DP + Pascal's Triangle
 */
public class Prob11051 {

    static int n;
    static int k;
    static Integer[][] tri;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        // 0 <= k <= n <= 1000, n >= 1

        /**
         * Pascal's Triangle
         *      1
         *     1 1
         *    1 2 1
         *   1 3 3 1
         *  1 4 6 4 1
         * 1 5 10 10 5 1
         * =>
         * 1
         * 1 1
         * 1 2 1
         * 1 3 3 1
         * 1 4 6 4 1
         * 1 5 10 10 5 1
         * => ans = tri[n][k]
         */
        tri = new Integer[n + 1][n + 1];
        tri[0][0] = 1;
        tri[1][0] = 1;
        tri[1][1] = 1;
        for (int y = 2; y < n + 1; y++) {
            for (int x = 0; x < y + 1; x++) {
                if (x == 0 || x == y) {
                    tri[y][x] = 1;
                    continue;
                }
                tri[y][x] = (tri[y - 1][x] + tri[y - 1][x - 1]) % 10007;
            }
        }

//        for (int y = 0; y < n + 1; y++) {
//            for (int x = 0; x < y + 1; x++) {
//                System.out.print(tri[y][x] + " ");
//            }
//            System.out.println();
//        }

        System.out.println(tri[n][k] % 10007);
    }
}
