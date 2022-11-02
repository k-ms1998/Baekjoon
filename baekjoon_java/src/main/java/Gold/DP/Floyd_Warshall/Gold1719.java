package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 3(택배)
 *
 * https://www.acmicpc.net/problem/1719
 *
 * Solution: Floyd-Warshall
 */
public class Gold1719 {

    static int n, m;

    static final int INF = 1000000000;
    static int[][] dist;
    static int[][] dropOff;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        dist = new int[n + 1][n + 1];
        dropOff = new int[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    dropOff[i][j] = 0;
                    continue;
                }
                dist[i][j] = INF;
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int src = Integer.parseInt(st.nextToken());
            int dst = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            dist[src][dst] = cost;
            dist[dst][src] = cost;
            dropOff[src][dst] = dst;
            dropOff[dst][src] = src;
        }

        floydWarshall();
        for (int y = 1; y < n + 1; y++) {
            for (int x = 1; x < n + 1; x++) {
                if (dropOff[y][x] == 0) {
                    ans.append("-");
                }else{
                    ans.append(dropOff[y][x]);
                }
                ans.append(" ");
            }
            ans.append("\n");
        }

        System.out.println(ans);
    }

    /**
     * 플로이드로 모든 노드에 대해서 다른 노드들까지의 최단 거리 계산하기
     * 이때, 최단 거리가 업데이트 될때마다 직전에 방문한 노드를 업데이트 (dropOff[y][x])
     */
    private static void floydWarshall() {
        for (int i = 1; i < n + 1; i++) {
            for (int y = 1; y < n + 1; y++) {
                for (int x = 1; x < n + 1; x++) {
                    if(dist[y][x] > dist[y][i] + dist[i][x]){
                        dropOff[y][x] = dropOff[y][i];
                        dist[y][x] = dist[y][i] + dist[i][x];
                    }
                }
            }
        }
    }
}
