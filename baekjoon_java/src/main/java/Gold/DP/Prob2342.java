package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 3(Dance Dance Revolution)
 *
 * https://www.acmicpc.net/problem/2342
 *
 * Solution: DP
 * DP 로 풀이하지 않으면 시간초과 발생
 */
public class Prob2342 {

    static int size;
    static List<Integer> steps = new ArrayList<>();

    /**
     * dp[i][l][r] => i 번째 지시사항에서, l에는 왼쪽 발 위치, r에는 오른쪽 발 위치 저장 => dp[i][l][r] 는 i번째 지시사항에서, 왼쪽 발이 l, 오른쪽 발이 r에 위치했을때 지금까지 소요된 힘 저장
     */
    static int[][][] dp;
    static int[] dif = {1, 3, 4, 3};   // {같은 지점, 인접, 반대편, 인접}

    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        while (true) {
            int pos = Integer.parseInt(st.nextToken());
            if (pos == 0) {
                break;
            }
            steps.add(pos);
        }

        size = steps.size();
        dp = new int[size + 1][5][5]; // 왼발과 오른발의 위치는 1, 2, 3, 4
        for (int i = 1; i < size + 1; i++) {
            for (int l = 0; l <= 4; l++) {
                for (int r = 0; r <= 4; r++) {
                    dp[i][l][r] = 987654321;
                }
            }
        }
        ddr(0, 0, 0, 0);
        for (int l = 0; l <= 4; l++) {
            for (int r = 0; r <= 4; r++) {
//                System.out.println("dp[size][l][r] = " + dp[size][l][r]);
                if (dp[size][l][r] == 0) {
                    continue;
                }
                ans = Math.min(ans, dp[size][l][r]);
            }
        }

        System.out.println(ans);
    }

    public static void ddr(int depth, int left, int right, int t) {
        if (depth == size) {
//            ans = Math.min(ans, t);
            return;
        }
        dp[depth][left][right] = t;
        int next = steps.get(depth);
        /**
         * leftDif = 왼쪽 발을 다음 위치(next)로 옮길때 드는 힘
         * rightDif = 오른쪽 발을 다음 위치(next)로 옮길때 드는 힘
         * (왼쪽 또는 오른쪽 발이 0 위치에 있으면 옮기는데 드는 힘은 무조건 2)
         */
        int leftDif = left == 0 ? 2 : dif[Math.abs(next - left)];
        int rightDif = right == 0 ? 2 : dif[Math.abs(next - right)];

//        dp[depth + 1][left][right] = Math.min(leftDif, rightDif) + t;
        if (dp[depth + 1][next][right] > t + leftDif || dp[depth + 1][next][right] == 0) {
            ddr(depth + 1, next, right, t + leftDif);
        }
        if(dp[depth + 1][left][next] > t + rightDif || dp[depth + 1][left][next] == 0){
            ddr(depth + 1, left, next, t + rightDif);
        }
    }
}
