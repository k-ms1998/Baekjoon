package Gold.DP.Floyd_Warshall;

import java.util.*;
import java.io.*;

/**
 * Gold 3(저울)
 *
 * https://www.acmicpc.net/problem/10159
 *
 * Solution: Floyd-Warshall
 * 1. Floyd-Warshall 을 이용해서 각 노드로 부터 도달가능한 모든 노드들 찾기 (reachable)
 * 2. 두 개의 노드에 대해서, 서로 도달 가능하던가 서로 도달이 불가능 하면, 두 개의 노드에 대해서는 순서를 확실하게 알 수가 없음
 */
public class Prob10159 {

    static int n, m;
    static boolean[][] reachable;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        reachable = new boolean[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            reachable[i][i] = true;
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            reachable[b][a] = true;
        }

        /**
         * 플로이드로 각 노드에 대해서 도달 가능한 모든 노드 찾기
         */
        floyd();
        /**
         * 각 노드에 대해서, 순서를 확실히 알 수 없는 노드의 갯수 찾기
         * -> 두 개의 노드가 서로 도달 가능하던가, 서로 도달이 불가능하면 순서를 확실히 할 수 없음
         *  -> cnt++
         */
        for (int i = 1; i < n + 1; i++) {
            int cnt = 0;
            for (int j = 1; j < n + 1; j++) {
                if (i == j) {
                    continue;
                }
                if ((!reachable[i][j] && !reachable[j][i]) || (reachable[i][j] && reachable[j][i])) {
                    ++cnt;
                }
            }
            ans.append(cnt).append("\n");
        }

        System.out.println(ans);
    }

    public static void floyd() {
        for (int k = 1; k < n + 1; k++) {
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    if (reachable[i][k] && reachable[k][j]) {
                        reachable[i][j] = true;
                    }
                }
            }
        }
    }
}
/*
3
3
1 2
2 3
3 1
-> 2 2 2

4
3
1 2
2 3
1 4
-> 0 1 1 2
 */