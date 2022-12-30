package Gold.DP;

import java.io.*;
import java.util.*;

/**
 * Gold 2(로봇 조종하기)
 *
 * https://www.acmicpc.net/problem/2169
 *
 * Solution: DP
 * 값을 가져오는 방향을 고려한 DP
 * 3차원 배열을 선언해서, (x, y)좌표에서 값을 가져오는 방향에 따라서 따로 최대값을 저장
 */
public class Prob2169 {

    static int n, m;
    static int[][] grid;
    static int[][][] cost;
    static final int INF = -100000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        /**
         * cost 에 (x, y) 좌표에서 각 방향에 따라서 최대값 저장
         * cost[y][x][0] -> 좌측에서 왔을때 (x, y)의 최댓값
         * cost[y][x][1] -> 위에서 왔을때 (x, y)의 최댓값
         * cost[y][x][2] -> 우측에서 왔을때 (x, y)의 최댓값
         * 
         * 주의할점:
         * 각 배열에 저장된 값은 음수가 될수 있으므로, cost[0][0] ~ cost[n][0]에는 모두 가장 작은 값을 넣어줘야함
         * 그냥 0을 넣으면, x가 1인 좌표들에 값을 저장할때 오답 발생
         */
        grid = new int[n + 1][m + 1];
        cost = new int[n + 1][m + 1][3];
        for (int y = 0; y < n + 1; y++) {
            for (int x = 0; x < m + 1; x++) {
                cost[y][x][0] = INF;
                cost[y][x][1] = INF;
                cost[y][x][2] = INF;
            }
        }
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < m + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        cost[1][1][0] = grid[1][1];
        cost[1][1][1] = grid[1][1];
        cost[1][1][2] = grid[1][1];
        for (int x = 2; x < m + 1; x++) {
            cost[1][x][0] = cost[1][x - 1][0] + grid[1][x];
        }
        for (int y = 2; y < n + 1; y++) {
            /**
             * 좌측에서 올때: (x-1, y)에서의 최댓값 + (x, y)의 값 (이때, (x-1, y)의 오른쪽 값인 cost[y][x-1][2]은 계산 X 
             *  -> Because, (x-1, y)의 오른쪽 값은 (x, y) 자기 자신이기 때문에
             *  
             * 위에서 올때: (x, y-1)에서의 최댓ㄱ밧 + (x, y)의 값
             */
            for (int x = 1; x < m + 1; x++) {
                cost[y][x][0] = Math.max(cost[y][x - 1][0], cost[y][x - 1][1]) + grid[y][x];
                cost[y][x][1] = Math.max(cost[y - 1][x][0], Math.max(cost[y - 1][x][1], cost[y - 1][x][2])) + grid[y][x];
            }

            /**
             * 우측에서 올때: (x+1, y)에서의 최댓값 + (x, y)의 값 (이때, (x+1, y)의 왼쪽 값인 cost[y][x+1][0]은 계산 X
             *  -> Because, (x+1, y)의 왼쪽 값은 (x, y) 자기 자신이기 때문에
             */
            for (int x = m - 1; x >= 1; x--) {
                cost[y][x][2] = Math.max(cost[y][x + 1][1], cost[y][x + 1][2]) + grid[y][x];
            }
        }

//        printGrid();
        System.out.println(Math.max(cost[n][m][0], Math.max(cost[n][m][1], cost[n][m][2])));
    }

    public static void printGrid() {
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < m + 1; x++) {
                System.out.print("{" + (cost[y][x][0] == INF ? 0 : cost[y][x][0]) + ", " + (cost[y][x][1] == INF ? 0 : cost[y][x][1]) + ", " + (cost[y][x][2] == INF ? 0 : cost[y][x][2]) + "} ");
            }
            System.out.println();
        }
    }
}
/*
5 5
-1 -1 -1 -1 -1
-1 -1 -1 -1 -1
-1 -1 -1 -1 -1
-1 -1 -1 -1 -1
-1 -1 -1 -1 -1
-> -9
 */