package Gold.DP.Floyd_Warshall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Gold 4:
 *
 * https://www.acmicpc.net/problem/11404
 *
 * Solution: Floyd-Warshall(DP)
 */
public class Prob11404_floyd_warshall {

    static int n;
    static int m;
    static int[][] floyd;
    static final int MAX_DIST = 10000001; // 100 * 100000 + 1

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        floyd = new int[n + 1][n + 1];
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                if (y == x) {
                    floyd[y][x] = 0;
                    continue;
                }
                floyd[y][x] = MAX_DIST;
            }
        }

        m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int ts = Integer.parseInt(st.nextToken());
            int td = Integer.parseInt(st.nextToken());
            int tCost = Integer.parseInt(st.nextToken());

            /**
             * a->b 로 가는 버스가 여러개 존재하는 경우가 있는데, 이럴 경우 비용이 가장 작은 값을 저장해야됨
             * ex: 1 4 4, 1 4 1 중에서 1 -> 4 가는데 비용이 1인 버스를 선택해야됨
             */
            floyd[ts][td] = Math.min(floyd[ts][td], tCost);
        }

        /**
         * !! FLoyd-Warshall Algorithm !! => 시간 복잡도 = O(n^3)
         * 노드 y -> 노드 x 로 가는데, 중간에 노드 k 를 거쳐서 가는 경우를 계산하는 것 (y -> k -> x)
         * 그러므로, floyd[y][x] = min(floyd[y][x], y->k 가는 거리 + k -> x 를 가는 거리)
         */
        for(int k = 1; k < n + 1; k++){
            for (int y = 1; y < n + 1; y++) {
                if (y == k) {
                    continue;
                }
                for (int x = 1; x < n + 1; x++) {
                    if (y == x) {
                        continue;
                    }
                    floyd[y][x] = Math.min(floyd[y][x], floyd[y][k] + floyd[k][x]);
                }
            }
        }

        for (int y = 1; y < n + 1; y++) {
            for(int x = 1; x < n + 1; x++){
                int cost = floyd[y][x];
                /**
                 * 거리가 MAX_DIST 이면 도달 불가 -> 0 으로 치환
                 */
                ans.append(cost == MAX_DIST ? 0 : cost);
                ans.append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

}
