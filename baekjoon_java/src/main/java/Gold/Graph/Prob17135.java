package Gold.Graph;

import java.io.*;
import java.util.*;

/**
 * Gold 3(캐슬 디펜스)
 *
 * https://www.acmicpc.net/problem/17135
 *
 * Solution: Brute Force + Combination
 * 1. 궁수들을 배치할 수 있는 모든 조합 구하기
 * 2. 각 조합에 대해서 총 몇개의 적을 공격할 수 있는지 확인
 * 3. 공격할때, 다수의 궁수가 하나의 적을 동시에 공격할 수 있는 점 유의
 */
public class Prob17135 {

    static int n, m, d;
    static int[][] grid;
    static int[] archersX = new int[3];
    static int enemies = 0;
    static int ans = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        d = Integer.parseInt(st.nextToken());

        grid = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x < m + 1; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
                if (grid[y][x] == 1) {
                    ++enemies;
                }
            }
        }

        for (int i = 1; i < m - 1; i++) {
            archersX = new int[3];
            placeArchers(0, i);
        }
        System.out.println(ans);
    }

    /**
     * 궁수들의 배치 조합 구하기
     */
    public static void placeArchers(int depth, int idx) {
        if (depth == 3) {
            ans = Math.max(ans, findAnswer());
            return;
        }

        for (int x = idx; x < m + 1; x++) {
            archersX[depth] = x;
            placeArchers(depth + 1, x + 1);
        }
    }

    /**
     * 각 조합에 대해서 총 공격할 수 있는 적의 수 찾기
     * 1. 적들 공격하기
     * 2. 적들 움직이기
     */
    public static int findAnswer() {
        /**
         * 각 조합마다 따로 격자판을 업데이트 시켜야 하기 때문에, 오리지날 격자판을 복사
         */
        int[][] tmp = new int[n + 1][m + 1];
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < m + 1; x++) {
                tmp[y][x] = grid[y][x];
            }
        }

        int cnt = 0;
        int enemiesCnt = enemies;
        /**
         * 남은 적들의 수가 0일때까지 반복
         */
        while(enemiesCnt > 0){
            /*
            공격하기
             */
            for (int i = 0; i < 3; i++) {
                int archer = archersX[i];
                for (int dist = 1; dist <= d; dist++) {
                    int minX = archer - dist + 1 <= 0 ? 1 : archer - dist + 1;
                    int maxX = archer + dist - 1 > m ? m : archer + dist - 1;

                    for (int x = minX; x <= maxX; x++) {
                        int minY = (n + 1) - dist <= 0 ? 1 : (n + 1) - dist;
                        for (int y = n; y >= minY; y--) {
                            int distance = Math.abs(x - archer) + (n + 1 - y);
                            if (distance <= dist) {
                                /**
                                 * 여러 궁수가 동시에 공격할 수 있기 때문에, 이미 공격을 받은 적은 -1로 표시
                                 */
                                if (tmp[y][x] == 1 || tmp[y][x] == -1) {
                                    tmp[y][x] = -1;

                                    /*
                                    반복문들 모두 탈출하기
                                     */
                                    dist = d;
                                    x = maxX;
                                    y = minY;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            /*
            적들 움직이기
             */
            for (int x = 1; x < m + 1; x++) {
                for (int y = n; y >= 1; y--) {
                    if (tmp[y][x] == 1) {
                        tmp[y][x] = 0;
                        if (y + 1 <= n) {
                            tmp[y + 1][x] = 1;
                        }else{
                            --enemiesCnt;
                        }
                    } else if (tmp[y][x] == -1) {
                        /**
                         * -1 이면 공격을 받은 적으므로, 제거해주고 cnt 추가
                         */
                        ++cnt;
                        --enemiesCnt;
                        tmp[y][x] = 0;
                    }
                }
            }
        }

        return cnt;
    }
}
/*
5 5 2
1 0 1 1 1
0 1 1 1 1
1 0 1 0 1
1 1 0 1 0
1 0 1 0 1
-> 14

 */
