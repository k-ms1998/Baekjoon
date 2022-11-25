package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 4(키 순서)
 * 
 * https://www.acmicpc.net/problem/2458
 * 
 * Solution: Floyd-Warshall
 */
public class Prob2458 {

    static int n, m;
    static boolean[][] reachable;

    static int ans = 0;

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        reachable = new boolean[n + 1][n + 1];
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());

            reachable[src][dst] = true;
            reachable[src][src] = true;
            reachable[dst][dst] = true;
        }

        floydWarshall();
        /**
         * 현재 노드에서부터 다른 모든 노드들에 대해서 서로 도달가능한지 불가능한지 확인
         */
        for (int y = 1; y < n + 1; y++) {
            boolean flag = true;
            for (int x = 1; x < n + 1; x++) {
                /**
                 * 서로 둘 다 도달 불가능하면 서로에 대한 순서를 알지 못함
                 * 그러므로, y번째 노드가 자신의 키가 몇번째인지 알 수 없음
                 */
                if (!reachable[y][x] && !reachable[x][y]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans++;
            }
        }

        System.out.println(ans);
    }

    public static void floydWarshall() {
        for (int k = 1; k < n + 1; k++) {
            for (int y = 1; y < n + 1; y++) {
                for (int x = 1; x < n + 1; x++) {
                    /**
                     * y->k && k->x 가 도달 가능하면 y->x도 도달 가능
                     */
                    if(reachable[y][k] && reachable[k][x]){
                        reachable[y][x] = true;
                    }
                }
            }
        }
    }

}