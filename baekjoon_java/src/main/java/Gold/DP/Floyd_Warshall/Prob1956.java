package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 4(운동)
 *
 * https://www.acmicpc.net/problem/1956
 *
 * Solution: Floyd-Warshall
 * 모든 정점에 대해서, 모든 정점까지의 최단 거리를 구하면되기 때문에 Floyd-Warshall 로 해결
 * 이때, 모든 정점을 거칠 필요는 없기 때문에 단순함
 * 싸이클인지 확인하기 위해서는 s->d로 갈때, d->s도 도달 가능한지 확인하면 된다
 */
public class Prob1956 {

    static int v, e;

    static int[][] dist;
    static final int INF = 100000000;
    static int ans;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        e = Integer.parseInt(st.nextToken());

        dist = new int[v + 1][v + 1];
        for (int i = 1; i < v + 1; i++) {
            for (int j = 1; j < v + 1; j++) {
                dist[i][j] = INF;
            }
        }
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            dist[s][d] = c;
        }

        floydWarshall();
        findShortestDist();
        System.out.println(ans);
    }

    private static void findShortestDist() {
        ans = INF;
        
        for (int i = 1; i < v + 1; i++) {
            for (int j = 1; j < v + 1; j++) {
                /**
                 * dist[i][j] == INF 이면, i->j 도달 불가능
                 * dist[j][i] == INF 이면, j->i 도달 불가능
                 *
                 * 둘 중 하나라도 도달 불가능하면 싸이클 X => continue
                 */
                if (dist[i][j] == INF || dist[j][i] == INF) {
                    continue;
                }
                ans = Math.min(ans, dist[i][j] + dist[j][i]);
            }
        }

        if(ans == INF){
            ans = -1;
        }
    }

    public static void floydWarshall(){
        for (int k = 1; k < v + 1; k++) {
            for (int i = 1; i < v + 1; i++) {
                for (int j = 1; j < v + 1; j++) {
                    if (k == i || i == j || k == j) {
                        continue;
                    }
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}