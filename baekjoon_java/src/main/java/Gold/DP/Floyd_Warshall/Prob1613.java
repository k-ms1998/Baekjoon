package Gold.DP.Floyd_Warshall;

import java.io.*;
import java.util.*;

/**
 * Gold 3(역사)
 * 
 * Solution: Floyd-Warshall
 * !! Topological Sort(위상정렬) 문제 같지만, 사실은 플로이드로 해결해야되는 문제 !!
 * 역사가 일어난 순서를 결정해야되기 때문에 위상정렬 같지만, 모든 노드들끼리 연결이 안되어 있을수도 있다
 * 그러므로, a->b로 도달 가능한지를 파악해야한다
 * if(a->b 도달 X && b->a 도달 X) => 서로의 순서를 알 수 없음
 * else if(a->b 도달 O) => a가 b보다 먼저 발생
 * else if(b->a 도달 O) => b가 a보다 먼저 발생
 */
public class Prob1613 {

    static int n, k;
    static int[][] dist;
    static final int INF = 1000000000;

    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        dist = new int[n + 1][n + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                dist[i][j] = INF;
            }
        }
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            dist[a][b] = 1;
        }
        floydWarshall();

        int s = Integer.parseInt(br.readLine());
        for (int i = 0; i < s; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(dist[a][b] == INF && dist[b][a] == INF){
                ans.append(0);
            } else if (dist[a][b] != INF) {
                ans.append(-1);
            } else {
                ans.append(1);
            }

            ans.append("\n");
        }
        System.out.println(ans);
    }

    private static void floydWarshall() {
        for (int i = 1; i < n + 1; i++) {
            for (int s = 1; s < n + 1; s++) {
                for(int d = 1; d < n + 1; d++){
                    dist[s][d] = Math.min(dist[s][d], dist[s][i] + dist[i][d]);
                }
            }
        }

    }


}
/*
6 6
1 2
1 3
2 3
3 4
2 4
5 6
5
1 5
2 4
3 1
6 2
6 5

6 4
1 2
2 3
3 4
1 5
5
1 2
2 3
3 1
2 5
1 6
 */
