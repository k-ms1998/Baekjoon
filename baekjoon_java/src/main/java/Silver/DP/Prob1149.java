package Silver.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Silver 1 (RGB 거리)
 *
 * https://www.acmicpc.net/problem/1149
 *
 * Solution: DP
 */
public class Prob1149 {

    static int n;
    static int[][] houses;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        houses = new int[n + 1][3];
        for (int i = 1; i < n + 1; i++) {
            /**
             * 0 : R
             * 1 : G
             * 2 : B
             */
            st = new StringTokenizer(br.readLine());
            houses[i][0] = Integer.parseInt(st.nextToken());
            houses[i][1] = Integer.parseInt(st.nextToken());
            houses[i][2] = Integer.parseInt(st.nextToken());
        }

        for (int j = 2; j < n + 1; j++) {
            for (int r = 0; r < 3; r++) {
                /**
                 * r == 0 이면: j번째 집는 Red 로 색칠 -> 이전에 있는 집에서 1(Green) 또는 2(Blue)인 상태일때를 확인
                 * r == 1 이면: j번째 집는 Green 로 색칠 -> 이전에 있는 집에서 0(Red) 또는 2(Blue)인 상태일때를 확인
                 * r == 2 이면: j번째 집는 Blue 로 색칠 -> 이전에 있는 집에서 0(Red) 또는 1(Green)인 상태일때를 확인
                 */
                houses[j][r] = Math.min(houses[j-1][(r-1+3)%3], houses[j-1][(r+1+3)%3]) + houses[j][r];
            }
        }

        System.out.println(Math.min(houses[n][0], Math.min(houses[n][1], houses[n][2])));
    }
}
